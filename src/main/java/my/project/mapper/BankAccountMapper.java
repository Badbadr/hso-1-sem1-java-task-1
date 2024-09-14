package my.project.mapper;

import my.project.model.BankAccount;
import my.project.util.GenericRow;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Row;

import java.util.Map;


public class BankAccountMapper extends FieldMapper implements Mapper {

    public BankAccountMapper(Map<String, Integer> fieldToColumnMap) {
        super(fieldToColumnMap);
    }

    @Override
    public BankAccount map(GenericRow data) {
        return new BankAccount(
            data.get(fieldToColumnMap.get("iban")),
            data.get(fieldToColumnMap.get("bic")),
            data.get(fieldToColumnMap.get("account holder"))
        );
    }
}
