import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

public final class Teacher {

    private static final int MAX_NAME_LENGTH = 100;
    private static final int MAX_TEXT_LENGTH = 150;
    private static final int MAX_EMAIL_LENGTH = 254;

    private static final String NAME_REGEX =
            "\\p{L}+(?:[ '\\-]\\p{L}+)*";

    private static final String PHONE_REGEX =
            "\\+[1-9][0-9]{7,14}";

    private static final String EMAIL_REGEX =
            "[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+"
                    + "(?:\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,63}";

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
        requireValid(
                isValidTeacherId(teacherId),
                "ID должен быть больше нуля"
        );

        requireValid(
                isValidLastName(lastName),
                "Некорректная фамилия"
        );

        requireValid(
                isValidFirstName(firstName),
                "Некорректное имя"
        );

        requireValid(
                isValidPatronymic(patronymic),
                "Некорректное отчество"
        );

        requireValid(
                isValidBirthDate(birthDate),
                "Некорректная дата рождения"
        );

        requireValid(
                isValidAcademicDegree(academicDegree),
                "Некорректная учёная степень"
        );

        requireValid(
                isValidAdministrativePosition(administrativePosition),
                "Некорректная административная должность"
        );

        requireValid(
                isValidExperienceYears(experienceYears, birthDate),
                "Стаж должен быть неотрицательным и не превышать возраст"
        );

        requireValid(
                isValidPhone(phone),
                "Некорректный телефон"
        );

        requireValid(
                isValidEmail(email),
                "Некорректный email"
        );

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

    public Teacher(String line) {
        this(parseLine(line));
    }

    private Teacher(String[] fields) {
        this(
                parseInteger(fields[0], "teacher_id"),
                fields[1],
                fields[2],
                fields[3],
                parseDate(fields[4]),
                fields[5],
                fields[6],
                parseInteger(fields[7], "experience_years"),
                fields[8],
                fields[9]
        );
    }

    public Teacher(JsonObject json) {
        this(
                readJsonInteger(json, "teacher_id"),
                readJsonString(json, "last_name"),
                readJsonString(json, "first_name"),
                readJsonString(json, "patronymic"),
                parseDate(readJsonString(json, "birth_date")),
                readJsonString(json, "academic_degree"),
                readJsonString(json, "administrative_position"),
                readJsonInteger(json, "experience_years"),
                readJsonString(json, "phone"),
                readJsonString(json, "email")
        );
    }

    public static boolean isValidTeacherId(int teacherId) {
        return teacherId > 0;
    }

    public static boolean isValidLastName(String lastName) {
        return isValidPersonName(lastName, false);
    }

    public static boolean isValidFirstName(String firstName) {
        return isValidPersonName(firstName, false);
    }

    public static boolean isValidPatronymic(String patronymic) {
        return isValidPersonName(patronymic, true);
    }

    public static boolean isValidBirthDate(LocalDate birthDate) {
        return birthDate != null
                && birthDate.isBefore(LocalDate.now());
    }

    public static boolean isValidAcademicDegree(String academicDegree) {
        return isValidOptionalText(academicDegree);
    }

    public static boolean isValidAdministrativePosition(
            String administrativePosition
    ) {
        return isValidOptionalText(administrativePosition);
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
        return phone != null
                && phone.strip().matches(PHONE_REGEX);
    }

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }

        String value = email.strip();

        return value.length() <= MAX_EMAIL_LENGTH
                && value.matches(EMAIL_REGEX);
    }

    private static boolean isValidPersonName(
            String name,
            boolean allowEmpty
    ) {
        if (name == null) {
            return false;
        }

        String value = name.strip();

        if (value.isEmpty()) {
            return allowEmpty;
        }

        return value.length() <= MAX_NAME_LENGTH
                && value.matches(NAME_REGEX);
    }

    private static boolean isValidOptionalText(String text) {
        if (text == null) {
            return false;
        }

        return text.strip().length() <= MAX_TEXT_LENGTH
                && text.codePoints()
                        .noneMatch(Character::isISOControl);
    }

    private static void requireValid(
            boolean valid,
            String message
    ) {
        if (!valid) {
            throw new IllegalArgumentException(message);
        }
    }

    private static String[] parseLine(String line) {
        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException("Пустая строка");
        }

        String[] fields = line.split(";", -1);

        if (fields.length != 10) {
            throw new IllegalArgumentException(
                    "Ожидалось 10 полей, получено: " + fields.length
            );
        }

        return fields;
    }

    private static int parseInteger(String value, String fieldName) {
        try {
            return Integer.parseInt(value.strip());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "Поле " + fieldName + " должно быть целым числом",
                    exception
            );
        }
    }

    private static LocalDate parseDate(String value) {
        try {
            return LocalDate.parse(value.strip());
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(
                    "Дата рождения должна быть корректной датой "
                            + "в формате ГГГГ-ММ-ДД",
                    exception
            );
        }
    }

    private static JsonPrimitive readJsonPrimitive(
            JsonObject json,
            String fieldName
    ) {
        if (json == null) {
            throw new IllegalArgumentException("JSON не может быть null");
        }

        JsonElement value = json.get(fieldName);

        if (value == null || value.isJsonNull()) {
            throw new IllegalArgumentException(
                    "В JSON отсутствует значение поля " + fieldName
            );
        }

        if (!value.isJsonPrimitive()) {
            throw new IllegalArgumentException(
                    "Поле " + fieldName + " должно содержать одно значение"
            );
        }

        return value.getAsJsonPrimitive();
    }

    private static String readJsonString(
            JsonObject json,
            String fieldName
    ) {
        JsonPrimitive value = readJsonPrimitive(json, fieldName);

        if (!value.isString()) {
            throw new IllegalArgumentException(
                    "Поле " + fieldName + " должно быть строкой"
            );
        }

        return value.getAsString();
    }

    private static int readJsonInteger(
            JsonObject json,
            String fieldName
    ) {
        JsonPrimitive value = readJsonPrimitive(json, fieldName);

        if (!value.isNumber()) {
            throw new IllegalArgumentException(
                    "Поле " + fieldName + " должно быть числом"
            );
        }

        try {
            return value.getAsBigDecimal().intValueExact();
        } catch (NumberFormatException | ArithmeticException exception) {
            throw new IllegalArgumentException(
                    "Поле " + fieldName
                            + " должно быть целым числом в диапазоне int",
                    exception
            );
        }
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