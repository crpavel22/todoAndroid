package com.example.todolist.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.todolist.R;
import com.example.todolist.model.Note;

public class DialogShowNote extends DialogFragment {

    private Note mNote;


    public void sendNoteSelected(Note noteSelected) {
        mNote = noteSelected;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_show_note, null);

        ((TextView) dialogView.findViewById(R.id.tv_title)).setText(mNote.getTitle());
        ((TextView) dialogView.findViewById(R.id.tv_description)).setText(mNote.getDescription());
        ImageView imageImportant = dialogView.findViewById(R.id.iv_important);
        ImageView imageTodo = dialogView.findViewById(R.id.iv_todo);
        ImageView imageIdea = dialogView.findViewById(R.id.iv_idea);


        imageImportant.setVisibility(mNote.isImportant() ? View.VISIBLE : View.GONE);
        imageTodo.setVisibility(mNote.isTodo() ? View.VISIBLE : View.GONE);
        imageIdea.setVisibility(mNote.isIdea() ? View.VISIBLE : View.GONE);

        dialogView.findViewById(R.id.btn_ok).setOnClickListener(v -> dismiss());
        builder.setView(dialogView).setMessage("Note: ");

        return builder.create();
    }
}
