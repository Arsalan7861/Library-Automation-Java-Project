public abstract class User{
    private int userID;
    private String name;
    private String email;
    private int age;
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public int getAge() {
        return age;
    }
    public int getUserID() {
        return userID;
    }

    public User(int userID,String name, String email, int age, String password) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.age = age;
        this.password = password;
    }
    public User(String name, String email, int age, String password){
        this.name = name;
        this.email = email;
        this.age = age;
        this.password = password;

    }
    public User(){

    }
    public String toString() {
        return "User ID: " + getUserID() +
                ", Name: " + getName() +
                ", Email: " + getEmail() +
                ", Age: " + getAge() +
                ", Password: " + getPassword();
    }
}
