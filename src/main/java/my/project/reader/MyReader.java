package my.project.reader;

import lombok.Getter;
import my.project.model.Employee;

import java.util.List;
import java.util.Set;

@Getter
abstract class MyReader {
    private final String pathToFile;
    private final FileFormat format;

    MyReader(String pathToFile, FileFormat format) {
        this.pathToFile = pathToFile;
        this.format = format;
    }

    abstract protected List<Employee> read();

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
