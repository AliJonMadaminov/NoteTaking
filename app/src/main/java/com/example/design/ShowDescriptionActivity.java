package com.example.design;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class ShowDescriptionActivity extends AppCompatActivity {

    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_description);
        description = findViewById(R.id.txt_description_body);

        int id = getIntent().getExtras().getInt("id");
        DBHelper dbHelper = new DBHelper(getBaseContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("notes", new String[]{"description"}, "id =" + id, null, null,null,null);

        if (cursor.moveToFirst()) {
            description.setText(cursor.getString(cursor.getColumnIndex("description")));
        }
        cursor.close();
        dbHelper.close();
        db.close();
    }
}