package android.com.todoapp.todolist.task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.com.todoapp.todolist.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditTodoActivity extends AppCompatActivity {
    public static final String EXTRA_Id="android.com.todoapp.todolist.task.EXTRA_Id";
    public static final String EXTRA_TITLE="android.com.todoapp.todolist.task.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION="android.com.todoapp.todolist.task.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY="android.com.todoapp.todolist.task.EXTRA_PRIORITY";
    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriorty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnotes);

        editTextDescription=findViewById(R.id.edit_text_description);
        editTextTitle=findViewById(R.id.edit_text_title);
        numberPickerPriorty=findViewById(R.id.number_picker_priority);

        numberPickerPriorty.setMaxValue(3);
        numberPickerPriorty.setMinValue(1);

        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);

        Intent intent=getIntent();
        if(intent.hasExtra(EXTRA_Id)){
            setTitle("Edit Note");
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            numberPickerPriorty.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));

        }else{
            setTitle("Add Note");
        }

    }
    public void saveNote(){
        String title= editTextTitle.getText().toString();
        String description= editTextDescription.getText().toString();
        int priority=numberPickerPriorty.getValue();

        if(title.trim().isEmpty()|| description.trim().isEmpty()){
            Toast.makeText(this,"Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data= new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_PRIORITY,priority);
        int id=getIntent().getIntExtra(EXTRA_Id,-1);
        if(id !=-1){
            data.putExtra(EXTRA_Id, id);
        }
        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.add_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
