package my.project.reader;

import my.project.model.Employee;

import java.util.List;
import java.util.Objects;

public class DelegatingReader {

    private MyReader getReaderForFile(String path) {
        var paths = path.split("/");
        var lastPath = paths[paths.length - 1].split("\\.");
        var format = lastPath[lastPath.length - 1];

        return switch (Objects.requireNonNull(MyReader.FileFormat.of(format))) {
            case CSV -> new CsvReader(path);
            case XLSX, XLS -> new ExcelReader(path);
        };
    }

    public List<Employee> read(String path) {
        return getReaderForFile(path).read();
    }
}
