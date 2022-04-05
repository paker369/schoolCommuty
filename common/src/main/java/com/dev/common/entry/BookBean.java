package com.dev.common.entry;

import java.io.Serializable;
import java.util.List;

/**
 * @author long.guo
 * @since 1/24/21
 */
public class BookBean implements Serializable {

    // 根目录：如data/data/com..../cache/books/book1
    private String rootPath;

    private String bookName;
    private String poster;
    private String bookDesc;
    private List<PageBean> pages;

    /**
     * 1代表在第二个item显示，即启蒙
     * 2代表在第三个item显示，即童话
     * 3代表在第四个item显示，即寓言
     * 4代表在第五个item...以此类推
     */
    private int category;

    public List<PageBean> getPages() {
        return pages;
    }

    public void setPages(List<PageBean> pages) {
        this.pages = pages;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBookDesc() {
        return bookDesc;
    }

    public void setBookDesc(String bookDesc) {
        this.bookDesc = bookDesc;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String toString() {
        return "BookBean{" +
                "rootPath='" + rootPath + '\'' +
                ", bookName='" + bookName + '\'' +
                ", poster='" + poster + '\'' +
                ", bookDesc='" + bookDesc + '\'' +
                ", pages='" + pages + '\'' +
                ", category=" + category +
                '}';
    }

    public static class PageBean implements Serializable {
        private String mp3;
        private String text;
        private String image;

        public String getMp3() {
            return mp3;
        }

        public void setMp3(String mp3) {
            this.mp3 = mp3;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @Override
        public String toString() {
            return "PageBean{" +
                    "mp3='" + mp3 + '\'' +
                    ", text='" + text + '\'' +
                    ", image='" + image + '\'' +
                    '}';
        }
    }
}
