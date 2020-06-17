package android.com.todoapp.todolist.mvvm_model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "todo")
public class Todo {
    @PrimaryKey(autoGenerate = true)
    private int id;


    private String title;
    private String description;
    private int priority;
    private Date date;

    public Todo(String title, String description, int priority, Date date) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public Date getDate() {
        return date;
    }
}
