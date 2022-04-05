package com.dev.admin.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dev.book.viewmodel.BookManager;
import com.dev.common.entry.BookBean;

import java.util.List;

/**
 * @author long.guo
 * @since 1/24/21
 */
public class BookManageViewModel extends ViewModel {
    public MutableLiveData<List<BookBean>> allBooks = new MutableLiveData<>();

    {
        loadAllBooks();
    }

    public void loadAllBooks() {
        allBooks.setValue(BookManager.allBooks());
    }
}
