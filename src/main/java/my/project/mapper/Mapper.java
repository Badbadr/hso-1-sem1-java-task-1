package my.project.mapper;

import my.project.model.Mappable;
import org.apache.commons.csv.CSVRecord;

public interface Mapper {

    Mappable map(CSVRecord data);
}
