package pl.com.bottega.hrs.ui;

import pl.com.bottega.hrs.model.Employee;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

public class MainScreen {

    private Scanner scanner;
    private EntityManager entityManager;

    public MainScreen(Scanner scanner, EntityManager entityManager){

        this.scanner = scanner;
        this.entityManager = entityManager;
    }

    public Employee findEmployee() {
       while (true) {
           System.out.println("Podaj imię: ");
           String firstName = scanner.nextLine();
           System.out.println("Podaj nazwisko: ");
           String lastName = scanner.nextLine();
           entityManager.getTransaction();
           List<Employee> employees = entityManager.createQuery("SELECT e FROM Employee e WHERE e.firstName LIKE :firstName AND e.lastName LIKE :lastName").
                   setParameter("firstName", firstName).
                   setParameter("lastName", lastName + "%").
                   getResultList();
           for(Employee employee : employees)
               System.out.println(employee);
           selectedEmployee(employees);
           entityManager.close();
       }
    }
    private Employee selectedEmployee(List<Employee> employees) {
        int employeeNumber;
        do {
            System.out.println("Podaj numer pracownika: ");
            employeeNumber = scanner.nextInt();
        } while ((employeeNumber<1) || (employeeNumber>employees.size()));
            return employees.get(employeeNumber -1);
        }
    }
