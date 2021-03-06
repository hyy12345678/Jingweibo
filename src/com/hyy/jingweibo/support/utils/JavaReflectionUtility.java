package com.hyy.jingweibo.support.utils;

import java.lang.reflect.Field;

import android.widget.AbsListView;
import android.widget.AdapterView;

public class JavaReflectionUtility {

    public static <T> T getValue(AbsListView view, String name) {
        final Field field;
        try {
            field = AbsListView.class.getDeclaredField(name);
            field.setAccessible(true);
            return (T) field.get(view);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void setValue(AdapterView view, String name, int value) {
        final Field field;
        try {
            field = AdapterView.class.getDeclaredField(name);
            field.setAccessible(true);
            field.setInt(view, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
