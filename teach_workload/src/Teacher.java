import java.time.LocalDate;
import java.time.Period;

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
        if (!isValidTeacherId(teacherId)) {
            throw new IllegalArgumentException(
                    "ID преподавателя должен быть больше нуля"
            );
        }

        if (!isValidLastName(lastName)) {
            throw new IllegalArgumentException(
                    "Фамилия обязательна: до 100 символов, "
                            + "буквы, пробелы, дефисы и апострофы"
            );
        }

        if (!isValidFirstName(firstName)) {
            throw new IllegalArgumentException(
                    "Имя обязательно: до 100 символов, "
                            + "буквы, пробелы, дефисы и апострофы"
            );
        }

        if (!isValidPatronymic(patronymic)) {
            throw new IllegalArgumentException(
                    "Отчество: до 100 символов, "
                            + "буквы, пробелы, дефисы и апострофы; "
                            + "при отсутствии передайте пустую строку"
            );
        }

        if (!isValidBirthDate(birthDate)) {
            throw new IllegalArgumentException(
                    "Дата рождения обязательна "
                            + "и должна быть раньше сегодняшней даты"
            );
        }

        if (!isValidAcademicDegree(academicDegree)) {
            throw new IllegalArgumentException(
                    "Учёная степень: до 150 символов; "
                            + "при отсутствии передайте пустую строку"
            );
        }

        if (!isValidAdministrativePosition(administrativePosition)) {
            throw new IllegalArgumentException(
                    "Административная должность: до 150 символов; "
                            + "при отсутствии передайте пустую строку"
            );
        }

        if (!isValidExperienceYears(experienceYears, birthDate)) {
            throw new IllegalArgumentException(
                    "Стаж должен быть неотрицательным "
                            + "и не превышать возраст преподавателя"
            );
        }

        if (!isValidPhone(phone)) {
            throw new IllegalArgumentException(
                    "Телефон должен содержать + и от 8 до 15 цифр"
            );
        }

        if (!isValidEmail(email)) {
            throw new IllegalArgumentException(
                    "Email должен иметь формат имя@домен.зона "
                            + "и содержать не более 254 символов"
            );
        }

        this.teacherId = teacherId;
        this.lastName = lastName.strip();
        this.firstName = firstName.strip();
        this.patronymic = patronymic.strip();
        this.birthDate = birthDate;
        this.academicDegree = academicDegree.strip();
        this.administrativePosition = administrativePosition.strip();
        this.experienceYears = experienceYears;
        this.phone = phone.strip();
        this.email = email.strip();
    }

    // Статические методы валидации
    public static boolean isValidTeacherId(int teacherId) {
        return teacherId > 0;
    }

    public static boolean isValidLastName(String lastName) {
        if (lastName == null) {
            return false;
        }

        String value = lastName.strip();

        return !value.isEmpty()
                && value.length() <= 100
                && value.matches("\\p{L}+(?:[ '\\-]\\p{L}+)*");
    }

    public static boolean isValidFirstName(String firstName) {
        if (firstName == null) {
            return false;
        }

        String value = firstName.strip();

        return !value.isEmpty()
                && value.length() <= 100
                && value.matches("\\p{L}+(?:[ '\\-]\\p{L}+)*");
    }

    public static boolean isValidPatronymic(String patronymic) {
        if (patronymic == null) {
            return false;
        }

        String value = patronymic.strip();

        return value.isEmpty()
                || (value.length() <= 100
                    && value.matches("\\p{L}+(?:[ '\\-]\\p{L}+)*"));
    }

    public static boolean isValidBirthDate(LocalDate birthDate) {
        return birthDate != null
                && birthDate.isBefore(LocalDate.now());
    }

    public static boolean isValidAcademicDegree(String academicDegree) {
        if (academicDegree == null) {
            return false;
        }

        return academicDegree.strip().length() <= 150;
    }

    public static boolean isValidAdministrativePosition(
            String administrativePosition
    ) {
        if (administrativePosition == null) {
            return false;
        }

        return administrativePosition.strip().length() <= 150;
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

        return experienceYears >= 0
                && experienceYears <= age;
    }

    public static boolean isValidPhone(String phone) {
        if (phone == null) {
            return false;
        }

        return phone.strip().matches("\\+[0-9]{8,15}");
    }

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }

        String value = email.strip();

        return value.length() <= 254
                && value.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+");
    }

    // Геттеры: доступ к данным только для чтения

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