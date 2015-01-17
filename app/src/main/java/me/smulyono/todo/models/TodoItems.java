package me.smulyono.todo.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by sannymulyono on 1/16/15.
 */
@Table(name="TodoItems")
public class TodoItems extends Model {
    @Column(name="task")
    public String task;
    
    public TodoItems(){
        super();
    }
}
