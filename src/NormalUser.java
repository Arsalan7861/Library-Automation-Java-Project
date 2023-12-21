public class NormalUser extends User{
    public NormalUser(){

    }
    public NormalUser(int userID,String name, String email, int age,  String password) {
        super(userID,name, email, age, password);
    }
    public NormalUser(String name, String email, int age,  String password){
        super(name,email,age,password);
    }

    @Override
    public void displayInfo() {
        System.out.println("ID : " + getUserID());
        System.out.println("Name : " + getName());
        System.out.println("Email : " + getEmail());
    }
}
