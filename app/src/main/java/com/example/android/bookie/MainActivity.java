package com.example.android.bookie;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.android.bookie.models.Book;
import com.example.android.bookie.models.BookAdapter;
import com.example.android.bookie.models.BookLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>{
    private ListView listView;
    private BookAdapter bookAdapter;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final int BOOK_LOADER_ID = 1;

    private static final String QUERY_STRING =
            "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=6";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queue_list);

        List<Book> bookList = new ArrayList<>();

        listView = findViewById(R.id.list);

        bookAdapter = new BookAdapter(this, bookList);

        listView.setAdapter(bookAdapter);


        LoaderManager loaderManager = getLoaderManager();


        loaderManager.initLoader(BOOK_LOADER_ID, null,this);



    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        Log.e(LOG_TAG, "onCreateLoader...");

        return new BookLoader(this, QUERY_STRING);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        Log.e(LOG_TAG, "onLoadFinished");

        bookAdapter.clear();

        if (books != null && !books.isEmpty()){
            Log.e(LOG_TAG, "\nData is not empty\n");
            bookAdapter.addAll(books);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        Log.e(LOG_TAG, "onLoaderReset");
        bookAdapter.clear();

    }
}
