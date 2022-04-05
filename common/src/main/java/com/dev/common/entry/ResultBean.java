package com.dev.common.entry;

import java.io.Serializable;

/**
 * @author long.guo
 * @since 1/31/21
 */
public class ResultBean implements Serializable {
    public String title;
    public String content;
    public String image;

    public ResultBean(String title, String content, String image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }
}
