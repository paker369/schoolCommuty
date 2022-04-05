package com.dev.main.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dev.book.viewmodel.BookManager;
import com.dev.common.database.user.User;
import com.dev.common.entry.BookBean;
import com.dev.common.utils.data.ArrayList;

import java.util.List;

import static com.dev.common.database.DaoProvider.userDao;

/**
 * @author long.guo
 * @since 1/27/21
 */
public class SearchViewModel extends ViewModel {

    public MutableLiveData<List<User>> users = new MutableLiveData<>();
    public MutableLiveData<List<BookBean>> books = new MutableLiveData<>();

    public void searchUsers(String keyword) {
        List<User> userBeans = userDao().search("%" + keyword + "%");
        users.setValue(userBeans);
    }

    public void searchBooks(String keyword) {
        ArrayList<BookBean> books = BookManager.allBooks().filter(bookBean -> {
            if (bookBean.getBookName() == null) return true;
            if (bookBean.getBookDesc() == null) return true;
            if (keyword == null) return false;
            return bookBean.getBookName().contains(keyword) || bookBean.getBookDesc().contains(keyword);
        });
        this.books.setValue(books);
    }
}
