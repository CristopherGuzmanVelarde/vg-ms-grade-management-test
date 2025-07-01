package pe.edu.vallegrande.vg_ms_grade_management.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vg_ms_grade_management.domain.model.DatabaseSequence;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class SequenceGeneratorService {

    private final ReactiveMongoOperations mongoOperations;

    public Mono<Long> generateSequence(String seqName) {
        Query query = new Query(Criteria.where("_id").is(seqName));
        Update update = new Update().inc("seq", 1);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);

        return mongoOperations.findAndModify(query, update, options, DatabaseSequence.class)
                .map(DatabaseSequence::getSeq)
                .defaultIfEmpty(1L) // In case the sequence doesn't exist yet, start with 1
                .flatMap(seq -> {
                    // Ensure the first value is 1 if it was newly created and defaulted to 1L
                    // or if the existing sequence was somehow 0 or less.
                    if (seq <= 0) {
                        DatabaseSequence newSeq = new DatabaseSequence(seqName, 1L);
                        return mongoOperations.save(newSeq).thenReturn(1L);
                    }
                    return Mono.just(seq);
                });
    }
}