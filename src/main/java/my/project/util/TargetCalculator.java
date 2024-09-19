package my.project.util;

import lombok.Getter;
import my.project.model.Company;
import my.project.model.Individual;

import java.util.List;

@Getter
public class TargetCalculator {
    private int individualCount = 0;
    private int companyCount = 0;
    private int underTwentyAgeCount = 0;

    public TargetCalculator(List<Object> entities) {
        for (Object object: entities) {
            if (object instanceof Individual ind) {
                individualCount++;
                if (ind.getAge() < 20) {
                    underTwentyAgeCount++;
                }
            }
            if (object instanceof Company comp) {
                companyCount++;
            }
        }
    }
}
