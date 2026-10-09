import java.time.LocalDate;

public final class Teacher {

    private final int teacherId;
    private final String lastName;
    private final String firstName;
    private final String patronymic;
    private final LocalDate birthDate;
    private final String academicDegree;
    private final String administrativePosition;
    private final int experienceYears;
    private final String phone;
    private final String email;

    public Teacher(
            int teacherId,
            String lastName,
            String firstName,
            String patronymic,
            LocalDate birthDate,
            String academicDegree,
            String administrativePosition,
            int experienceYears,
            String phone,
            String email
    ) {
        this.teacherId = teacherId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.academicDegree = academicDegree;
        this.administrativePosition = administrativePosition;
        this.experienceYears = experienceYears;
        this.phone = phone;
        this.email = email;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getAcademicDegree() {
        return academicDegree;
    }

    public String getAdministrativePosition() {
        return administrativePosition;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}