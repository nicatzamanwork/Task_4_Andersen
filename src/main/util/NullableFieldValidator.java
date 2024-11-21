package main.util;

import java.lang.reflect.Field;

public class NullableFieldValidator {
    public static void checkNullableFields(Object object) {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(NullableWarning.class)) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(object);
                    if (value == null) {
                        throw new RuntimeException("VARIABLE IS NULL: " + field.getName());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
