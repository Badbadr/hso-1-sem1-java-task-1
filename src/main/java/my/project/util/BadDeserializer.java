package my.project.util;

import my.project.reader.Headers;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BadDeserializer {

    /**
     * Делаем допущение, что все поля лежат в таком же порядке, что и аргумента конструкторов
     * @param entities
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public List<Object> deserialize(HashMap<Headers, List<LinkedHashMap<String, String>>> entities) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Object> result = new ArrayList<>();

        Map<Headers, Class<?>> headerToRootClassMap = new HashMap<>();
        Map<Headers, Class<?>> headerToParentClassMap = new HashMap<>();
        Map<Headers, Class<?>> propClassHeaderToContainingClassMap = new HashMap<>();

        for (Headers header: entities.keySet()) {
            if (header.getFiledOf() != null) {
                propClassHeaderToContainingClassMap.put(header, Class.forName(header.getFiledOf().getQualifiedClassName()));
                continue;
            }

            if (header.getParentClass() != null) {
                headerToParentClassMap.put(header, Class.forName(header.getParentClass().getQualifiedClassName()));
                headerToRootClassMap.put(header.getParentClass(), Class.forName(header.getParentClass()
                        .getQualifiedClassName()));
                continue;
            }
            if (!isAbstract(Class.forName(header.getQualifiedClassName()))) {
                headerToParentClassMap.put(header, null);
            }
        }

        Map<Headers, List<List<Object>>> propClassHeaderToArgs = new HashMap<>();

        for (var entry: propClassHeaderToContainingClassMap.entrySet()) {
            Headers header = entry.getKey();
            var fieldClassInstances = entities.get(header);
            for (var instanceSource: fieldClassInstances) {
                var arr = propClassHeaderToArgs.putIfAbsent(header, new ArrayList<>());
                if (arr == null) {
                    propClassHeaderToArgs.get(header).add(Arrays.stream(instanceSource.values().toArray()).toList());
                } else {
                    arr.add(Arrays.stream(instanceSource.values().toArray()).toList());
                }
            }
        }

        Map<Headers, List<List<Object>>> rootClassHeaderToArgs = new HashMap<>();

        for (var entry: headerToRootClassMap.entrySet()) {
            Headers header = entry.getKey();
            var fieldClassInstances = entities.get(header);
            for (var instanceSource: fieldClassInstances) {
                var arr = rootClassHeaderToArgs.putIfAbsent(header, new ArrayList<>());
                if (arr == null) {
                    rootClassHeaderToArgs.get(header).add(Arrays.stream(instanceSource.values().toArray()).toList());
                } else {
                    arr.add(Arrays.stream(instanceSource.values().toArray()).toList());
                }
            }
        }

        Map<Headers, List<List<Object>>> endClassHeaderToArgs = new HashMap<>();

        List<Headers> headersToRemove = new ArrayList<>();
        for (var entry: headerToParentClassMap.entrySet()) {
            Headers header = entry.getKey();
            if (entry.getValue() != null) {
                var fieldClassInstances = entities.get(header);
                for (var instanceSource: fieldClassInstances) {
                    var countOfBlank = instanceSource.values().stream().filter(StringUtils::isBlank).count();
                    if (countOfBlank != 0) {
                        continue;
                    }
                    var arr = endClassHeaderToArgs.putIfAbsent(header, new ArrayList<>());
                    if (arr == null) {
                        endClassHeaderToArgs.get(header).add(Arrays.stream(instanceSource.values().toArray()).toList());
                    } else {
                        arr.add(Arrays.stream(instanceSource.values().toArray()).toList());
                    }
                }
            } else {
                Class<?> clazz = Class.forName(header.getQualifiedClassName());
                Constructor<?> cons = clazz.getConstructor(List.class);
                var listOfEntities = entities.get(header);
                for (var instanceSource: listOfEntities) {
                    Object obj = cons.newInstance(Arrays.stream(instanceSource.values().toArray()).toList());
                    result.add(obj);
                }
                headersToRemove.add(header);
            }
        }

        for (Headers header: headersToRemove) {
            headerToParentClassMap.remove(header);
        }

        for (var entry: headerToParentClassMap.entrySet()) {
            var parentArgs = rootClassHeaderToArgs.get(entry.getKey().getParentClass());
            var propOfRootClass = propClassHeaderToContainingClassMap.entrySet().stream().filter(entry1 -> {
                try {
                    return entry1.getValue().equals(Class.forName(entry.getKey().getParentClass()
                            .getQualifiedClassName()));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }).findFirst();
            var propArgs = propClassHeaderToArgs.get(propOfRootClass.get().getKey());
            Class<?> clazz = Class.forName(entry.getKey().getQualifiedClassName());
            Constructor<?> cons = clazz.getConstructor(List.class);

            var size = endClassHeaderToArgs.get(entry.getKey()).size();
            for (int i = 0; i < size; i++) {
                List<Object> resultArgs = new ArrayList<>();
                resultArgs.addAll(parentArgs.get(i));
                resultArgs.addAll(propArgs.get(i));
                resultArgs.addAll(endClassHeaderToArgs.get(entry.getKey()).get(i));
                result.add(cons.newInstance(resultArgs));
            }
        }

        return result;
    }

    private boolean isAbstract(Class<?> clazz) {
        return Modifier.isAbstract(clazz.getModifiers());
    }
}
