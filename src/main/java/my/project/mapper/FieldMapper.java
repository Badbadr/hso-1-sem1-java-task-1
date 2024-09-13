package my.project.mapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public abstract class FieldMapper {

    protected final Map<String, Integer> fieldToColumnMap;
}
