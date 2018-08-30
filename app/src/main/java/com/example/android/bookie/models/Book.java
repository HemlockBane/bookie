package com.example.android.bookie.models;

public class Book {

    private String title;
    private String description;
    private String publisher;
    private String publishedDate;

    public Book(String title, String description, String publisher, String publishedDate){
        this.title = title;
        this.description = description;
        this.publisher = publisher;
        this.publishedDate = publishedDate;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        builder.append("title: " + getTitle())
                .append("\n description: " + getDescription())
                .append("\n publisher: " + getPublishedDate())
                .append("\n date of publication: " + getPublishedDate());

        return builder.toString();
    }
}
