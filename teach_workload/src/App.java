import java.time.LocalDate;

public class App {

    public static void main(String[] args) {
        Teacher teacher = new Teacher(
                1,
                "Иванов",
                "Иван",
                "Иванович",
                LocalDate.of(1985, 3, 15),
                "Кандидат технических наук",
                "Заведующий кафедрой",
                15,
                "+79991234567",
                "ivanov@example.com"
        );

        System.out.println("Полная версия:");
        System.out.println(teacher);

        Mapper mapper = new Mapper();
        ShortTeacher shortTeacher = mapper.toShortTeacher(teacher);

        System.out.println();

        System.out.println("Краткая версия:");
        System.out.println(shortTeacher);
    }
}