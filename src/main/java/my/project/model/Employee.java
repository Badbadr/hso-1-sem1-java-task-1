package my.project.model;

public abstract class Employee implements Mappable {

    protected final long id;
    protected final String email;
    protected final String phone;
    protected final String address;
    protected final BankAccount bankAccount;

    public Employee(String id, String email, String phone, String address, String bankAccountBin,
                    String bankAccountBic, String bankAccountHolder) {
        this.id = Long.parseLong(id);
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.bankAccount = new BankAccount(bankAccountBin, bankAccountBic, bankAccountHolder);
    }
}
