package main.util;

import java.lang.reflect.Field;

public class NullableFieldValidator {

    public static void validate(Object obj) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(NullableWarning.class)) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(obj);
                    if (value == null) {
                        throw new RuntimeException("Field " + field.getName() + " is null in " + obj.getClass().getSimpleName());
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Unable to access field " + field.getName(), e);
                }
            }
        }
    }
}
