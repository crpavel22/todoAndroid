package com.example.todolist.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.todolist.MainActivity;
import com.example.todolist.R;
import com.example.todolist.model.Note;

public class DialogNewNote extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_new_note, null);

        EditText editTitle = dialogView.findViewById(R.id.edit_title);
        EditText editDescription = dialogView.findViewById(R.id.edit_description);

        CheckBox cbIdea = dialogView.findViewById(R.id.cb_idea);
        CheckBox cbImportant = dialogView.findViewById(R.id.cb_important);
        CheckBox cbTodo = dialogView.findViewById(R.id.cb_todo);

        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
        Button btnOk = dialogView.findViewById(R.id.btn_ok);


        builder.setView(dialogView).setMessage("Add new note");

        btnCancel.setOnClickListener(v -> dismiss());

        btnOk.setOnClickListener(v -> {

            Note newNote = new Note();

            newNote.setTitle(editTitle.getText().toString());
            newNote.setDescription(editDescription.getText().toString());

            newNote.setIdea(cbIdea.isChecked());
            newNote.setImportant(cbImportant.isChecked());
            newNote.setTodo(cbTodo.isChecked());

            MainActivity callingActivity = (MainActivity) getActivity();

            callingActivity.createNewNote(newNote);

            dismiss();
        });

        return builder.create();

    }
}
