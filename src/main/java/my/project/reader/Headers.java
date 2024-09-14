package my.project.reader;

public enum Headers {
    EMPLOYEE("employee"),
    COMPANY("company"),
    INDIVIDUAL("individual"),
    BANK_ACCOUNT("bank account");

    private final String name;

    Headers(String name) {
        this.name = name;
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
