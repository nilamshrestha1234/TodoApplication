package android.com.todoapp.todolist.mvvm_model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface TodoDao {

    @Insert
    void insert(Todo note);

    @Update
    void update(Todo note);

    @Delete
    void delete(Todo note);

    @Query("DELETE FROM todo")
    void deleteAllNote();

    @Query("SELECT * FROM todo")
    LiveData<List<Todo>> getAllNotes();
}
































