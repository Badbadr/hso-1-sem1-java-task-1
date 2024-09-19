package my.project.reader;

import lombok.Getter;
import my.project.util.GenericRow;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
class CsvReader extends MyReader {

    CsvReader(String pathToFile, FileFormat format) {
        super(pathToFile, format);
    }

    @Override
    protected HashMap<Headers, List<LinkedHashMap<String, String>>> read() {
        LinkedHashMap<Headers, List<LinkedHashMap<String, String>>> result = new LinkedHashMap<>();
        for (Headers value : Headers.values()) {
            result.put(value, new ArrayList<>());
        }

        try (Reader in = new FileReader(getPathToFile())) {
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(Headers.class)
                    .setSkipHeaderRecord(false)
                    .build();

            Iterable<CSVRecord> records = csvFormat.parse(in);

            Iterator<CSVRecord> iterator = records.iterator();
            var entityGroupNames = new GenericRow(iterator.next());
            var entityFieldNames = new GenericRow(iterator.next());
            var groupColumns = getGroupColumns(entityGroupNames, entityFieldNames);
            defineMappers(groupColumns, entityFieldNames);

            while (iterator.hasNext()) {
                var data = new GenericRow(iterator.next());
                var some = data.getData(generalMapper);
                for (var entry: some.entrySet()) {
                    if (!entry.getValue().isEmpty()) {
                        result.get(entry.getKey()).add(entry.getValue());
                    }
                }
            }

            return result;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
