package com.dev.common.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author long.guo
 * @since 1/24/21
 */
public class LayoutInflaterUtils {

    public static View inflate(ViewGroup parent, int layout) {
        return LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
    }
}
