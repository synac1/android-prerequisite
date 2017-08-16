package com.prereq.yanilda.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditItemActivity extends AppCompatActivity {
    EditText editItemEditText;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String item_value=getIntent().getStringExtra("item_value");
        position =getIntent().getIntExtra("position", 0);
        editItemEditText = (EditText)findViewById(R.id.editItemEditText);
        editItemEditText.setText(item_value);
    }

    public void saveEditedItem(View view) {
        Intent data = new Intent();
        if( !editItemEditText.getText().toString().matches("^\\s*$")) {
            // Pass relevant data back as a result
            data.putExtra("item_value", editItemEditText.getText().toString());
            data.putExtra("position", position); // ints work too
            // Activity finished ok, return the data
            setResult(RESULT_OK, data); // set result code and bundle data for response
        }
        else{
            Toast.makeText(this, "Can't be a whitespace", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
