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

    public static final String LOG_TAG = NetworkUtils.class.getSimpleName() ;

    public static List<Book> fetchBookData(String queryString ){
        URL url = createURL(queryString);

        String jsonResult = null;

        try {
            jsonResult = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Error closing stream");
        }

        List<Book> listOfBooks = parseJsonResponse(jsonResult);

        return listOfBooks;
    }

    private static URL createURL(String queryString){

//        Uri builtUri = Uri.parse(BOOK_BASE_URL).buildUpon()
//                .appendQueryParameter(QUERY_PARAM, queryString)
//                .appendQueryParameter(MAX_RESULTS, "10")
//                .appendQueryParameter(PRINT_TYPE, "books")
//                .build();

        URL requestUrl = null;
        try {
            requestUrl = new URL(queryString);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problem building the url", e);
        }

        return requestUrl;
    }

    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();

                Log.e(LOG_TAG, "The JSON response code is : " + httpURLConnection.getResponseCode());
                jsonResponse = getJsonFromStream(inputStream);


            } else {
                Log.e(LOG_TAG, "The JSON response code is :" + httpURLConnection.getResponseCode() );
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the quakes", e);
            Log.e(LOG_TAG, "The JSON response code is :" + httpURLConnection.getResponseCode() );
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

    private static String getJsonFromStream(InputStream inputStream) throws IOException{

        StringBuilder builder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();
            while (line != null){
                builder.append(line);
                line = bufferedReader.readLine();
            }
        }

        Log.e(LOG_TAG, "JSON response : " + builder.toString());
        return builder.toString();
    }

    public static List<Book> parseJsonResponse(String jsonResponse){

        List<Book> bookList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < itemsArray.length() ; i++) {
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    String title = volumeInfo.getString("title");
                    Log.e(LOG_TAG, "title :" + title);

                    String authors = volumeInfo.getString("authors");
                    Log.e(LOG_TAG, "author :" + authors);


                    String publishedDate = volumeInfo.getString("publishedDate");
                    Log.e(LOG_TAG, "Published date: " + publishedDate);

                    String publisher = volumeInfo.getString("publisher");
                    Log.e(LOG_TAG, "publisher :" + publisher );


                    Book books = new Book(title, authors, publisher, publishedDate);
                    bookList.add(books);



                }catch (JSONException e){
                    Log.e(LOG_TAG, "Could not get the contents of JSONObject volumeInfo", e);
                }

            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Could not get the contents of JSONObject jsonResponse", e);
        }

        Log.e(LOG_TAG, jsonResponse);


        return bookList;
    }

}
