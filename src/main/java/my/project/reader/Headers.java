package my.project.reader;

import lombok.Getter;

public enum Headers {
    EMPLOYEE("employee", "Employee"),
    COMPANY("company", "Company"),
    INDIVIDUAL("individual", "my.project.model.Individual"),
    BANK_ACCOUNT("bank account", "BankAccount"),
    PAYMENT("payment", "Payment");

    private final String name;
    @Getter
    private final String qualifiedClassName;

    Headers(String name, String qualifiedClassName) {
        this.name = name;
        this.qualifiedClassName = qualifiedClassName;
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
