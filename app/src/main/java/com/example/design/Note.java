package com.example.design;

public class Note {
    String title;
    String description;
    int importance;
    int id;

    public final static int SIMPLE = 1;
    public final static int IMPORTANT = 2;
    public final static int VERY_IMPORTANT = 3;

    public Note(int id, String title, String description) {
        this.title = title;
        this.description = description;
        this.id = id;
    }
}
