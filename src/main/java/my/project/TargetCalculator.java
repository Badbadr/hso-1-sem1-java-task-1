package my.project;

import lombok.Getter;
import my.project.model.Employee;
import my.project.model.Individual;

import java.util.List;

@Getter
public class TargetCalculator {
    private int individualCount = 0;
    private int companyCount = 0;
    private int underTwentyAgeCount = 0;

    public TargetCalculator(List<Employee> employeeList) {
        for (Employee employee: employeeList) {
            if (employee instanceof Individual individual) {
                individualCount++;
                if (individual.getAge() < 20) {
                    underTwentyAgeCount++;
                }
            } else {
                companyCount++;
            }
        }
    }
}
