package my.project.reader;

import lombok.Getter;
import my.project.mapper.BankAccountMapper;
import my.project.mapper.CompanyMapper;
import my.project.mapper.EmployeeMapper;
import my.project.mapper.IndividualMapper;
import my.project.model.Employee;
import my.project.util.GenericRow;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class ExcelReader extends MyReader {

    private BankAccountMapper bankAccountMapper;
    private EmployeeMapper employeeMapper;
    private CompanyMapper companyMapper;
    private IndividualMapper individualMapper;

    ExcelReader(String pathToFile, FileFormat format) {
        super(pathToFile, format);
    }

    @Override
    protected List<Employee> read() {
        try (FileInputStream file = new FileInputStream(getPathToFile())) {
            Workbook workbook = FileFormat.XLSX.equals(getFormat()) ? new XSSFWorkbook(file): new HSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> iterator = sheet.rowIterator();
            var entityGroupNames = iterator.next();
            var entityFieldNames = iterator.next();
            var groupColumns = getGroupColumns(entityGroupNames, entityFieldNames);
            defineMappers(groupColumns, entityFieldNames);

            List<Employee> employees = new ArrayList<>();
            while (iterator.hasNext()) {
                var data = new GenericRow(iterator.next());
                if (data.isEmpty()) {
                    break;
                }
                employees.add(employeeMapper.map(data));
            }

            return employees;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("There is no such file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void defineMappers(Map<String, List<Integer>> groupColumns, Row entityFieldNames) {
        for (String key: groupColumns.keySet()) {
            var entityType = Headers.of(key);
            var fieldToColumnMap = groupColumns.get(key).stream().collect(Collectors.toMap(
                    index -> entityFieldNames.getCell(index).getStringCellValue().toLowerCase().strip(), Function.identity()));

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

    private Map<String, List<Integer>> getGroupColumns(Row entityGroupNames, Row entityFieldNames) {
        String currentGroup = entityGroupNames.getCell(0).getStringCellValue();
        Map<String, List<Integer>> groupToColumnsMap = new HashMap<>();

        for (int i = 0; i < entityFieldNames.getLastCellNum(); i++) {
            var possibleFieldName = entityFieldNames.getCell(i);
            if (possibleFieldName == null || StringUtils.isBlank(possibleFieldName.getStringCellValue())) {
                continue;
            }

            if (entityGroupNames.getCell(i) != null && !StringUtils.isBlank(entityGroupNames.getCell(i).getStringCellValue())) {
                currentGroup = entityGroupNames.getCell(i).getStringCellValue();
            }
            List<Integer> integers = groupToColumnsMap.putIfAbsent(currentGroup, new ArrayList<>(Arrays.asList(i)));
            if (integers != null) {
                integers.add(i);
            }
        }

        return groupToColumnsMap;
    }
}
