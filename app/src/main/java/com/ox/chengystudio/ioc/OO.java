package com.ox.chengystudio.ioc;

import android.content.Context;

import com.ox.mylibrary.util.ManageLog;

import java.lang.reflect.Field;

/**
 * Created by ox on 16/12/29.
 */
public class OO {

    public static void inject(Context context, Object object) {
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                injectColor(context, object, field);
            }
        } catch (IllegalAccessException e) {
            ManageLog.e(e);
        }
    }

    private static void injectColor(Context context, Object object, Field field) throws IllegalAccessException {
        if (field.isAnnotationPresent(OO_resColor.class)) {
            int id = field.getAnnotation(OO_resColor.class).value();
            int color = context.getResources().getColor(id);
            field.setAccessible(true);
            field.set(object, color);
        }
    }

}
