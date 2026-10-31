public class ShortTeacher {

    protected final String name;
    protected final String phone;

    public ShortTeacher(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String toShortString() {
        return name + ", телефон: " + phone;
    }
}