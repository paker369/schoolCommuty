package com.dev.book.viewmodel;

import android.util.Log;

import com.dev.common.BaseApplication;
import com.dev.common.database.AppDatabase;
import com.dev.common.database.recommend.BookRecommend;
import com.dev.common.database.recommend.PublishBook;
import com.dev.common.entry.BookBean;
import com.dev.common.utils.data.ArrayList;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Objects;

import static com.dev.common.database.DaoProvider.publishBookDao;

/**
 * @author long.guo
 * @since 1/24/21
 */
public class BookManager {
    public static List<BookBean> getBooks(int position) {
        List<BookBean> beans = allBooks();
        switch (position) {
            case 0:
                return recommend(beans);
            case 1:
                return filterByCategory(beans, 1);
            case 2:
                return filterByCategory(beans, 2);
            case 3:
                return filterByCategory(beans, 3);
        }
        return null;
    }

    public static ArrayList<BookBean> allBooks() {
        Gson gson = new Gson();
        ArrayList<BookBean> result = new ArrayList<>();
        try {
            String path = BaseApplication.app.getExternalCacheDir() + File.separator + "books";
            for (String s : Objects.requireNonNull(new File(path).list())) {
                Log.i("guolong", "path:" + s);
                String itemRootPath = path + File.separator + s;
                try {
                    BookBean bean = gson.fromJson(new FileReader(new File(itemRootPath + File.separator + "book.json")), BookBean.class);
                    bean.setRootPath(itemRootPath);
                    bean.setPoster(path + bean.getPoster());
                    for (BookBean.PageBean page : bean.getPages()) {
                        page.setMp3(path + page.getMp3());
                        page.setImage(path + page.getImage());
                    }

                    result.add(bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (PublishBook publishBook : publishBookDao().findAll()) {
            BookBean bean = gson.fromJson(publishBook.json, BookBean.class);
            result.add(bean);
        }
        return result;
    }

    private static List<BookBean> filterByCategory(List<BookBean> bookBeans, int category) {
        List<BookBean> result = new ArrayList<>();
        for (BookBean bookBean : bookBeans) {
            if (bookBean.getCategory() == category) {
                result.add(bookBean);
            }
        }
        return result;
    }

    private static List<BookBean> recommend(List<BookBean> beans) {
        List<BookBean> result = new ArrayList<>();
        List<BookRecommend> recommends = AppDatabase.getInstance().bookRecommendDao().findAll();
        for (BookBean bean : beans) {
            if (pathInRecommend(bean.getRootPath(), recommends)) {
                result.add(bean);
            }
        }
        return result;
    }

    private static boolean pathInRecommend(String path, List<BookRecommend> recommends) {
        for (BookRecommend recommend : recommends) {
            if (recommend.bookRootPath.equals(path)) {
                return true;
            }
        }
        return false;
    }

    public static void saveBook(BookBean bookBean) {
        String json = new Gson().toJson(bookBean);
        publishBookDao().insert(new PublishBook(json));
    }
}
