package android.com.todoapp.todolist.mvvm_model;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    private TodoRepository repository;
    private LiveData<List<Todo>> allNotes;

    public TodoViewModel(@NonNull Application application) {
        super(application);

        repository =new TodoRepository(application);
        allNotes= repository.getAllNotes();




    }
    public void insert(Todo note){
        repository.insert(note);
    }
    public void delete(Todo note){
        repository.delete(note);
    }
    public void update(Todo note){
        repository.update(note);
    }
    public void deleteAllNotes(){
        repository.deleteAllnotes();
    }
    public LiveData<List<Todo>> getAllNotes(){
        return allNotes;
    }





}
