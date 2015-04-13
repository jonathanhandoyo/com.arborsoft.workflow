package com.arborsoft.workflow.util;

import com.arborsoft.workflow.annotation.RelatedTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ReflectionUtils.class);

    public static <A extends Annotation> A getTypeAnnotation(Class<?> type, Class<A> annotationType) {
        if (type != null) {
            while (type != null) {
                if (type.getAnnotation(annotationType) != null) {
                    return type.getAnnotation(annotationType);
                }
                type = type.getSuperclass();
            }
        }
        return null;
    }

    public static List<Field> getFields(Class<?> _class, boolean editable) {
        Class<?> current = _class;
        List<Field> all = new ArrayList<>();
        while (current != null) {
            for (Field field: current.getDeclaredFields()) {
                if (!field.isAnnotationPresent(RelatedTo.class) && !Modifier.isStatic(field.getModifiers()) && !Modifier.isTransient(field.getModifiers())) {
                    if (editable) {
                        field.setAccessible(true);
                    }
                    all.add(field);
                }
            }
            current = current.getSuperclass();
        }
        return all;
    }

    public static Field getField(Class<?> type, String fieldName, boolean editable) {
        while (type != null) {
            try {
                Field field = type.getDeclaredField(fieldName);
                if (editable) {
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {
                //do nothing
            } catch (IllegalArgumentException e) {
                LOG.error(e.getMessage(), e);
                throw e;
            }
            type = type.getSuperclass();
        }
        return null;
    }
}
