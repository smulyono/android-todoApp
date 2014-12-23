package me.smulyono.todo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TodoActivity extends ActionBarActivity {
    private final String TAG = TodoActivity.class.getSimpleName();
    // Internal representations of the todo item list
    private List<String> todoItems;
    // every view component will use Adapter as their models
    // Type accepted by ArrayAdapter can be any Object
    private ArrayAdapter<String> atodoItems;
    // List view UI reference
    private ListView lvItems;
    private EditText etNewItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        //
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        // create adapter with the data (e.g list<String>)
        atodoItems = new ArrayAdapter<String>(this,
                                android.R.layout.simple_list_item_1,
                                todoItems);
        // attach adapter
        lvItems.setAdapter(atodoItems);
        // attach listener
        setupListViewListener();
    }

    // Programatically attach listener
    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View item, int position, long id) {
                Log.i(TAG, "Removing item in position " + position);
                todoItems.remove(position);
                atodoItems.notifyDataSetChanged();
                writeItem();
                return true;
            }
        });
    }

    public void onAddedClick(View v){
        Log.i(TAG, "button click");
        atodoItems.add(etNewItem.getText().toString());
        etNewItem.setText("");
        writeItem();
        Log.i(TAG, "Items count : " + todoItems.size());
    }

    // Access list items from a file
    private void readItems(){
        // get standard directory
        File filesDir = getFilesDir();
        Log.i(TAG, "File directory : " + filesDir.getAbsolutePath());
        File todoFile = new File(filesDir, "todo.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException ev){
            todoItems = new ArrayList<String>();
        }
    }

    private void writeItem(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, todoItems);
        } catch (IOException ev){
            ev.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
