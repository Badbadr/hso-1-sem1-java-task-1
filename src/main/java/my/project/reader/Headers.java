package my.project.reader;

import lombok.Getter;

public enum Headers {
    EMPLOYEE("employee", "my.project.model.Employee", null, null),
    COMPANY("company", "my.project.model.Company", EMPLOYEE, null),
    INDIVIDUAL("individual", "my.project.model.Individual", EMPLOYEE, null),
    BANK_ACCOUNT("bank account", "my.project.model.BankAccount", null, EMPLOYEE),
    PAYMENT("payment", "my.project.model.Payment", null, null);

    private final String name;
    @Getter
    private final String qualifiedClassName;
    @Getter
    private final Headers parentClass;
    @Getter
    private final Headers filedOf;

    Headers(String name, String qualifiedClassName, Headers parentClass, Headers filedOf) {
        this.name = name;
        this.qualifiedClassName = qualifiedClassName;
        this.parentClass = parentClass;
        this.filedOf = filedOf;
    }

    public static Headers of(String headerName) {
        for(Headers f : values()){
            if (f.name.equals(headerName.toLowerCase().strip())){
                return f;
            }
        }
        throw new IllegalArgumentException("Unknown header: " + headerName);
    }
}
