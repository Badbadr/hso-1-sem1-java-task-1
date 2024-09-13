package my.project.model;

import lombok.Getter;

@Getter
public class Individual extends Employee {

    private final String firstName;
    private final String lastName;
    private final boolean haveKids;
    private final int age;

    public Individual(long id, String email, String phone, String address, BankAccount bankAccount,
                      String firstName, String lastName, boolean haveKids, int age) {
        super(id, email, phone, address, bankAccount);
        this.firstName = firstName;
        this.lastName = lastName;
        this.haveKids = haveKids;
        this.age = age;
    }
}
