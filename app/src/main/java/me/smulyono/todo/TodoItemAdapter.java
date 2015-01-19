package me.smulyono.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import me.smulyono.todo.models.TodoItems;

/**
 * Created by sannymulyono on 1/1/15.
 */
public class TodoItemAdapter extends ArrayAdapter<TodoItems> {
    
    private static class ViewHolder {
        TextView taskView;
        RatingBar ratingBarView;
        
    }
    
    public TodoItemAdapter(Context context, ArrayList<TodoItems> items){
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TodoItems todoItems = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent, false);

            // get the components and cache them
            viewHolder.taskView = (TextView)convertView.findViewById(R.id.taskName);
            viewHolder.ratingBarView = (RatingBar)convertView.findViewById(R.id.taskRating);
            // cache them
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // populate the data
        viewHolder.taskView.setText(todoItems.task);
        viewHolder.ratingBarView.setRating(todoItems.priority);

        return convertView;
    }
}
