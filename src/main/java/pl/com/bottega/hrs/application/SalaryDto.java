package pl.com.bottega.hrs.application;

import pl.com.bottega.hrs.model.Salary;

import java.time.LocalDate;

public class SalaryDto {

    private LocalDate fromDate, toDate;

    private Integer salary;

    public SalaryDto(Salary salary){
        this.fromDate = salary.getFromDate();
        this.toDate = salary.getFromDate();
        this.salary = salary.getValue();

    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}
