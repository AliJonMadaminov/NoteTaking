package com.example.design;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    EditText noteTitle;
    EditText noteDescription;
    Button btnAdd;
    RadioGroup importanceRgb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        noteTitle = findViewById(R.id.edt_title);
        noteDescription = findViewById(R.id.edt_description);
        btnAdd = findViewById(R.id.btn_add);
        importanceRgb = findViewById(R.id.rgb_importance);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(getBaseContext());
                if (noteTitle.getText().toString().isEmpty() || noteDescription.getText().toString().isEmpty()) {
                    Toast.makeText(AddNoteActivity.this, "You havent entered a note or description", Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put("title", noteTitle.getText().toString());
                    cv.put("description", noteDescription.getText().toString());
                    cv.put("importance", getCheckedImportanceId());
                    db.insert("notes", null, cv);
                    db.close();
                    dbHelper.close();
                    finish();
                }
            }
        });
    }

    public int getCheckedImportanceId() {
        switch (importanceRgb.getCheckedRadioButtonId()) {
            case R.id.rb_simple:
                return Note.SIMPLE;
            case R.id.rb_important:
                return Note.IMPORTANT;
            case R.id.rb_very_important:
                return Note.VERY_IMPORTANT;
            default:
                return Note.SIMPLE;
        }
    }
}