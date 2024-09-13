package my.project;

import my.project.model.Employee;
import my.project.reader.DelegatingReader;
import my.project.writer.ConsoleWriter;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DelegatingReader reader = new DelegatingReader();
        // TODO: проблема с доступом java к файлам системы на маке, пока только так
        List<Employee> employeeList = reader.read("src/main/resources/java-test.csv");
        TargetCalculator calculator = new TargetCalculator(employeeList);
        ConsoleWriter writer = new ConsoleWriter();
        writer.write(calculator);
    }
}