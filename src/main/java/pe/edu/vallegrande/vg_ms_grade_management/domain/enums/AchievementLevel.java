package pe.edu.vallegrande.vg_ms_grade_management.domain.enums;

/**
 * Enum que representa los niveles de logro según el sistema educativo peruano
 */
public enum AchievementLevel {
    AD("Destacado", "El estudiante demuestra un aprendizaje superior al esperado para su grado o edad."),
    A("Satisfactorio", "El estudiante ha alcanzado el nivel de aprendizaje esperado para su grado."),
    B("En Proceso", "El estudiante está cerca de alcanzar el nivel esperado, pero requiere acompañamiento para lograrlo."),
    C("En Inicio", "El estudiante muestra un progreso mínimo y necesita mayor apoyo y tiempo para desarrollar la competencia.");

    private final String description;
    private final String message;

    AchievementLevel(String description, String message) {
        this.description = description;
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Obtiene el mensaje automático para el campo remarks
     * @return Mensaje automático basado en el nivel de logro
     */
    public String getAutoRemarks() {
        return this.message;
    }
}