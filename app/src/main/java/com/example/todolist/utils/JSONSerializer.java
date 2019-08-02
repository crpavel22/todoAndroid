package com.example.todolist.utils;

import android.content.Context;

import com.example.todolist.model.Note;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class JSONSerializer {

    private String mFileName;
    private Context mContext;


    public JSONSerializer(String mFileName, Context mContext) {
        this.mFileName = mFileName;
        this.mContext = mContext;
    }

    public void save(List<Note> notes) throws IOException, JSONException {
        JSONArray jsonArray = new JSONArray();

        if (null == notes || notes.isEmpty()) {
            return;
        }

        for (Note n : notes) {
            jsonArray.put(n.convertToJSON());
        }

        Writer writer = null;

        try {

            OutputStream out = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);

            writer = new OutputStreamWriter(out);

            writer.write(jsonArray.toString());
        } finally {
            if (null != writer)
                writer.close();
        }


    }

    public List<Note> load() throws IOException, JSONException {
        List<Note> notes = new ArrayList<>();

        BufferedReader reader = null;

        try {
            InputStream in = mContext.openFileInput(mFileName);

            reader = new BufferedReader(new InputStreamReader(in));

            StringBuilder jsStr = new StringBuilder();
            String current;

            while (null != (current = reader.readLine())) {
                jsStr.append(current);
            }

            JSONArray jsonArray = (JSONArray) new JSONTokener(jsStr.toString()).nextValue();

            for (int i = 0; i < jsonArray.length(); i++) {
                notes.add(new Note(jsonArray.getJSONObject(i)));
            }


        } catch (FileNotFoundException e) {

        } finally {
            if (null != reader) {
                reader.close();
            }
        }

        return notes;

    }

}
