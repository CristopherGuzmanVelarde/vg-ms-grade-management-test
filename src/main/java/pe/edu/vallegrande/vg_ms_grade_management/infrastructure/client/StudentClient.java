package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.response.StudentDTO;

@Service
public class StudentClient {
    private final WebClient webClient;

    public StudentClient(@Value("${students.service.url}") String studentsServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(studentsServiceUrl)
                .build();
    }

    public Mono<StudentDTO> getStudentById(String studentId) {
        return webClient.get()
                .uri("/api/v1/students/{id}", studentId)
                .retrieve()
                .bodyToMono(StudentDTO.class);
    }
} 