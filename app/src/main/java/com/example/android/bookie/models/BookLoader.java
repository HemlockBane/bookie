package com.example.android.bookie.models;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.bookie.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Book>>{

    private static final String LOG_TAG = BookLoader.class.getSimpleName();

    private String url;

    public BookLoader(Context context, String url) {
        super(context);

        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        List<Book> bookList =  NetworkUtils.fetchBookData(url);
        return bookList;
    }
}
