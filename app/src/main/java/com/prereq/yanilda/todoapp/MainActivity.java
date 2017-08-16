package com.prereq.yanilda.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> toDoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;
    private final int REQUEST_CODE = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems= (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText =(EditText) findViewById(R.id.etEditText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                toDoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i= new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("item_value",((TextView) view).getText());
                i.putExtra("position", position);
                startActivityForResult(i,REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract edit value from result extras
            String item_value = data.getExtras().getString("item_value");
            int position = data.getExtras().getInt("position", 0);
            toDoItems.set(position, item_value);
            aToDoAdapter.notifyDataSetChanged();
            writeItems();
        }
    }
    public void populateArrayItems() {
        readItems();
        aToDoAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toDoItems);
    }


    public void onAddItem(View view) {
        if( !etEditText.getText().toString().matches("^\\s*$")){
            aToDoAdapter.add(etEditText.getText().toString());
            etEditText.setText("");
            writeItems();
        }else{
            Toast.makeText(this, "Can't be a whitespace", Toast.LENGTH_SHORT).show();
        }
    }

    public  void readItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todoApp3.txt");
        try{
            toDoItems= new ArrayList<String>(FileUtils.readLines(file));
        }
        catch (FileNotFoundException e) {
            toDoItems = new ArrayList<String>();
        }
        catch (IOException e){
        }
    }


    public  void writeItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todoApp3.txt");
        try{
            FileUtils.writeLines(file, toDoItems);
        }catch (IOException e){
        }
    }
}
