class User {
    private Int age;
    private String name;

    public User(Int age, String name) {
        this.age = age;
        this.name = name;
    }

    public Int getAge() {
        return age;
    }

    public void setAge(Int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        User user = (User) object;
        return java.util.Objects.equals(age, user.age) && java.util.Objects.equals(name, user.name);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), age, name);
    }
}