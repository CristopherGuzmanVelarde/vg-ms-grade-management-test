package pe.edu.vallegrande.vg_ms_grade_management.domain.enums;

/**
 * Enumeración para las escalas de calificación según el sistema MINEDU
 * 
 * INICIAL (3-5 años):
 * - INICIO, PROCESO, LOGRADO
 * 
 * PRIMARIA (1ro-6to grado):
 * - C (En inicio: 11-00)
 * - B (En proceso: 14-11)
 * - A (Logro esperado: 17-14)
 * - AD (Logro destacado: 20-17)
 * 
 * SECUNDARIA (1ro-5to año):
 * - Escala numérica: 0-20
 * - Calificación mínima aprobatoria: 11
 */
public enum GradeScale {
    // Primaria y Secundaria
    AD("Logro destacado", "El estudiante demuestra un aprendizaje superior al esperado"),
    A("Logro esperado", "El estudiante ha alcanzado el nivel de aprendizaje esperado"),
    B("En proceso", "El estudiante está cerca de alcanzar el nivel esperado"),
    C("En inicio", "El estudiante muestra un progreso mínimo"),
    
    // Inicial
    INICIO("En inicio", "En proceso de desarrollo"),
    PROCESO("En proceso", "En camino al logro"),
    LOGRADO("Logrado", "Logro esperado");
    
    private final String displayName;
    private final String description;
    
    GradeScale(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
}
