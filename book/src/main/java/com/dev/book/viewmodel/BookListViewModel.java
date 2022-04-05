package com.dev.book.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dev.common.entry.BookBean;

import java.util.List;

/**
 * @author long.guo
 * @since 1/24/21
 */
public class BookListViewModel extends ViewModel {
    public MutableLiveData<List<BookBean>> books = new MutableLiveData<>();

    public void loadBooks(int position) {
        books.setValue(BookManager.getBooks(position));
    }
}
