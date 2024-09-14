package my.project.reader;

import lombok.Getter;
import my.project.mapper.BankAccountMapper;
import my.project.mapper.CompanyMapper;
import my.project.mapper.EmployeeMapper;
import my.project.mapper.IndividualMapper;
import my.project.model.Employee;
import my.project.util.GenericRow;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
class CsvReader extends MyReader {

    private BankAccountMapper bankAccountMapper;
    private EmployeeMapper employeeMapper;
    private CompanyMapper companyMapper;
    private IndividualMapper individualMapper;

    CsvReader(String pathToFile, FileFormat format) {
        super(pathToFile, format);
    }

    @Override
    protected List<Employee> read() {
        try (Reader in = new FileReader(getPathToFile())) {
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(Headers.class)
                    .setSkipHeaderRecord(false)
                    .build();

            Iterable<CSVRecord> records = csvFormat.parse(in);

            Iterator<CSVRecord> iterator = records.iterator();
            var entityGroupNames = iterator.next();
            var entityFieldNames = iterator.next();
            var groupColumns = getGroupColumns(entityGroupNames, entityFieldNames);
            defineMappers(groupColumns, entityFieldNames);

            List<Employee> employees = new ArrayList<>();
            while (iterator.hasNext()) {
                var data = new GenericRow(iterator.next());
                employees.add(employeeMapper.map(data));
            }

            return employees;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
        Изначально не так понял задачу, думал что должно быть адаптировано под произвольные классы моделей, а не под
        произвольные классы чтения, проектировал под первый пункт, потом понел что тупанул
     */
    private void defineMappers(Map<String, List<Integer>> groupColumns, CSVRecord entityFieldNames) {
        for (String key: groupColumns.keySet()) {
            var entityType = Headers.of(key);
            var fieldToColumnMap = groupColumns.get(key).stream().collect(Collectors.toMap(
                    index -> entityFieldNames.get(index).toLowerCase().strip(), Function.identity()));

            if (entityType.equals(Headers.INDIVIDUAL)) {
                individualMapper = new IndividualMapper(fieldToColumnMap);
            } else if (entityType.equals(Headers.COMPANY)) {
                companyMapper = new CompanyMapper(fieldToColumnMap);
            } else if (entityType.equals(Headers.BANK_ACCOUNT)) {
                bankAccountMapper = new BankAccountMapper(fieldToColumnMap);
            } else {
                employeeMapper = new EmployeeMapper(fieldToColumnMap);
            }
        }
        employeeMapper.setCompanyMapper(companyMapper);
        employeeMapper.setIndividualMapper(individualMapper);
        employeeMapper.setBankAccountMapper(bankAccountMapper);
    }

    private Map<String, List<Integer>> getGroupColumns(CSVRecord entityGroupNames, CSVRecord entityFieldNames) {
        String currentGroup = entityGroupNames.get(0);
        Map<String, List<Integer>> groupToColumnsMap = new HashMap<>();

        for (int i = 0; i < entityFieldNames.size(); i++) {
            String possibleFieldName = entityFieldNames.get(i);
            if (StringUtils.isBlank(possibleFieldName)) {
                continue;
            }

            if (!StringUtils.isBlank(entityGroupNames.get(i))) {
                currentGroup = entityGroupNames.get(i);
            }
            List<Integer> integers = groupToColumnsMap.putIfAbsent(currentGroup, new ArrayList<>(Arrays.asList(i)));
            if (integers != null) {
                integers.add(i);
            }
        }

        return groupToColumnsMap;
    }
}
