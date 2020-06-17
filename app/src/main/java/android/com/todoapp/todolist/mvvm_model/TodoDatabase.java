package android.com.todoapp.todolist.mvvm_model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;


@Database(entities = {Todo.class},version = 1)
@TypeConverters(DateConversion.class)
public abstract class TodoDatabase extends RoomDatabase {

    private static TodoDatabase instance;
    public abstract TodoDao noteDao();
    public static synchronized TodoDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    TodoDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();

        }
        return instance;


    }


    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private TodoDao noteDao;
        private PopulateDbAsyncTask(TodoDatabase db){
            noteDao =db.noteDao();
        }






        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Todo("Titel 1", "description1",1,(new Date(2020,1,30))));
           noteDao.insert(new Todo("Titel 2", "description2",2,(new Date(2020,1,30))));
           noteDao.insert(new Todo("Titel 3", "description3",3,(new Date(2020,1,30))));
            return null;
        }
    }











}
