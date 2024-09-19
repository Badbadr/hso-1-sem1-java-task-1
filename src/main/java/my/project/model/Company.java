package my.project.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Company extends Employee {

    private final String name;
    private final String type;

    public Company(String id, String email, String phone, String address, String bankAccountBin,
                   String bankAccountBic, String bankAccountHolder, String name, String type) {
        super(id, email, phone, address, bankAccountBin, bankAccountBic, bankAccountHolder);
        this.name = name;
        this.type = type;
    }

    public Company(List<String> args) {
        this(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4), args.get(5), args.get(6),
                args.get(7), args.get(8));
    }

}
