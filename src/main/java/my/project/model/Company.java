package my.project.model;

import lombok.Getter;

@Getter
public class Company extends Employee {

    private final String name;
    private final CompanyType type;

    public Company(long id, String email, String phone, String address, BankAccount bankAccount,
                   String name, String type) {
        super(id, email, phone, address, bankAccount);
        this.name = name;
        this.type = CompanyType.of(type);
    }

    public enum CompanyType {
        TYPE1("type1"),
        TYPE2("type2");

        private final String name;

        CompanyType(String name) {
            this.name = name;
        }

        public static CompanyType of(String stringFormat) {
            for(CompanyType f : values()){
                if (f.name.equals(stringFormat)){
                    return f;
                }
            }
            return null;
        }
    }

}
