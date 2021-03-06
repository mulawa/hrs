package pl.com.bottega.hrs.model.commands;

public class UnassignDepartmentCommand implements Command{

    private Integer empNo;
    private String deptNo;

    public Integer getEmpNo() {
        return empNo;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public void validate(ValidationErrors errors){
        validatePresence(errors, "empNo",empNo);
        validatePresence(errors, "deptNo", deptNo);
    }
}
