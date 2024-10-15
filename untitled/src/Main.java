import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Gaurav();
        System.out.println(Gaurav2(3,4));
        Gaurav3(3,4);
        System.out.println(Gaurav4());
    }

    public static void Gaurav() {
        System.out.println("Gaurav Patel");
    }
    public static int Gaurav2(int a, int b){
        return a + b;
    }
    public static void Gaurav3(int a, int b){
        System.out.println(a+b);
    }
    public static String Gaurav4(){
        return "My name is Gaurav";
    }
}
