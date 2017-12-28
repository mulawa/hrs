package pl.com.bottega.hrs;

import pl.com.bottega.hrs.ui.MainScreen;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class App3 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HRS");
        EntityManager em = emf.createEntityManager();
        MainScreen mainScreen = new MainScreen(scanner, em);
        mainScreen.findEmployee();
    }
}
