package me.smulyono.todo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class EditItemActivity extends ActionBarActivity {
    private final String TAG = this.getClass().getSimpleName();


    private EditText etEditItem;
    private int position;

    public void onEditedItem(View v){
        // only allow if it is not empty text
        if (etEditItem.getText().length() > 0){
            Intent data = new Intent();
            // pass back the new edited item name
            data.putExtra("itemName", etEditItem.getText().toString());
            // pass back the position
            data.putExtra("itemPosition", position);
            // pass back the result
            this.setResult(RESULT_OK, data);
            this.finish();
        } else {
            // tell user that item name cannot be empty
            Toast.makeText(this, R.string.noEmptyString, Toast.LENGTH_SHORT).show();
        }
    }

    // all layout item assignment
    private void layoutItemAssignment(){
        // get the passed item
        String itemName = getIntent().getStringExtra("itemName");
        position = getIntent().getExtras().getInt("itemPosition");
        Log.d(TAG, "Receiving itemName " + itemName);
        Log.d(TAG, "Item position :: " + position);

        etEditItem = (EditText) findViewById(R.id.etEditItem);
        if (!itemName.isEmpty()) {
            etEditItem.setText(itemName);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        layoutItemAssignment();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
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
