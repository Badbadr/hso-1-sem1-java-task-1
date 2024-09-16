package my.project.reader;

import lombok.Getter;
import my.project.mapper.GeneralMapper;
import my.project.util.GenericRow;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
abstract class MyReader {

    protected GeneralMapper generalMapper;

    private final String pathToFile;
    private final FileFormat format;

    MyReader(String pathToFile, FileFormat format) {
        this.pathToFile = pathToFile;
        this.format = format;
        generalMapper = new GeneralMapper();
    }

    abstract protected HashMap<Headers, List<HashMap<String, String>>> read() throws IOException;

    /**
     * Определяет связь свойства объекта с номером столбца в таблице, путем инициализации {@link GeneralMapper}
     * @param groupColumns - словарь содержаший связь названий сущостей с соответсвующими ей номерами столбцов
     * @param entityFieldNames - строка с названиями свойств сущностей
     */
    protected void defineMappers(Map<String, List<Integer>> groupColumns, GenericRow entityFieldNames) {
        for (String key: groupColumns.keySet()) {
            var entityType = Headers.of(key);
            var fieldToColumnMap = groupColumns.get(key).stream().collect(Collectors.toMap(
                    Function.identity(), index -> entityFieldNames.get(index).toLowerCase().strip()));
            generalMapper.addMapping(entityType, fieldToColumnMap);
        }
    }

    /**
     * Определяет набор столбцов отвечающих сущности (заголовку)
     * @param entityGroupNames - строка с названиями сущностей
     * @param entityFieldNames - строка с названиями свойств сущностей
     * @return  Словарь в котором ключем является название сущности, а значеннием - список номеров столбцов содержащих
     *          свойства соответствующей сущности
     */
    protected Map<String, List<Integer>> getGroupColumns(GenericRow entityGroupNames, GenericRow entityFieldNames) {
        String currentGroup = entityGroupNames.get(0);
        Map<String, List<Integer>> groupToColumnsMap = new HashMap<>();

        for (int i = 0; i < entityFieldNames.size(); i++) {
            var possibleFieldName = entityFieldNames.get(i);
            if (StringUtils.isBlank(possibleFieldName)) {
                continue;
            }

            if (!StringUtils.isBlank(entityGroupNames.get(i))) {
                currentGroup = entityGroupNames.get(i);
            }
            List<Integer> integers = groupToColumnsMap.putIfAbsent(currentGroup, new ArrayList<>(Arrays.asList(i)));
            if (integers != null) {
                integers.add(i);
            }
        }

        return groupToColumnsMap;
    }

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
