package pe.edu.vallegrande.vg_ms_grade_management.application.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.EvaluationType;
import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.GradeScale;
import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.TypePeriod;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.document.GradeDocument;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.request.GradeRequest;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.mapper.GradeMapper;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.repository.GradeRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 🧪 Tests Unitarios para GradeServiceImpl - Microservicio de Calificaciones
 * Pruebas con datos mock para validación en Jenkins
 * Compatible con Java 17+ y todas las terminales
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("🎓 Grade Service - Unit Tests")
class GradeServiceImplTest {

        @Mock
        private GradeRepository gradeRepository;

        private GradeMapper gradeMapper;

        private GradeServiceImpl gradeService;

        private String teacherId;

        @BeforeEach
        void setUp() {
                // Configurar encoding UTF-8 para emojis en todas las terminales
                System.setProperty("file.encoding", "UTF-8");
                System.setProperty("sun.stdout.encoding", "UTF-8");
                System.setProperty("sun.stderr.encoding", "UTF-8");

                // Inicializar mapper real (no mock) para evitar problemas con Java 25
                gradeMapper = new GradeMapper();

                // Inicializar servicio con dependencias
                gradeService = new GradeServiceImpl(gradeRepository, gradeMapper);

                teacherId = "TCH2024001";
        }

        @Test
        @DisplayName("📝 Test 1: Registro de Calificaciones en Lote (Batch)")
        void registerBatchGrades_Success() {
                System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
                System.out.println("║  📝 TEST 1: REGISTRO DE CALIFICACIONES EN LOTE               ║");
                System.out.println("╚════════════════════════════════════════════════════════════════╝");
                System.out.println("📌 Objetivo: Validar registro masivo de calificaciones");
                System.out.println("👨‍🏫 Profesor: " + teacherId);
                System.out.println("📚 Escenario: 2 estudiantes - Competencia MINEDU\n");
                // 📋 Arrange - Preparar datos mock
                GradeRequest request1 = new GradeRequest();
                request1.setStudentId("STU2024101");
                request1.setCourseId("MAT-5TO-SEC");
                request1.setClassroomId("5A-SECUNDARIA");
                request1.setPeriodId("2024-T1");
                request1.setTypePeriod(TypePeriod.I_TRIMESTRE);
                request1.setCompetenceName("Resuelve problemas de cantidad");
                request1.setCapacityEvaluated("Traduce cantidades a expresiones numéricas");
                request1.setGradeScale(GradeScale.A);
                request1.setNumericGrade(new BigDecimal("15.0"));
                request1.setEvaluationType(EvaluationType.FORMATIVA);
                request1.setEvaluationDate(LocalDate.now());

                GradeRequest request2 = new GradeRequest();
                request2.setStudentId("STU2024102");
                request2.setCourseId("MAT-5TO-SEC");
                request2.setClassroomId("5A-SECUNDARIA");
                request2.setPeriodId("2024-T1");
                request2.setTypePeriod(TypePeriod.I_TRIMESTRE);
                request2.setCompetenceName("Resuelve problemas de cantidad");
                request2.setCapacityEvaluated("Traduce cantidades a expresiones numéricas");
                request2.setGradeScale(GradeScale.AD);
                request2.setNumericGrade(new BigDecimal("18.0"));
                request2.setEvaluationType(EvaluationType.FORMATIVA);
                request2.setEvaluationDate(LocalDate.now());

                System.out.println("📊 Datos Mock Preparados:");
                System.out.println("   👤 Estudiante 1: María García (STU2024101) | Nota: A (15.0)");
                System.out.println("   👤 Estudiante 2: Carlos Pérez (STU2024102) | Nota: AD (18.0)");
                System.out.println("   📖 Competencia: Resuelve problemas de cantidad");
                System.out.println("   🎯 Capacidad: Traduce cantidades a expresiones numéricas");
                System.out.println("   📅 Período: I_TRIMESTRE 2024\n");

                // Mock del repositorio - el mapper es real
                when(gradeRepository.save(any(GradeDocument.class)))
                                .thenAnswer(invocation -> {
                                        GradeDocument doc = invocation.getArgument(0);
                                        // Simular que MongoDB asigna un ID
                                        if (doc.getStudentId().equals("STU2024101")) {
                                                doc.setId("GRD2024101");
                                        } else if (doc.getStudentId().equals("STU2024102")) {
                                                doc.setId("GRD2024102");
                                        }
                                        return Mono.just(doc);
                                });

                // ⚡ Act & Assert - Ejecutar y validar
                System.out.println("⚡ Ejecutando registro en lote...");
                StepVerifier.create(gradeService.registerBatchGrades(java.util.List.of(request1, request2), teacherId))
                                .expectNextMatches(g -> {
                                        System.out.println("   ✅ Calificación 1 registrada: " + g.getId());
                                        return g.getId().equals("GRD2024101");
                                })
                                .expectNextMatches(g -> {
                                        System.out.println("   ✅ Calificación 2 registrada: " + g.getId());
                                        return g.getId().equals("GRD2024102");
                                })
                                .verifyComplete();

                verify(gradeRepository, times(2)).save(any(GradeDocument.class));
                System.out.println("\n✨ TEST 1 COMPLETADO: 2 calificaciones registradas exitosamente");
                System.out.println("════════════════════════════════════════════════════════════════\n");
        }

