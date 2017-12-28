package pl.com.bottega.hrs.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.hrs.model.Employee;
import pl.com.bottega.hrs.model.Employee_;
import pl.com.bottega.hrs.model.repositories.EmployeeRepository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Component
public class JPAEmployeeRepository implements EmployeeRepository {

    private EntityManager entityManager;

    public JPAEmployeeRepository(EntityManager entityManager) {

        this.entityManager = entityManager;
    }

    @Override
    public Integer generateNumber() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery();
        Root employee = criteriaQuery.from(Employee.class);
        criteriaQuery.select(criteriaBuilder.max(employee.get(Employee_.empNo)));
        Query query = entityManager.createQuery(criteriaQuery);
        Integer result = (Integer) query.getSingleResult();
        if (result == null) {
            return 1;
        }
        return result + 1;
    }

    @Override
    public void save(Employee employee) {
        entityManager.persist(employee);
    }

    @Override
    public Employee get(Integer empNo) {
        Employee employee = entityManager.find(Employee.class, empNo, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        if (employee == null)
            throw new NoSuchEntityException();
        return employee;
    }
}