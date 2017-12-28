package pl.com.bottega.hrs.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.hrs.model.Department;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.TimeProvider;
import pl.com.bottega.hrs.model.commands.AddEmployeeCommand;
import pl.com.bottega.hrs.model.commands.Command;
import pl.com.bottega.hrs.model.repositories.DepartmentRepository;
import pl.com.bottega.hrs.model.repositories.EmployeeRepository;

@Component
public class AddEmployeeHandler implements Handler<AddEmployeeCommand>{

    private EmployeeRepository repository;
    private TimeProvider timeProvider;
    private DepartmentRepository departmentRepository;

    public AddEmployeeHandler(EmployeeRepository repository, TimeProvider timeProvider,
                              DepartmentRepository departmentRepository) {

        this.repository = repository;
        this.timeProvider = timeProvider;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public void handle(AddEmployeeCommand cmd){ //wykonaj
        Employee employee = new Employee(
                repository.generateNumber(),
                cmd.getFirstName(),
                cmd.getLastName(),
                cmd.getBirthDate(),
                cmd.getAddress(),
                timeProvider
        );
        employee.changeSalary(cmd.getSalary());
        employee.changeTitle(cmd.getTitle());
        Department department = departmentRepository.get(cmd.getDeptNo());
        employee.assignDepartment(department);
        repository.save(employee);
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return AddEmployeeCommand.class;
    }
}
