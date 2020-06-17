package android.com.todoapp.todolist.mvvm_model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TodoRepository {

    private TodoDao noteDao;
    private LiveData<List<Todo>> allNotes;
    public TodoRepository(Application application){
        TodoDatabase database= TodoDatabase.getInstance(application);
        noteDao=database.noteDao();
        allNotes=noteDao.getAllNotes();
    }

    public void insert(Todo note){
        new InsertNoteAsyncTask(noteDao).execute(note);
    }


    public void  update(Todo note){
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }
    public void delete(Todo note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }
    public void deleteAllnotes(){
        new DeleteAllNotesAsyncTask(noteDao).execute();

    }

    public LiveData<List<Todo>> getAllNotes(){
        return allNotes;
    }
    private static class InsertNoteAsyncTask extends AsyncTask<Todo, Void,Void>{
        private TodoDao noteDao;

        private InsertNoteAsyncTask(TodoDao noteDao){
            this.noteDao =noteDao;
        }

        @Override
        protected Void doInBackground(Todo... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }
    private static class UpdateNoteAsyncTask extends AsyncTask<Todo, Void,Void>{
        private TodoDao noteDao;

        private UpdateNoteAsyncTask(TodoDao noteDao){
            this.noteDao =noteDao;
        }

        @Override
        protected Void doInBackground(Todo... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<Todo, Void,Void>{
        private TodoDao noteDao;

        private DeleteNoteAsyncTask(TodoDao noteDao){
            this.noteDao =noteDao;
        }

        @Override
        protected Void doInBackground(Todo... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }


    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void,Void>{
        private TodoDao noteDao;

        private DeleteAllNotesAsyncTask(TodoDao noteDao){
            this.noteDao =noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNote();
            return null;
        }
    }




}

