package pe.edu.vallegrande.vg_ms_grade_management.domain.enums;

/**
 * Enumeración para los tipos de períodos evaluativos según el sistema educativo peruano
 */
public enum TypePeriod {
    I_TRIMESTRE("I Trimestre"),
    II_TRIMESTRE("II Trimestre"),
    III_TRIMESTRE("III Trimestre"),
    I_BIMESTRE("I Bimestre"),
    II_BIMESTRE("II Bimestre"),
    III_BIMESTRE("III Bimestre"),
    IV_BIMESTRE("IV Bimestre"),
    I_SEMESTRE("I Semestre"),
    II_SEMESTRE("II Semestre");
    
    private final String description;
    
    TypePeriod(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
