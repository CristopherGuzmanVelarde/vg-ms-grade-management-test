package pe.edu.vallegrande.vg_ms_grade_management.domain.enums;

/**
 * Enumeración para los instrumentos de evaluación
 */
public enum EvaluationInstrument {
    PRUEBA_ESCRITA("Prueba escrita"),
    PROYECTO("Proyecto"),
    EXPOSICION("Exposición"),
    RUBRICA("Rúbrica"),
    LISTA_COTEJO("Lista de cotejo"),
    PORTAFOLIO("Portafolio"),
    OBSERVACION("Observación"),
    ENSAYO("Ensayo"),
    PRACTICA_LABORATORIO("Práctica de laboratorio"),
    EXAMEN_ORAL("Examen oral");
    
    private final String description;
    
    EvaluationInstrument(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}