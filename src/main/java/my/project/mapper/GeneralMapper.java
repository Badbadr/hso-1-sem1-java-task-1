package my.project.mapper;

import my.project.reader.Headers;

import java.util.HashMap;
import java.util.Map;

public class GeneralMapper {

    private final Map<Headers, Map<Integer, String>> mappers = new HashMap<>();
    private final Map<Integer, Headers> columnIndexToEntityMap = new HashMap<>();

    public GeneralMapper() {
        for (Headers entityType : Headers.values()) {
            mappers.put(entityType, new HashMap<>());
        }
    }

    public void addMapping(Headers entityType, Map<Integer, String> mapping) {
        this.mappers.put(entityType, mapping);
        mapping.forEach((key, value) -> columnIndexToEntityMap.put(key, entityType));
    }

    public Map<Integer, String> getEntityColumnMapping(Headers entityType) {
        return mappers.get(entityType);
    }

    public Headers getEntityForColumnIndex(int index) {
        return columnIndexToEntityMap.get(index);
    }
}
