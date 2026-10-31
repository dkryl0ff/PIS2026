import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.time.Period;

public class Teacher {

    private String lastName;
    private String firstName;
    private String patronymic;
    private LocalDate birthDate;
    private String academicDegree;
    private String administrativePosition;
    private int experienceYears;
    private String phone;
    private String email;

    public Teacher(
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
        setLastName(lastName);
        setFirstName(firstName);
        setPatronymic(patronymic);
        setBirthDate(birthDate);
        setAcademicDegree(academicDegree);
        setAdministrativePosition(administrativePosition);
        setExperienceYears(experienceYears);
        setPhone(phone);
        setEmail(email);
    }

    public Teacher(String line) {
        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException("Пустая строка");
        }

        String[] fields = line.split(";", -1);

        if (fields.length != 9) {
            throw new IllegalArgumentException(
                    "Ожидалось 9 полей, получено: " + fields.length
            );
        }

        setLastName(fields[0].trim());
        setFirstName(fields[1].trim());
        setPatronymic(fields[2].trim());
        setBirthDate(LocalDate.parse(fields[3].trim()));
        setAcademicDegree(fields[4].trim());
        setAdministrativePosition(fields[5].trim());
        setExperienceYears(Integer.parseInt(fields[6].trim()));
        setPhone(fields[7].trim());
        setEmail(fields[8].trim());
    }

    public Teacher(JsonObject json) {
        setLastName(json.get("last_name").getAsString());
        setFirstName(json.get("first_name").getAsString());
        setPatronymic(json.get("patronymic").getAsString());
        setBirthDate(
                LocalDate.parse(json.get("birth_date").getAsString())
        );
        setAcademicDegree(json.get("academic_degree").getAsString());
        setAdministrativePosition(
                json.get("administrative_position").getAsString()
        );
        setExperienceYears(
                json.get("experience_years")
                        .getAsBigDecimal()
                        .intValueExact()
        );
        setPhone(json.get("phone").getAsString());
        setEmail(json.get("email").getAsString());
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (!isValidName(lastName)) {
            throw new IllegalArgumentException(
                    "Некорректная фамилия: " + lastName
            );
        }

        this.lastName = lastName.trim();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (!isValidName(firstName)) {
            throw new IllegalArgumentException(
                    "Некорректное имя: " + firstName
            );
        }

        this.firstName = firstName.trim();
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        if (!isValidName(patronymic)) {
            throw new IllegalArgumentException(
                    "Некорректное отчество: " + patronymic
            );
        }

        this.patronymic = patronymic.trim();
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        if (!isValidBirthDate(birthDate)) {
            throw new IllegalArgumentException(
                    "Некорректная дата рождения: " + birthDate
            );
        }

        if (!isValidExperienceYears(experienceYears, birthDate)) {
            throw new IllegalArgumentException(
                    "Дата рождения несовместима с текущим стажем"
            );
        }

        this.birthDate = birthDate;
    }

    public String getAcademicDegree() {
        return academicDegree;
    }

    public void setAcademicDegree(String academicDegree) {
        if (!isValidText(academicDegree)) {
            throw new IllegalArgumentException(
                    "Некорректная учёная степень: " + academicDegree
            );
        }

        this.academicDegree = academicDegree.trim();
    }

    public String getAdministrativePosition() {
        return administrativePosition;
    }

    public void setAdministrativePosition(
            String administrativePosition
    ) {
        if (!isValidText(administrativePosition)) {
            throw new IllegalArgumentException(
                    "Некорректная административная должность: "
                            + administrativePosition
            );
        }

        this.administrativePosition = administrativePosition.trim();
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        if (!isValidExperienceYears(experienceYears, birthDate)) {
            throw new IllegalArgumentException(
                    "Стаж должен быть неотрицательным "
                            + "и не превышать возраст"
            );
        }

        this.experienceYears = experienceYears;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (!isValidPhone(phone)) {
            throw new IllegalArgumentException(
                    "Некорректный телефон: " + phone
            );
        }

        this.phone = phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException(
                    "Некорректный email: " + email
            );
        }

        this.email = email.trim();
    }

    public static boolean isValidName(String value) {
        if (value == null) {
            return false;
        }

        String trimmed = value.trim();

        return !trimmed.isEmpty()
                && trimmed.length() <= 50
                && trimmed.matches("\\p{L}+([ '-]\\p{L}+)*");
    }

    public static boolean isValidBirthDate(LocalDate birthDate) {
        return birthDate != null
                && birthDate.isBefore(LocalDate.now());
    }

    public static boolean isValidText(String value) {
        return value != null
                && value.trim().length() <= 150
                && value.codePoints()
                        .noneMatch(Character::isISOControl);
    }

    public static boolean isValidExperienceYears(
            int experienceYears,
            LocalDate birthDate
    ) {
        if (!isValidBirthDate(birthDate)) {
            return false;
        }

        int age = Period.between(
                birthDate,
                LocalDate.now()
        ).getYears();

        return experienceYears >= 0 && experienceYears <= age;
    }

    public static boolean isValidPhone(String phone) {
        if (phone == null) {
            return false;
        }

        return phone.trim().matches("\\+[1-9][0-9]{7,14}");
    }

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }

        String trimmed = email.trim();

        return trimmed.length() <= 254
                && trimmed.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+");
    }

    public String toFullString() {
        return "Преподаватель: "
                + lastName + " "
                + firstName + " "
                + patronymic
                + ", дата рождения: " + birthDate
                + ", учёная степень: " + academicDegree
                + ", административная должность: "
                + administrativePosition
                + ", стаж: " + experienceYears
                + ", телефон: " + phone
                + ", email: " + email;
    }

    public String toShortString() {
        return Mapper.createShortName(this)
                + ", телефон: " + phone;
    }

    public boolean isSameTeacher(Teacher teacher) {
        return teacher != null && phone.equals(teacher.phone);
    }
}