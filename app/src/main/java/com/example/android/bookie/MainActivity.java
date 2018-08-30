package com.example.android.bookie;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.bookie.models.Book;
import com.example.android.bookie.models.BookLoader;
import com.example.android.bookie.utils.NetworkUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final int BOOK_LOADER_ID = 1;

    private static final String QUERY_STRING =
            "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoaderManager loaderManager = getLoaderManager();


        loaderManager.initLoader(BOOK_LOADER_ID, null,this);



    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new BookLoader(this, QUERY_STRING);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        if (books != null && !books.isEmpty()){
            Log.e(LOG_TAG, "Data is not empty");
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {

    }
}
