package pe.edu.vallegrande.vg_ms_grade_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * Microservicio de Gestión de Calificaciones - Grade Management v8.0
 * 
 * Sistema de registro de calificaciones basado en el estándar MINEDU (Ministerio de Educación del Perú)
 * con capacidades y competencias por períodos evaluativos.
 * 
 * ROL EXCLUSIVO: TEACHER
 * 
 * Características principales:
 * - Registro de calificaciones por competencias y capacidades MINEDU
 * - Escalas de evaluación según nivel educativo (AD/A/B/C, INICIO/PROCESO/LOGRADO, 0-20)
 * - Gestión por períodos evaluativos (Trimestres, Bimestres, Semestres)
 * - Validación granular con headers HTTP (X-User-Id, X-User-Roles, X-Institution-Id)
 * - Arquitectura de microservicios independiente (sin FOREIGN KEYS)
 * - Base de datos MongoDB reactiva
 * 
 * Endpoints principales:
 * - POST /api/v1/grades/teacher/register-grade - Registrar calificación individual
 * - POST /api/v1/grades/teacher/register-batch-grades - Registrar en lote
 * - PUT /api/v1/grades/teacher/update-grade/{id} - Actualizar calificación (mantiene estado A)
 * - DELETE /api/v1/grades/teacher/delete-grade/{id} - Eliminar lógicamente (estado I)
 * - PATCH /api/v1/grades/teacher/restore-grade/{id} - Restaurar calificación (I -> A)
 * - GET /api/v1/grades/teacher/my-grades - Ver mis calificaciones
 * - GET /api/v1/grades/teacher/classroom/{id}/period/{id}/type/{type}/report - Reporte consolidado
 * - GET /api/v1/grades/teacher/student/{id}/grades - Historial de estudiante
 */
@SpringBootApplication
@EnableWebFlux
public class VgMsGradeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(VgMsGradeManagementApplication.class, args);
	}
}
