public class Admin extends User{
    private String status;

    public Admin(int userID,String name, String email, int age, String password, String status) {
        super(userID, name, email, age, password);
        this.status = status;
    }
}
