package com.example.todolist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.example.todolist.utils.Constants;
import com.example.todolist.utils.JSONSerializer;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteAdapter mNoteAdapter;

    private Boolean mSound;
    private Integer mAnimationOprions;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onResume() {
        super.onResume();
        mSharedPreferences = getSharedPreferences(Constants.SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        mSound = mSharedPreferences.getBoolean(Constants.SHARED_PREF_SOUND, Constants.DEFAULT_SOUND_OPTION);
        mAnimationOprions = mSharedPreferences.getInt(Constants.SHARED_PREF_ANIMATION_SPEED, Constants.FAST);
    }

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

        switch (item.getItemId()) {

            case R.id.action_add:
                DialogNewNote dialog = new DialogNewNote();

                dialog.show(getSupportFragmentManager(), "note_create");
                break;

            case R.id.action_settings:

                Intent intent = new Intent(this, SettingsActivity.class);

                startActivity(intent);

                break;
        }

        return false;
    }


    // https://oig.ssa.gov/español
    // 1800 2690271
    //ide.mti.ty


    //robodeindentidad.gov


    @Override
    protected void onPause() {
        super.onPause();
        mNoteAdapter.saveNotes();
    }

    public class NoteAdapter extends BaseAdapter {

        private static final String TAG = "NoteAdapter";
        List<Note> noteList = new ArrayList<>();
        private JSONSerializer mSerializer;

        public NoteAdapter() {
            mSerializer = new JSONSerializer(Constants.JSON_FILE_NAME, MainActivity.this.getApplicationContext());

            try {
                noteList = mSerializer.load();
            } catch (JSONException e) {
                Log.e(TAG, "NoteAdapter: there is an error at JSON file ", e);
            } catch (IOException e) {
                Log.e(TAG, "NoteAdapter: there is an error at work with file ", e);
            }

        }

        public void saveNotes() {
            try {
                mSerializer.save(noteList);
            } catch (JSONException e) {
                Log.e(TAG, "NoteAdapter: there is an error at JSON file ", e);
            } catch (IOException e) {
                Log.e(TAG, "NoteAdapter: there is an error at JSON file ", e);
            }
        }

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
