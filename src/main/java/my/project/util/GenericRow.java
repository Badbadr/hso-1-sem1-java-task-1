package my.project.util;

import my.project.mapper.GeneralMapper;
import my.project.reader.Headers;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class GenericRow {

    private final CSVRecord csvSrc;
    private final Row excelSrc;

    public GenericRow(Object source) {
        if (source instanceof CSVRecord csvRecord) {
            csvSrc = csvRecord;
            excelSrc = null;
        } else if (source instanceof Row row) {
            excelSrc = row;
            csvSrc = null;
        } else {
            throw new IllegalArgumentException("Unsupported type of row");
        }
    }

    public String get(int index) {
        if (csvSrc != null) {
            return csvSrc.get(index);
        } else {
            var cell = excelSrc.getCell(index);
            if (cell == null) {
                return null;
            }
            return switch (cell.getCellType()) {
                case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
                case STRING -> cell.getStringCellValue();
                case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
                case BLANK -> "";
                default -> throw new IllegalArgumentException("Unsupported column type: " + cell.getCellType());
            };
        }
    }

    public int size() {
        return csvSrc != null? csvSrc.size(): Objects.requireNonNull(excelSrc).getLastCellNum();
    }

    public Map<Headers, LinkedHashMap<String, String>> getData(GeneralMapper mapper) {
        Map<Headers, LinkedHashMap<String, String>> result = new HashMap<>();
        for (Headers value : Headers.values()) {
            result.put(value, new LinkedHashMap<>());
        }

        long cellsNum = csvSrc != null ? csvSrc.size(): Objects.requireNonNull(excelSrc).getLastCellNum();

        for (int i = 0; i < cellsNum; i++) {
            var entityType = mapper.getEntityForColumnIndex(i);
            if (entityType != null) {
                result.get(entityType).put(mapper.getEntityColumnMapping(entityType).get(i), this.get(i));
            }
        }

        return result;
    }

    public boolean isEmpty() {
        for (var cell: excelSrc) {
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }
}
