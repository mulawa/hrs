package pl.com.bottega.hrs.model.commands;

public class AddDepartmentCommand implements Command {

    private String number, name;

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void validate(ValidationErrors errors){
        validatePresence(errors, "number", number);
        validatePresence(errors, "name", name);
    }
}
