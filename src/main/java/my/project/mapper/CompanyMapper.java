package my.project.mapper;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class CompanyMapper extends FieldMapper {

    public CompanyMapper(Map<String, Integer> fieldToColumnMap) {
        super(fieldToColumnMap);
    }

    public boolean isCompany(CSVRecord data) {
        return !StringUtils.isBlank(data.get(this.getFieldToColumnMap().get("company name")));
    }

}
