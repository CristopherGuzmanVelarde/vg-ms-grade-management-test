package pe.edu.vallegrande.vg_ms_grade_management.infrastructure.dto.response;

public class StudentDTO {
    private String id;
    private String firstName;
    private String lastName;
    private Boolean deleted;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public Boolean getDeleted() { return deleted; }
    public void setDeleted(Boolean deleted) { this.deleted = deleted; }
} 