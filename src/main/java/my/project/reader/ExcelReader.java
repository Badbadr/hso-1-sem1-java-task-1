package my.project.reader;

import lombok.Getter;
import my.project.model.Employee;

import java.util.List;
import java.util.Set;


/**
 |Head1            |Head2      | empty |Head3            |
 |minh1|minh2|minh3|minh4|minh5| empty |minh6|minh7|minh8|

 **/

@Getter
public class ExcelReader extends MyReader {

    ExcelReader(String pathToFile) {
        super(pathToFile);
    }

    @Override
    protected List<Employee> read() {
        return List.of();
    }

    @Override
    public Set<FileFormat> getSupportedFormats() {
        return Set.of(FileFormat.XLS, FileFormat.XLSX);
    }
}
