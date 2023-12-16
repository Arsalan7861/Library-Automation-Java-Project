public class NormalUser extends User{
    public NormalUser(String name, String email, int age, String userID, String password) {
        super(name, email, age, userID, password);
    }
    public NormalUser(){

    }

    @Override
    public void displayInfo() {
        System.out.println("ID : " + getUserID());
        System.out.println("Name : " + getName());
        System.out.println("Email : " + getEmail());
    }
}
