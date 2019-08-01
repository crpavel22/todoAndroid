package com.example.todolist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.fragment.DialogNewNote;
import com.example.todolist.fragment.DialogShowNote;
import com.example.todolist.model.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteAdapter mNoteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNoteAdapter = new NoteAdapter();
        ListView listNote = findViewById(R.id.list_view);

        listNote.setAdapter(mNoteAdapter);

        listNote.setOnItemClickListener(((adapterView, view, itemIndex, itemId) -> {
            Note tempNote = mNoteAdapter.getItem(itemIndex);

            DialogShowNote dialog = new DialogShowNote();

            dialog.sendNoteSelected(tempNote);

            dialog.show(getSupportFragmentManager(), "show_note");

        }));
    }


    public void createNewNote(Note newNote) {
        //TODO: Create functionality to create a new Note.
        mNoteAdapter.addNote(newNote);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_add) {
            DialogNewNote dialog = new DialogNewNote();

            dialog.show(getSupportFragmentManager(), "note_create");
        }

        return false;
    }


    // https://oig.ssa.gov/español
    // 1800 2690271
    //ide.mti.ty


    //robodeindentidad.gov


    public class NoteAdapter extends BaseAdapter {

        List<Note> noteList = new ArrayList<>();

        @Override
        public int getCount() {
            return noteList.size();
        }

        @Override
        public Note getItem(int index) {
            return noteList.get(index);
        }

        @Override
        public long getItemId(int index) {
            return index;
        }

        @Override
        public View getView(int itemIndex, View view, ViewGroup viewGroup) {


            if (null == view) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_item, viewGroup, false);
            }

            TextView tvTitle = view.findViewById(R.id.tv_title);
            TextView tvDescription = view.findViewById(R.id.tv_description);

            ImageView ivImportant = view.findViewById(R.id.iv_important);
            ImageView ivTodo = view.findViewById(R.id.iv_todo);
            ImageView ivIdea = view.findViewById(R.id.iv_idea);

            Note note = noteList.get(itemIndex);

            ivIdea.setVisibility(note.isIdea() ? View.VISIBLE : View.GONE);
            ivTodo.setVisibility(note.isTodo() ? View.VISIBLE : View.GONE);
            ivImportant.setVisibility(note.isImportant() ? View.VISIBLE : View.GONE);

            tvTitle.setText(note.getTitle());
            tvDescription.setText(note.getDescription());


            return view;
        }


        public void addNote(Note n) {
            noteList.add(n);
            notifyDataSetChanged();
        }
    }


}