        @Test
        @DisplayName("📊 Test 2: Generación de Reporte Consolidado por Aula")
        void getClassroomPeriodReport_Success() {
                System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
                System.out.println("║  📊 TEST 2: REPORTE CONSOLIDADO POR AULA Y PERÍODO          ║");
                System.out.println("╚════════════════════════════════════════════════════════════════╝");
                System.out.println("📌 Objetivo: Validar generación de reportes académicos");
                System.out.println("🏫 Aula: 5A-SECUNDARIA");
                System.out.println("📅 Período: I_TRIMESTRE 2024\n");
                // 📋 Arrange - Preparar datos mock
                String classroomId = "5A-SECUNDARIA";
                String periodId = "2024-T1";
                TypePeriod typePeriod = TypePeriod.I_TRIMESTRE;

                GradeDocument doc1 = GradeDocument.builder()
                                .id("GRD2024201")
                                .courseId("MAT-5TO-SEC")
                                .gradeScale(GradeScale.A)
                                .teacherId(teacherId)
                                .status("A")
                                .build();

                GradeDocument doc2 = GradeDocument.builder()
                                .id("GRD2024202")
                                .courseId("MAT-5TO-SEC")
                                .gradeScale(GradeScale.AD)
                                .teacherId(teacherId)
                                .status("A")
                                .build();

                GradeDocument doc3 = GradeDocument.builder()
                                .id("GRD2024203")
                                .courseId("COM-5TO-SEC")
                                .gradeScale(GradeScale.B)
                                .teacherId(teacherId)
                                .status("A")
                                .build();

                System.out.println("📊 Datos Mock Preparados:");
                System.out.println("   📚 Matemática (MAT-5TO-SEC): 2 calificaciones [A, AD]");
                System.out.println("   📚 Comunicación (COM-5TO-SEC): 1 calificación [B]");
                System.out.println("   📈 Total de calificaciones: 3\n");

                // Mock del repositorio
                when(gradeRepository.findByClassroomIdAndPeriodIdAndTypePeriodAndTeacherIdAndStatusActive(
                                classroomId, periodId, typePeriod, teacherId))
                                .thenReturn(Flux.just(doc1, doc2, doc3));

                // ⚡ Act & Assert - Ejecutar y validar
                System.out.println("⚡ Generando reporte consolidado...");
                StepVerifier.create(gradeService.getClassroomPeriodReport(classroomId, periodId, typePeriod, teacherId))
                                .expectNextMatches(report -> {
                                        System.out.println("   📋 Reporte generado con " + report.size() + " cursos");
                                        assertThat(report).hasSize(2);
                                        System.out.println("   ✅ Validación: 2 cursos agrupados correctamente");
                                        return true;
                                })
                                .verifyComplete();

                verify(gradeRepository, times(1))
                                .findByClassroomIdAndPeriodIdAndTypePeriodAndTeacherIdAndStatusActive(
                                                classroomId, periodId, typePeriod, teacherId);

                System.out.println("\n✨ TEST 2 COMPLETADO: Reporte consolidado generado exitosamente");
                System.out.println("════════════════════════════════════════════════════════════════\n");
        }

