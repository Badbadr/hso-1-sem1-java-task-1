package my.project.mapper;

import my.project.model.Mappable;
import my.project.util.GenericRow;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Row;

public interface Mapper {

    Mappable map(GenericRow data);
}
