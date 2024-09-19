package my.project.util;

import lombok.Getter;
import my.project.reader.Headers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
public class TargetCalculator {
    private final int individualCount;
    private final int companyCount;
    private final int underTwentyAgeCount;

    public TargetCalculator(HashMap<Headers, List<LinkedHashMap<String, String>>> entities) {
        individualCount = entities.get(Headers.INDIVIDUAL).size();
        companyCount = entities.get(Headers.COMPANY).size();
        underTwentyAgeCount = (int) entities.get(Headers.INDIVIDUAL).stream()
                .filter(entity -> Integer.parseInt(entity.get("age")) < 20).count();
    }
}
