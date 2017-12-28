package pl.com.bottega.hrs.ui;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.events.SalaryChangedEvent;
import pl.com.bottega.hrs.model.repositories.EmployeeRepository;

@Component
@ConditionalOnProperty(name = "hrs.notificationsEnabled", havingValue = "true")
public class SalaryChangedNotifier {

    private EmployeeRepository employeeRepository;

    public SalaryChangedNotifier(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @TransactionalEventListener
    public void salaryChanged(SalaryChangedEvent event) {
        Logger.getLogger(SalaryChangedNotifier.class).info("Starting email sending");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {e.printStackTrace();}

            Employee employee = employeeRepository.get(event.getEmpNo());
            Logger.getLogger(SalaryChangedNotifier.class).info("Sending email to " + employee.getLastName());
        }
    }
