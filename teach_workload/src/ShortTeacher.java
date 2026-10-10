public final class ShortTeacher {

    private final int teacherId;
    private final String lastName;
    private final String initials;
    private final String email;

    public ShortTeacher(
            int teacherId,
            String lastName,
            String initials,
            String email
    ) {
        if (!Teacher.isValidTeacherId(teacherId)) {
            throw new IllegalArgumentException(
                    "ID должен быть больше нуля"
            );
        }

        if (!Teacher.isValidLastName(lastName)) {
            throw new IllegalArgumentException(
                    "Некорректная фамилия"
            );
        }

        if (initials == null
                || !initials.strip().matches("\\p{L}\\.(?:\\p{L}\\.)?")) {
            throw new IllegalArgumentException(
                    "Инициалы должны иметь формат И. или И.И."
            );
        }

        if (!Teacher.isValidEmail(email)) {
            throw new IllegalArgumentException(
                    "Некорректный email"
            );
        }

        this.teacherId = teacherId;
        this.lastName = lastName.strip();
        this.initials = initials.strip();
        this.email = email.strip();
    }

    public int getTeacherId() {
        return teacherId;
    }

    public String getLastName() {
        return lastName;
    }

    public String getInitials() {
        return initials;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return teacherId + ": " + lastName + " "
                + initials + " (" + email + ")";
    }
}