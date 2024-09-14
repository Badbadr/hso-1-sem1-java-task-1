package my.project.mapper;

import my.project.util.GenericRow;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class CompanyMapper extends FieldMapper {

    public CompanyMapper(Map<String, Integer> fieldToColumnMap) {
        super(fieldToColumnMap);
    }

    public boolean isCompany(GenericRow data) {
        Integer companyColumnIndex = this.getFieldToColumnMap().get("company name");
        String companyName = data.get(companyColumnIndex);
        return !StringUtils.isBlank(companyName);
    }

}
