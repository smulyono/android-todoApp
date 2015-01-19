package me.smulyono.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import me.smulyono.todo.models.TodoItems;


public class TodoActivity extends ActionBarActivity {
    private final String TAG = this.getClass().getSimpleName();
    // Internal representations of the todo item list
//    private List<String> todoItems;
    private ArrayList<TodoItems> todoItems;
    // every view component will use Adapter as their models
    // Type accepted by ArrayAdapter can be any Object
//    private ArrayAdapter<String> atodoItems;
    private TodoItemAdapter atodoItems;
    // List view UI reference
    private ListView lvItems;
    // REQUEST_CODE used to determine result type
    private final int EDIT_REQUEST = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_todo);
        //
        lvItems = (ListView) findViewById(R.id.lvItems);

        readItems();
        // create adapter with the data (e.g list<String>)
//        atodoItems = new ArrayAdapter<String>(this,
//                                android.R.layout.simple_list_item_1,
//                                todoItems);
        // using customAdapter view
        atodoItems = new TodoItemAdapter(this, todoItems);
        
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
                Log.d(TAG, "Removing item in position " + position);
                todoItems.remove(position);
                flush();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Edit item in position " + position);
                // http://guides.codepath.com/android/Using-Intents-to-Create-Flows
                Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
                // passing parameters there
                i.putExtra("itemName", todoItems.get(position).task);
                i.putExtra("itemPriority", todoItems.get(position).priority);
                i.putExtra("itemPosition", position);
                
                startActivityForResult(i, EDIT_REQUEST);
            }
        });
    }
    
    public void addNewTask(TodoItems newTask){
        atodoItems.add(newTask);
        writeItem();
        Log.d(TAG, "Items count : " + todoItems.size());
    }
    
    // Access list items from a file
    private void readItems(){
        ActiveAndroid.clearCache();
        // get data from DB
        List<TodoItems> recs = new Select().from(TodoItems.class).execute();
        todoItems = new ArrayList<TodoItems>();
        if (recs != null && recs.size() > 0){
            for (TodoItems rec : recs){
                todoItems.add(rec);
            }
        }
    }

    private void writeItem(){
        // write data to DB
        ActiveAndroid.beginTransaction();
        try {
            for (TodoItems todoItem : todoItems){
                todoItem.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    private void flush(){
        atodoItems.notifyDataSetChanged();
        writeItem();
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
        if (id == R.id.action_settings_additem) {
            AddNewItemFragment addItemDialog = AddNewItemFragment.newInstance();
            addItemDialog.show(getFragmentManager(), "Add Item");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // when activity received back
        if (requestCode == EDIT_REQUEST && resultCode == RESULT_OK){
            String newItemName = data.getExtras().getString("itemName");
            int position = data.getExtras().getInt("itemPosition");
            float newPriority = data.getExtras().getFloat("itemPriority");
            if (newItemName != null && !newItemName.isEmpty()) {
                Log.d(TAG, "Updating " + position + " with " + newItemName + " and priority " + newPriority);
                
                // get the old TodoItems record
                TodoItems rec =  todoItems.get(position);
                rec.task = newItemName;
                // show some random rating
                rec.priority = newPriority;
                todoItems.set(position, rec);
                
                flush();
                // show some toast
                Toast.makeText(this, R.string.editMessage, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
