public class Mapper {

    public ShortTeacher toShortTeacher(Teacher teacher) {
        if (teacher == null) {
            return null;
        }

        String shortName = createShortName(teacher);

        return new ShortTeacher(
                shortName,
                teacher.getPhone()
        );
    }

    protected static String createShortName(Teacher teacher) {
        return teacher.getLastName() + " "
                + teacher.getFirstName().charAt(0) + "."
                + teacher.getPatronymic().charAt(0) + ".";
    }
}