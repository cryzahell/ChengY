package com.ox.mylibrary.util;

import java.util.Collection;

/**
 * Created by admin on 2016/12/20.
 */

public class UtilCollection {

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.size() < 1;
    }

}
