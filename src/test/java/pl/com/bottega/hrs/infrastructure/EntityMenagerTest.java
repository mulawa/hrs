package pl.com.bottega.hrs.infrastructure;

import org.hibernate.LazyInitializationException;
import org.junit.Test;
import pl.com.bottega.hrs.model.Address;
import pl.com.bottega.hrs.model.Employee;

import java.time.LocalDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class EntityMenagerTest extends InfrastructureTest{

    @Test
    public void tracksChangesToEntities() { //kazde zdarzenie obsluguje nowy menager
        //given - dany pracownik zapisany do bazy danych, dodaje pracownika commituje
        executeInTransaction((em) -> {
            Employee employee = createEmployee("Jan");
            em.persist(employee); //uzywanie persist zeby stworzyc
        });

        //when - pobiera w transakcji pracownika, cos mu robimy i commitujemy
        executeInTransaction((em) -> {
            Employee employee = em.find(Employee.class, 1); //zapytanie do bazy danych i zwrocony nowy obiekt
            updateFirstName("Janusz", employee);
            //em.detach(employee); //odlaczanie pracownika od sledzenia, zmiany nie sa juz sledzone
        });

        //then - pobieramy i sprawdzamy
        executeInTransaction((em) -> {
            Employee employee = em.find(Employee.class, 1);
            assertEquals("Janusz", employee.getFirstName());
        });
    }

    @Test
    public void mergesEntities() {
        //dodajemy nowego pracownika do bazy danych
        executeInTransaction((em) -> {
            Employee employee = createEmployee("Jan");
            em.persist(employee);
        });
        //tworzenie nowego pracownika w pamieci
        executeInTransaction((em) -> {
            Employee employee = createEmployee("Janusz");
            Employee employeeCopy = em.merge(employee); //merge zrobi kopie i te kopie zwroci, kopia jest zarzadzana a employee
            //nie jest zarzadzane, merge powoduje ze w pamieci jest dwoch januszow
            updateFirstName("Stefan", employee);
            updateFirstName("Eustachy", employeeCopy);
        });

        executeInTransaction((em) -> {
            Employee employee = em.find(Employee.class, 1);
            assertEquals("Eustachy", employee.getFirstName());
        });
    }

    @Test
    public void removeEntities() {
        //given
        executeInTransaction((em) -> {
            Employee employee = createEmployee("Jan");
            em.persist(employee);
        });

        executeInTransaction((em) -> {
            Employee employee = em.find(Employee.class, 1);
            em.remove(employee);
        });

        executeInTransaction((em) -> {
            Employee employee = em.find(Employee.class, 1);
            assertNull(employee);
        });
    }

    @Test
    public void cascadesOperations() {
        executeInTransaction((em) -> {
            Employee employee = createEmployee("Janek");
            em.persist(employee);
        });

        executeInTransaction((em) -> {
            Address address = em.find(Address.class, 1);
            assertNotNull(address);
        });

    }
    private Employee tmpEmployee;

    @Test(expected = LazyInitializationException.class) //to jest spodziewane ze wyskoczy ten wyjatek
    public void throwsLazyInitException() {
        executeInTransaction((em) -> {
            Employee employee = createEmployee("Janek");
            em.persist(employee);
        });

        executeInTransaction((em) -> {
            tmpEmployee = em.find(Employee.class, 1);
        });

        tmpEmployee.getSalaries().size();
    }

    private void updateFirstName(String newName, Employee employee){
        employee.updateProfile(newName, "Nowak", LocalDate.now());

    }
    private Employee createEmployee(String firstName) {
        Address address = new Address("al.Warszawska 10", "Lublin");
        return new Employee(1, firstName, "Nowak", LocalDate.now(), address, new StandardTimeProvider());
    }

}