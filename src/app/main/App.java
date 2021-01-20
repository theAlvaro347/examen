package app.main;
import app.core.Cuenta;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        System.out.println("ATDD para 1DAW3 Plaiaundi"); 

        Cuenta cta = new Cuenta();
        System.out.println("saldo en cta de "+cta.getSaldo()+"€");
        
    }
}
