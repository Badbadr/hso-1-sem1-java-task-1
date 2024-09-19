package my.project.writer;

import lombok.NoArgsConstructor;
import my.project.util.TargetCalculator;

@NoArgsConstructor
public class ConsoleWriter implements Writer {

    @Override
    public void write(TargetCalculator targetCalculator) {
        System.out.printf("Count of individual employees: %d\n", targetCalculator.getIndividualCount());
        System.out.printf("Count of company employees: %d\n", targetCalculator.getCompanyCount());
        System.out.printf("Count of under 20 age employees: %d\n", targetCalculator.getUnderTwentyAgeCount());
    }
}
