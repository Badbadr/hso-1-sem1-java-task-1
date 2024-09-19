package my.project;

import my.project.reader.DelegatingReader;
import my.project.reader.Headers;
import my.project.util.BadDeserializer;
import my.project.util.TargetCalculator;
import my.project.writer.ConsoleWriter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DelegatingReader reader = new DelegatingReader();
        // TODO: проблема с доступом java к файлам системы на маке, пока только так
        try {
            HashMap<Headers, List<LinkedHashMap<String, String>>> entities = reader.read(
                    "src/main/resources/java-test.xlsx");
            BadDeserializer d = new BadDeserializer();
            List<Object> objects = d.deserialize(entities);
            TargetCalculator calculator = new TargetCalculator(objects);
            ConsoleWriter writer = new ConsoleWriter();
            writer.write(calculator);
        } catch (IOException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}