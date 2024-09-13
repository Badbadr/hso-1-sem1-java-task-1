package my.project.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class BankAccount implements Mappable{

    private final String iban;
    private final String bic;
    private final String accountHolder;
}
