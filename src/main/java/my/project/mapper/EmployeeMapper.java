package my.project.mapper;

import lombok.Setter;
import my.project.model.Company;
import my.project.model.Employee;
import my.project.model.Individual;
import org.apache.commons.csv.CSVRecord;

import java.util.Map;


@Setter
public class EmployeeMapper extends FieldMapper implements Mapper {

    private IndividualMapper individualMapper;
    private CompanyMapper companyMapper;
    private BankAccountMapper bankAccountMapper;

    public EmployeeMapper(Map<String, Integer> fieldToColumnMap) {
        super(fieldToColumnMap);
    }

    @Override
    public Employee map(CSVRecord data) {
        if (individualMapper != null && companyMapper != null && bankAccountMapper != null) {
            if (companyMapper.isCompany(data)) {
                return new Company(
                        Long.parseLong(data.get(fieldToColumnMap.get("id"))),
                        data.get(fieldToColumnMap.get("email")),
                        data.get(fieldToColumnMap.get("phone")),
                        data.get(fieldToColumnMap.get("address")),
                        bankAccountMapper.map(data),
                        data.get(companyMapper.getFieldToColumnMap().get("company name")),
                        data.get(companyMapper.getFieldToColumnMap().get("type"))
                );
            } else {
                return new Individual(
                        Long.parseLong(data.get(fieldToColumnMap.get("id"))),
                        data.get(fieldToColumnMap.get("email")),
                        data.get(fieldToColumnMap.get("phone")),
                        data.get(fieldToColumnMap.get("address")),
                        bankAccountMapper.map(data),
                        data.get(individualMapper.getFieldToColumnMap().get("first name")),
                        data.get(individualMapper.getFieldToColumnMap().get("last name")),
                        Boolean.parseBoolean(data.get(individualMapper.getFieldToColumnMap().get("has children"))),
                        Integer.parseInt(data.get(individualMapper.getFieldToColumnMap().get("age")))
                );
            }
        } else {
            throw new RuntimeException("Ohhhhh, so bad");
        }
    }
}
