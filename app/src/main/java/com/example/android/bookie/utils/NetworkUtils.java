package com.example.android.bookie.utils;

import android.net.Uri;
import android.util.Log;

import com.example.android.bookie.models.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class NetworkUtils {

    public static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    public static List<Book> fetchBookData(String queryString) {
        // Create URL object from queryString
        URL url = createURL(queryString);

        String jsonResult = null;

        try {
            // Make HTTP connection and get JSON response from url
            jsonResult = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing stream");
        }

        // Parse JSON response and store it in List object
        List<Book> listOfBooks = parseJsonResponse(jsonResult);

        return listOfBooks;
    }

    private static URL createURL(String queryString) {

        URL requestUrl = null;
        try {
            requestUrl = new URL(queryString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the url", e);
        }

        return requestUrl;
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If url is null (i.e. there was an error in creating the url),
        // return an empty string
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        // Compose and send the HTTP request
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.connect();

            // If connection to the url is successful,
            // extract the input stream from the url
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                // Log the JSON response
                Log.e(LOG_TAG, "\n The JSON response code is : " + httpURLConnection.getResponseCode());
                // Read JSON response from input stream
                jsonResponse = getJsonFromStream(inputStream);

            } else {
                Log.e(LOG_TAG, "\n The JSON response code is :" + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "\n Problem retrieving the quakes", e);
            Log.e(LOG_TAG, "\n The JSON response code is :" + httpURLConnection.getResponseCode());
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String getJsonFromStream(InputStream inputStream) throws IOException {

        StringBuilder builder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();
            while (line != null) {
                builder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return builder.toString();
    }

    public static List<Book> parseJsonResponse(String jsonResponse) {
        // Log JSON response
        Log.e(LOG_TAG, "\n JSON response in parseJsonResponse method : " + jsonResponse + "\n");

        List<Book> bookList = new ArrayList<>();

        // Extract required details from JSON response
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    String title = volumeInfo.getString("title");

                    String authors = volumeInfo.getString("authors");

                    String publisher = volumeInfo.getString("publisher");

                    String publishedDate = volumeInfo.getString("publishedDate");

                    JSONObject photoUrls = volumeInfo.getJSONObject("imageLinks");

                    String photoUrl = photoUrls.getString("smallThumbnail");

                    Book books = new Book(title, authors, publisher, publishedDate, photoUrl);
                    // Log the contents of the Book object
                    Log.e(LOG_TAG, "\nBook Details" + books.toString());
                    // Add Book object to array list
                    bookList.add(books);

                } catch (JSONException e) {
                    Log.e(LOG_TAG, "Could not get the contents of JSONObject volumeInfo", e);
                }

            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Could not get the contents of JSONObject jsonResponse", e);
        }

        return bookList;
    }

}
