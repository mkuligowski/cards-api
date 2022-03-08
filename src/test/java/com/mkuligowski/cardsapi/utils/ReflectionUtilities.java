package com.mkuligowski.cardsapi.utils;

import javax.persistence.Id;
import java.lang.reflect.Field;

import static java.util.Optional.ofNullable;

public class ReflectionUtilities {
    public static void setPrivateField(String fieldName, Object obj, Object newValue) {
        final Class c = obj.getClass();
        try {
            Field field = c.getDeclaredField(fieldName);
            ofNullable(field.getDeclaredAnnotation(Id.class))
                    .orElseThrow(() -> new RuntimeException("Only the id field is supported by reflection"));
            field.setAccessible(true);
            field.set(obj, newValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getPrivateFieldValue(String fieldName, Object obj) {
        try{
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}