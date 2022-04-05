package com.dev.common.utils.data;


/**
 * @author long.guo
 * @since 2/6/21
 */
public class ArrayList<T> extends java.util.ArrayList<T> {

    public ArrayList<T> filter(Predicate<T> predicate) {
        ArrayList<T> result = new ArrayList<>();
        if (size() == 0) return result;
        for (int i = 0; i < size(); i++) {
            T t = get(i);
            if (predicate.predicate(t)) {
                result.add(t);
            }
        }
        return result;
    }

    public interface Predicate<T> {
        boolean predicate(T t);
    }
}
