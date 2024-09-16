package my.project.reader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DelegatingReader {

    private MyReader getReaderForFile(String path) {
        var paths = path.split("/");
        var lastPath = paths[paths.length - 1].split("\\.");
        var format = lastPath[lastPath.length - 1];

        return switch (Objects.requireNonNull(MyReader.FileFormat.of(format))) {
            case CSV -> new CsvReader(path, MyReader.FileFormat.CSV);
            case XLSX -> new ExcelReader(path, MyReader.FileFormat.XLSX);
            case XLS -> new ExcelReader(path, MyReader.FileFormat.XLS);
        };
    }

    public HashMap<Headers, List<HashMap<String, String>>> read(String path) throws IOException {
        return getReaderForFile(path).read();
    }
}
