package com.example.android.bookie;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bookie.models.Book;
import com.example.android.bookie.models.BookAdapter;
import com.example.android.bookie.models.BookLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>{
    private ListView listView;
    private BookAdapter bookAdapter;
    private ProgressBar loadingIndicator;
    private TextView emptyView;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int BOOK_LOADER_ID = 1;
    private static final String QUERY_STRING =
            "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=6";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queue_list);

        List<Book> bookList = new ArrayList<>();
        loadingIndicator = findViewById(R.id.loading_indicator);


        listView = findViewById(R.id.list);

        bookAdapter = new BookAdapter(this, bookList);
        listView.setAdapter(bookAdapter);

        emptyView = findViewById(R.id.empty_text_view);
        listView.setEmptyView(emptyView);
        // Get a reference to the ConnectivityManager, to check network state
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check network state
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        // If device is connected, initiate loaderManger.
        // Else, display informatory message.
        if (networkInfo != null && networkInfo.isConnected()){

            // Get a reference to the LoaderManager, in order to interact with the loader
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).


            loaderManager.initLoader(BOOK_LOADER_ID, null,this);


        }else {
            // Hide the progress bar
            loadingIndicator.setVisibility(View.GONE);
            // Display informatory message
            emptyView.setText(R.string.no_internet_connection);
        }






    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        Log.e(LOG_TAG, "onCreateLoader...");

        return new BookLoader(this, QUERY_STRING);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        Log.e(LOG_TAG, "onLoadFinished");

        loadingIndicator.setVisibility(View.GONE);
        bookAdapter.clear();

        if (books != null && !books.isEmpty()){
            Log.e(LOG_TAG, "\nData is not empty\n");

            bookAdapter.addAll(books);
        }else{
            emptyView.setText(R.string.no_data_found);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        Log.e(LOG_TAG, "onLoaderReset");
        bookAdapter.clear();

    }
}
