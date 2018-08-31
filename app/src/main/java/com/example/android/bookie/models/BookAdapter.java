package com.example.android.bookie.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.bookie.R;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context, List<Book> bookList) {
        super(context, 0, bookList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listViewItem = convertView;
        if (listViewItem == null){
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.queue_view, parent, false);
        }

        ImageView bookCover = listViewItem.findViewById(R.id.book_cover_view);

        //Find the title textView
        TextView title = listViewItem.findViewById(R.id.title_text_view);

        //Find the author textView
        TextView author = listViewItem.findViewById(R.id.author_text_view);

        // Find the book at the given position in the list of books
        Book currentBooks = getItem(position);

        if (currentBooks.getPhotoUrl()!= null) {
            Glide
                    .with(bookCover.getContext())
                    .load(currentBooks.getPhotoUrl())
                    .into(bookCover);
        }else {
            bookCover.setVisibility(View.INVISIBLE);
        }

        // Set the title
        title.setText(currentBooks.getTitle());

        // Set the author
        author.setText(currentBooks.getAuthors());

//        //Find the publisher textView
//        TextView publisher = listViewItem.findViewById(R.id.publisher_text_view);
//        // Set the publisher
//        publisher.setText(currentBooks.getPublisher());
//
//        //Find the date of publication textView
//        TextView dateOfPublication = listViewItem.findViewById(R.id.publication_date_text_view);
//        // Set the date of publication
//        dateOfPublication.setText(currentBooks.getPublishedDate());

        return listViewItem;
    }
}
