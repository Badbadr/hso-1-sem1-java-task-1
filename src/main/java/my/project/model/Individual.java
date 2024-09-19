package my.project.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Individual extends Employee {

    private final String firstName;
    private final String lastName;
    private final boolean haveKids;
    private final int age;

    public Individual(String id, String email, String phone, String address, String bankAccountBin,
                      String bankAccountBic, String bankAccountHolder, String firstName, String lastName,
                      String haveKids, String age) {
        super(id, email, phone, address, bankAccountBin, bankAccountBic, bankAccountHolder);
        this.firstName = firstName;
        this.lastName = lastName;
        this.haveKids = Boolean.getBoolean(haveKids);
        this.age = Integer.parseInt(age);
    }

    public Individual(List<String> args) {
        this(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5), args.get(6),
                args.get(7), args.get(8), args.get(9), args.get(10));
    }
}
