package my.project.mapper;

import my.project.reader.Headers;

import java.util.Map;

public class MapperFactory {

    private MapperFactory() {}

    public static FieldMapper create(Headers entityType, Map<String, Integer> fieldToColumnMap) {
        return switch (entityType) {
            case COMPANY -> new CompanyMapper(fieldToColumnMap);
            case INDIVIDUAL -> new IndividualMapper(fieldToColumnMap);
            case EMPLOYEE ->  new EmployeeMapper(fieldToColumnMap);
            case BANK_ACCOUNT -> new BankAccountMapper(fieldToColumnMap);
        };
    }
}
