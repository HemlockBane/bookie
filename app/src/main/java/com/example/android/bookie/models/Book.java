package com.example.android.bookie.models;

public class Book {

    private String title;
    private String authors;
    private String photoUrl;
    private String publisher;
    private String publishedDate;



    public Book(String title, String authors, String publisher, String publishedDate){
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;

    }
    public Book(String title, String authors, String publisher, String publishedDate, String photoUrl){
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.photoUrl = photoUrl;

    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("\ntitle: " + getTitle())
                .append("\n authors: " + getAuthors())
                .append("\n publisher: " + getPublisher())
                .append("\n date of publication: " + getPublishedDate() + "\n");

        return builder.toString();
    }
}
