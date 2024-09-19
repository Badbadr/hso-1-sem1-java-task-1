package my.project.util;

import my.project.reader.Headers;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class BadDeserializer {

    public void deserialize(HashMap<Headers, List<LinkedHashMap<String, String>>> entities) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var h = Arrays.stream(entities.get(Headers.INDIVIDUAL).get(0).values().toArray()).toList();
        var x = Arrays.stream(entities.get(Headers.EMPLOYEE).get(0).values().toArray()).toList();
        var y = Arrays.stream(entities.get(Headers.BANK_ACCOUNT).get(0).values().toArray()).toList();
        var res = new ArrayList<>();
        res.addAll(x);
        res.addAll(y);
        res.addAll(h);
        Class<?> clazz = Class.forName(Headers.INDIVIDUAL.getQualifiedClassName());
        Constructor<?> cons = clazz.getConstructor(List.class);
        Object obj = cons.newInstance(res);
        System.out.println(obj);
    }
}
