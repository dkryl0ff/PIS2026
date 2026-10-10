public final class Mapper {

    public ShortTeacher toShortTeacher(Teacher teacher) {
        if (teacher == null) {
            throw new IllegalArgumentException(
                    "Преподаватель не может быть null"
            );
        }

        String initials = toInitial(teacher.getFirstName());

        if (!teacher.getPatronymic().isEmpty()) {
            initials += toInitial(teacher.getPatronymic());
        }

        return new ShortTeacher(
                teacher.getTeacherId(),
                teacher.getLastName(),
                initials,
                teacher.getEmail()
        );
    }

    private static String toInitial(String name) {
        int endIndex = name.offsetByCodePoints(0, 1);

        return name.substring(0, endIndex) + ".";
    }
}
