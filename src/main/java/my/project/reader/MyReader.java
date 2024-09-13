package my.project.reader;

import lombok.Getter;
import my.project.model.Employee;

import java.util.List;
import java.util.Set;

@Getter
abstract class MyReader {
    private final String pathToFile;

    MyReader(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    abstract protected List<Employee> read();

    abstract public Set<FileFormat> getSupportedFormats();

    public enum FileFormat {
        XLSX("xlsx"),
        XLS("xls"),
        CSV("csv");

        private final String format;

        FileFormat(String format) {
            this.format = format;
        }

        public static FileFormat of(String stringFormat) {
            for(FileFormat f : values()){
                if (f.format.equals(stringFormat)){
                    return f;
                }
            }
            return null;
        }
    }
}
