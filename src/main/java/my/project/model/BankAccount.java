package my.project.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
public class BankAccount implements Mappable{

    private final String iban;
    private final String bic;
    private final String accountHolder;

    public BankAccount(String iban, String bic, String accountHolder) {
        this.iban = iban;
        this.bic = bic;
        this.accountHolder = accountHolder;
    }

    public BankAccount(List<String> args) {
        this(args.get(0), args.get(1), args.get(2));
    }
}
