public abstract class User{
    private String name;
    private String email;
    private int age;
    private String userID;
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public User(String name, String email, int age, String userID, String password) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.userID = userID;
        this.password = password;
    }
    public User(){

    }

    public abstract void displayInfo();
}
