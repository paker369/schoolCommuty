package com.dev.common.utils.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author long.guo
 * @since 1/26/21
 */
public class Collections {
    public static <T> List<T> mutableListOf(T... data) {
        ArrayList<T> list = new ArrayList<>(data.length);
        java.util.Collections.addAll(list, data);
        return list;
    }

    public static <T> List<T> take(List<T> src, int n) {
        if (n == 0) return new ArrayList<T>();
        if (n >= src.size()) {
            return src;
        } else {
            List<T> des = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                des.add(src.get(i));
            }
            return des;
        }
    }
}