        @Test
        @DisplayName("🔍 Test 3: Registro con Campos Opcionales (Null Handling)")
        void registerGrade_WithNullObservations_Success() {
                System.out.println("\n╔════════════════════════════════════════════════════════════════╗");
                System.out.println("║  🔍 TEST 3: MANEJO DE CAMPOS OPCIONALES (NULL)              ║");
                System.out.println("╚════════════════════════════════════════════════════════════════╝");
                System.out.println("📌 Objetivo: Validar registro sin observaciones");
                System.out.println("👨‍🎓 Estudiante: Ana Torres (STU2024303)");
                System.out.println("📝 Observaciones: null (campo opcional)\n");
                // 📋 Arrange - Preparar datos mock
                GradeRequest requestWithoutObservations = new GradeRequest();
                requestWithoutObservations.setStudentId("STU2024303");
                requestWithoutObservations.setCourseId("COM-5TO-SEC");
                requestWithoutObservations.setClassroomId("5A-SECUNDARIA");
                requestWithoutObservations.setPeriodId("2024-B2");
                requestWithoutObservations.setTypePeriod(TypePeriod.II_BIMESTRE);
                requestWithoutObservations.setCompetenceName("Se comunica oralmente en su lengua materna");
                requestWithoutObservations.setCapacityEvaluated("Obtiene información del texto oral");
                requestWithoutObservations.setGradeScale(GradeScale.B);
                requestWithoutObservations.setNumericGrade(new BigDecimal("13.5"));
                requestWithoutObservations.setEvaluationType(EvaluationType.SUMATIVA);
                requestWithoutObservations.setEvaluationDate(LocalDate.now());
                requestWithoutObservations.setObservations(null);

                System.out.println("📊 Datos Mock Preparados:");
                System.out.println("   📖 Competencia: Se comunica oralmente en su lengua materna");
                System.out.println("   🎯 Capacidad: Obtiene información del texto oral");
                System.out.println("   � OCalificación: B (13.5)");
                System.out.println("   � Observac iones: null");
                System.out.println("   📅 Período: II_BIMESTRE\n");

                // Mock del repositorio - el mapper es real
                when(gradeRepository.save(any(GradeDocument.class)))
                                .thenAnswer(invocation -> {
                                        GradeDocument doc = invocation.getArgument(0);
                                        doc.setId("GRD2024303");
                                        return Mono.just(doc);
                                });

                // ⚡ Act & Assert - Ejecutar y validar
                System.out.println("⚡ Registrando calificación sin observaciones...");
                StepVerifier.create(gradeService.registerGrade(requestWithoutObservations, teacherId))
                                .expectNextMatches(savedGrade -> {
                                        System.out.println("   ✅ Calificación registrada: " + savedGrade.getId());
                                        System.out.println("   ✅ Observaciones: "
                                                        + (savedGrade.getObservations() == null ? "null (OK)"
                                                                        : "ERROR"));
                                        assertThat(savedGrade.getId()).isEqualTo("GRD2024303");
                                        assertThat(savedGrade.getObservations()).isNull();
                                        return true;
                                })
                                .verifyComplete();

                verify(gradeRepository, times(1)).save(any(GradeDocument.class));

                System.out.println("\n✨ TEST 3 COMPLETADO: Campo opcional manejado correctamente");
                System.out.println("════════════════════════════════════════════════════════════════\n");
        }
}
