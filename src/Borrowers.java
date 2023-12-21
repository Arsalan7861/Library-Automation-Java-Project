public class Borrowers extends User{
    private double tax;

    public void setTax(double tax) {
        this.tax = tax;
    }
    public double getTax() {
        return tax;
    }

    public Borrowers(int userID,String name, String email, int age, String password, double tax) {
        super(userID,name, email, age,password);
        this.tax = tax;
    }

    @Override
    public void displayInfo() {
        System.out.println("ID : " + getUserID());
        System.out.println("Name : " + getName());
        System.out.println("The tax that must be paid : " + tax);
    }
}
