package android.com.todoapp.todolist.task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.com.todoapp.todolist.R;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

import android.com.todoapp.todolist.mvvm_model.Todo;
import android.com.todoapp.todolist.mvvm_model.TodoViewModel;

public class MainActivity extends AppCompatActivity {

    private TodoViewModel noteViewModel;
    public static final int ADD_NOTE_REQUEST=1;
    public static final int EDIT_NOTE_REQUEST=2;


    FloatingActionButton buttonAddNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView=findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        buttonAddNote=findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, AddEditTodoActivity.class);
               // startActivity(intent);

                startActivityForResult(intent,ADD_NOTE_REQUEST);
            }
        });

        final TodoAdapter adapter=new TodoAdapter();
        recyclerView.setAdapter(adapter);

        noteViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> notes) {

                adapter.setNotes(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback( 0, ItemTouchHelper.LEFT|
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this,"Note Deleted",Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        adapter.setOnItemClickListener(new TodoAdapter.OnItemClikedListener() {
            @Override
            public void onItemClick(Todo note) {
                Intent intent=new Intent(MainActivity.this, AddEditTodoActivity.class);
                intent.putExtra(AddEditTodoActivity.EXTRA_Id,note.getId());
                intent.putExtra(AddEditTodoActivity.EXTRA_TITLE,note.getTitle());
                intent.putExtra(AddEditTodoActivity.EXTRA_DESCRIPTION,note.getDescription());
                intent.putExtra(AddEditTodoActivity.EXTRA_PRIORITY,note.getPriority());
                startActivityForResult(intent,EDIT_NOTE_REQUEST);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== ADD_NOTE_REQUEST && resultCode== RESULT_OK){
            String title= data.getStringExtra(AddEditTodoActivity.EXTRA_TITLE);
            String description= data.getStringExtra(AddEditTodoActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditTodoActivity.EXTRA_PRIORITY,1);
            Date date =new Date();
            Todo note= new Todo(title, description, priority,date);
            noteViewModel.insert(note);

            Toast.makeText(this,"Note Saved",Toast.LENGTH_SHORT).show();



        }else if(requestCode== EDIT_NOTE_REQUEST && resultCode== RESULT_OK){
            int id =data.getIntExtra(AddEditTodoActivity.EXTRA_Id,1);
            if(id==-1){
                Toast.makeText(this,"Note can't be Updated", Toast.LENGTH_SHORT).show();
                return;

            }
            String title= data.getStringExtra(AddEditTodoActivity.EXTRA_TITLE);
            String description= data.getStringExtra(AddEditTodoActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditTodoActivity.EXTRA_PRIORITY,1);
            Date date =new Date();
            Todo note= new Todo(title, description, priority,date);
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(MainActivity.this,"Note Updated",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Note not Save", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_notes:
                noteViewModel.deleteAllNotes();
                Toast.makeText(MainActivity.this,"All Note Deleted",Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
