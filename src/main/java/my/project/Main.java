package my.project;

import my.project.reader.DelegatingReader;
import my.project.util.TargetCalculator;
import my.project.writer.ConsoleWriter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        DelegatingReader reader = new DelegatingReader();
        // TODO: проблема с доступом java к файлам системы на маке, пока только так
        try {
            var entities = reader.read("src/main/resources/java-test.xlsx");
            TargetCalculator calculator = new TargetCalculator(entities);
            ConsoleWriter writer = new ConsoleWriter();
            writer.write(calculator);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}