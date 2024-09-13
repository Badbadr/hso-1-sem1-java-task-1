package my.project.model;

import lombok.Data;

@Data
public abstract class Employee implements Mappable {

    protected final long id;
    protected final String email;
    protected final String phone;
    protected final String address;
    protected final BankAccount bankAccount;
}
