public class Borrowers extends NormalUser{

    public Borrowers(int userID,String name, String email, int age, String password) {
        super(userID,name, email, age,password);
    }

    @Override
    public void displayInfo() {
        System.out.println("ID : " + getUserID());
        System.out.println("Name : " + getName());
    }
}