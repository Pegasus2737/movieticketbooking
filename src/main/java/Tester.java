public class Tester {
    private int number; // Stores an integer in the class

    public Tester() {
        number = 0; // Initialize the integer to 0
    }

    public int getNumber() {
        return number; // Return the integer stored in the class
    }

    public void setNumber(int number) {
        this.number = number; // Set the integer stored in the class
    }

    public static void main(String[] args) {
        System.out.println("Hello World");
        Tester tester = new Tester();
        System.out.println(tester.getNumber());
        tester.setNumber(5);
        System.out.println(tester.getNumber());
    }
}