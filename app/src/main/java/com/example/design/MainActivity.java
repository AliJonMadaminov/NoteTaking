package com.example.design;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addFab;
    NoteAdapter adapter;
    DBHelper dbHelper;
    List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();
        notes = new ArrayList<>();
        addFab = findViewById(R.id.add_fab);
        recyclerView = findViewById(R.id.recycler_main);

        dbHelper = new DBHelper(getBaseContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        
        Cursor cursor = db.query("notes", null, "isDeleted =" + 0, null,null, null, "importance desc, title");
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int titleIndex = cursor.getColumnIndex("title");
            int descriptionIndex = cursor.getColumnIndex("description");
            do {
                notes.add(new Note(cursor.getInt(idIndex), cursor.getString(titleIndex), cursor.getString(descriptionIndex)));
            } while (cursor.moveToNext());
        }
        cursor.close();
//        db.close();
//        dbHelper.close();


        NoteOnClickListener listener = new NoteOnClickListener() {
            @Override
            public void onClick(Note note) {
                openOrCreateDatabase("MyDb", Context.MODE_PRIVATE, null);
                db.execSQL("update notes "
                + "set isDeleted = 1"
                + " where id = " + note.id);
            }

            @Override
            public void onRootCreate(Note note) {
                Intent intent = new Intent(getBaseContext(), ShowDescriptionActivity.class);
                intent.putExtra("id", note.id);
                startActivity(intent);
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NoteAdapter(notes, listener);
        recyclerView.setAdapter(adapter);

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }
}