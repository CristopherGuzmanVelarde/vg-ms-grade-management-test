package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.validation;

import org.springframework.http.server.reactive.ServerHttpRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.vallegrande.vg_ms_grade_management.domain.enums.UserRole;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.exception.InvalidTokenException;
import pe.edu.vallegrande.vg_ms_grade_management.infrastructure.exception.InsufficientPermissionsException;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Validador de headers HTTP para autenticación y autorización
 * Implementa el sistema de validación granular con headers HTTP v7.0
 */
public class HeaderValidator {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class HeaderValidationResult {
        private UUID userId;
        private List<UserRole> userRoles;
        private UUID institutionId;
    }

    /**
     * Valida los headers HTTP obligatorios: X-User-Id, X-User-Roles, X-Institution-Id
     * @param request Solicitud HTTP con los headers
     * @return Resultado de la validación con datos extraídos
     */
    public static HeaderValidationResult validateHeaders(ServerHttpRequest request) {
        String userIdHeader = request.getHeaders().getFirst("X-User-Id");
        String userRolesHeader = request.getHeaders().getFirst("X-User-Roles");
        String institutionIdHeader = request.getHeaders().getFirst("X-Institution-Id");

        // Validar headers obligatorios
        if (userIdHeader == null || userIdHeader.trim().isEmpty()) {
            throw new InvalidTokenException("X-User-Id", "Header is required");
        }

        if (userRolesHeader == null || userRolesHeader.trim().isEmpty()) {
            throw new InvalidTokenException("X-User-Roles", "Header is required");
        }

        // Parsear User ID
        UUID userId;
        try {
            userId = UUID.fromString(userIdHeader.trim());
        } catch (IllegalArgumentException e) {
            throw new InvalidTokenException("X-User-Id", "Invalid UUID format");
        }

        // Parsear roles a enum UserRole - solo incluir roles válidos
        List<UserRole> userRoles;
        try {
            userRoles = Arrays.stream(userRolesHeader.split(","))
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .filter(roleStr -> {
                        try {
                            UserRole.valueOf(roleStr);
                            return true;
                        } catch (IllegalArgumentException e) {
                            // Ignorar roles no válidos en lugar de fallar
                            System.out.println("Ignoring invalid role: " + roleStr);
                            return false;
                        }
                    })
                    .map(UserRole::valueOf)
                    .collect(Collectors.toList());
            
            // Validar que se encontró al menos un rol válido
            if (userRoles.isEmpty()) {
                throw new InvalidTokenException("X-User-Roles", "No valid roles found in header");
            }
        } catch (InvalidTokenException e) {
            throw e; // Re-lanzar InvalidTokenException
        } catch (Exception e) {
            throw new InvalidTokenException("X-User-Roles", "Invalid value format");
        }

        // Parsear Institution ID (puede ser null para ADMIN)
        UUID institutionId = null;
        if (institutionIdHeader != null && !institutionIdHeader.trim().isEmpty() 
                && !institutionIdHeader.trim().equalsIgnoreCase("null")) {
            try {
                institutionId = UUID.fromString(institutionIdHeader.trim());
            } catch (IllegalArgumentException e) {
                throw new InvalidTokenException("X-Institution-Id", "Invalid UUID format");
            }
        }

        return HeaderValidationResult.builder()
                .userId(userId)
                .userRoles(userRoles)
                .institutionId(institutionId)
                .build();
    }

    /**
     * Valida si el usuario tiene rol de TEACHER
     * @param userRoles Lista de roles del usuario
     * @return true si contiene el rol TEACHER
     */
    public static boolean validateTeacherAccess(List<UserRole> userRoles) {
        return userRoles.contains(UserRole.teacher);
    }

    /**
     * Valida que el usuario tenga rol TEACHER para acceder a endpoints de teacher
     * @param headers Resultado de la validación de headers
     * @throws InsufficientPermissionsException si no tiene el rol requerido
     */
    public static void validateTeacherRole(HeaderValidationResult headers) {
        // Solo teacher puede acceder a endpoints teacher
        if (!validateTeacherAccess(headers.getUserRoles())) {
            throw new InsufficientPermissionsException("teacher", "access teacher endpoints");
        }
        
        // Teacher debe tener X-Institution-Id
        if (headers.getInstitutionId() == null) {
            throw new InvalidTokenException("X-Institution-Id", "Teacher users must have Institution-Id");
        }
    }

    /**
     * Valida headers para endpoints de TEACHER de forma reactiva
     * @param request Solicitud HTTP
     * @return Mono con el resultado de la validación
     */
    public static Mono<HeaderValidationResult> validateTeacherHeaders(ServerHttpRequest request) {
        return Mono.fromCallable(() -> {
            HeaderValidationResult headers = validateHeaders(request);
            validateTeacherRole(headers);
            return headers;
        });
    }
}
