package my.project.util;

import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

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
            return switch (cell.getCellType()) {
                case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
                case STRING -> cell.getStringCellValue();
                case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
                case BLANK -> "";
                default -> throw new IllegalArgumentException("Unsupported column type: " + cell.getCellType());
            };
        }
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
