package my.project.mapper;

import my.project.model.BankAccount;
import org.apache.commons.csv.CSVRecord;

import java.util.Map;


public class BankAccountMapper extends FieldMapper implements Mapper {

    public BankAccountMapper(Map<String, Integer> fieldToColumnMap) {
        super(fieldToColumnMap);
    }

    @Override
    public BankAccount map(CSVRecord data) {
        return new BankAccount(
            data.get(fieldToColumnMap.get("iban")),
            data.get(fieldToColumnMap.get("bic")),
            data.get(fieldToColumnMap.get("account holder"))
        );
    }
}
