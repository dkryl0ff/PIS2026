import com.google.gson.JsonObject;

import java.time.LocalDate;

public class App {

    public static void main(String[] args) {
        Teacher teacher = new Teacher(
                "Морозова",
                "Елена",
                "Викторовна",
                LocalDate.of(1987, 11, 21),
                "Кандидат технических наук",
                "Доцент",
                14,
                "+79161234567",
                "morozova@example.com"
        );

        System.out.println("Основной преподаватель:");
        System.out.println(teacher.toFullString());

        // Те же данные получаем из строки
        String line = "Морозова;Елена;Викторовна;1987-11-21;"
                + "Кандидат технических наук;Доцент;14;"
                + "+79161234567;morozova@example.com";

        Teacher teacherFromLine = new Teacher(line);

        System.out.println();
        System.out.println(
                "Запись из строки совпадает: "
                        + teacher.isSameTeacher(teacherFromLine)
        );

        JsonObject json = new JsonObject();
        json.addProperty("last_name", "Орлов");
        json.addProperty("first_name", "Сергей");
        json.addProperty("patronymic", "Олегович");
        json.addProperty("birth_date", "1992-04-17");
        json.addProperty("academic_degree", "");
        json.addProperty("administrative_position", "");
        json.addProperty("experience_years", 8);
        json.addProperty("phone", "+79265554433");
        json.addProperty("email", "orlov@example.com");

        Teacher anotherTeacher = new Teacher(json);

        System.out.println();
        System.out.println("Преподаватель из JSON:");
        System.out.println(anotherTeacher.toFullString());

        System.out.println(
                "Совпадает с основным преподавателем: "
                        + teacher.isSameTeacher(anotherTeacher)
        );

        System.out.println();
        System.out.println(
                "Текущие контакты: "
                        + teacher.getPhone() + ", "
                        + teacher.getEmail()
        );

        teacher.setEmail("e.morozova@example.com");
        teacher.setAdministrativePosition("Заведующий кафедрой");

        System.out.println("После изменений:");
        System.out.println(teacher.toFullString());

        System.out.println();
        System.out.println(
                "Проверка телефона: "
                        + Teacher.isValidPhone(teacher.getPhone())
        );

        Mapper mapper = new Mapper();
        ShortTeacher shortTeacher = mapper.toShortTeacher(teacher);

        System.out.println();
        System.out.println("Краткая версия:");
        System.out.println(shortTeacher.toShortString());

        System.out.println(
                "Контакт для связи с "
                        + shortTeacher.getName() + ": "
                        + shortTeacher.getPhone()
        );
    }
}