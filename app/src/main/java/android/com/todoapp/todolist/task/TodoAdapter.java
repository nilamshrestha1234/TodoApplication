package android.com.todoapp.todolist.task;



import android.com.todoapp.todolist.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.com.todoapp.todolist.mvvm_model.Todo;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.NoteHolder> {

    private List<Todo> notes= new ArrayList<>();
    private OnItemClikedListener listener;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_items,parent, false);

        return new NoteHolder(itemview) ;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Todo currentNote= notes.get(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());
        holder.textViewdate.setText(currentNote.getDate().toString());
        holder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));

    }

    @Override
    public int getItemCount() {

        return notes.size();
    }

    public void setNotes(List<Todo> notes){
        this.notes=notes;
        notifyDataSetChanged();
    }
    public Todo getNoteAt(int position){
        return notes.get(position);
    }


    class NoteHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView  textViewPriority;
        private  TextView textViewdate;


        public NoteHolder(@NonNull View itemView) {

            super(itemView);


            textViewDescription=itemView.findViewById(R.id.text_view_description);
            textViewPriority=itemView.findViewById(R.id.text_view_priority);
            textViewTitle=itemView.findViewById(R.id.text_view_title);
            textViewdate=itemView.findViewById(R.id.text_view_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position =getAdapterPosition();
                    if(listener !=null && position !=RecyclerView.NO_POSITION){
                        listener.onItemClick(notes.get(position));
                    }

                }
            });
        }
    }

    public interface OnItemClikedListener{
        void onItemClick(Todo note);
    }
    public void setOnItemClickListener(OnItemClikedListener listener){
        this.listener=listener;
    }
}
