package my.project.reader;

import lombok.Getter;
import my.project.util.GenericRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
public class ExcelReader extends MyReader {

    ExcelReader(String pathToFile, FileFormat format) {
        super(pathToFile, format);
    }

    @Override
    protected HashMap<Headers, List<LinkedHashMap<String, String>>> read() throws IOException {
        HashMap<Headers, List<LinkedHashMap<String, String>>> result = new HashMap<>();
        for (Headers value : Headers.values()) {
            result.put(value, new ArrayList<>());
        }

        try (FileInputStream file = new FileInputStream(getPathToFile())) {
            Workbook workbook = FileFormat.XLSX.equals(getFormat()) ? new XSSFWorkbook(file): new HSSFWorkbook(file);
            var sheetIterator = workbook.sheetIterator();
            Sheet sheet;
            while (sheetIterator.hasNext()) {
                sheet = sheetIterator.next();
                Iterator<Row> iterator = sheet.rowIterator();
                var entityGroupNames = new GenericRow(iterator.next());
                var entityFieldNames = new GenericRow(iterator.next());
                var groupColumns = getGroupColumns(entityGroupNames, entityFieldNames);
                defineMappers(groupColumns, entityFieldNames);

                while (iterator.hasNext()) {
                    var data = new GenericRow(iterator.next());
                    var some = data.getData(generalMapper);
                    if (data.isEmpty()) {
                        break;
                    }
                    for (var entry: some.entrySet()) {
                        if (!entry.getValue().isEmpty()) {
                            result.get(entry.getKey()).add(entry.getValue());
                        }
                    }
                }
            }

            return result;
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e2) {
            throw e2;
        }
    }
}
