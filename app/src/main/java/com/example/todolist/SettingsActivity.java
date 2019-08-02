package com.example.todolist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.todolist.utils.Constants.DEFAULT_SOUND_OPTION;
import static com.example.todolist.utils.Constants.FAST;
import static com.example.todolist.utils.Constants.NONE;
import static com.example.todolist.utils.Constants.SHARED_PREF_ANIMATION_SPEED;
import static com.example.todolist.utils.Constants.SHARED_PREF_FILE_NAME;
import static com.example.todolist.utils.Constants.SHARED_PREF_SOUND;
import static com.example.todolist.utils.Constants.SLOW;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";

    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;
    private Boolean mSound;
    private Integer mAnimationOption;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        CheckBox cbSound = findViewById(R.id.cb_sound);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);


        mPrefs = getSharedPreferences(SHARED_PREF_FILE_NAME, MODE_PRIVATE);
        mEditor = mPrefs.edit();

        mSound = mPrefs.getBoolean(SHARED_PREF_SOUND, DEFAULT_SOUND_OPTION);
        mAnimationOption = mPrefs.getInt(SHARED_PREF_ANIMATION_SPEED, FAST);

        cbSound.setChecked(mSound);
        radioGroup.clearCheck();

        switch (mAnimationOption) {

            case FAST:
                radioGroup.check(R.id.rd_fast);
                break;
            case SLOW:
                radioGroup.check(R.id.rd_slow);
                break;
            case NONE:
                radioGroup.check(R.id.rd_none);
                break;
            default:
                Log.e(TAG, "onCreate: \"El valor no esta dentro del rango correcto\"");
        }

        cbSound.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            mSound = isChecked;
            mEditor.putBoolean(SHARED_PREF_SOUND, mSound);

        });

        radioGroup.setOnCheckedChangeListener((rg, id) -> {

            RadioButton rb = rg.findViewById(id);

            switch (rb.getId()) {
                case R.id.rd_fast:
                    mAnimationOption = FAST;
                    break;
                case R.id.rd_slow:
                    mAnimationOption = SLOW;
                    break;
                case R.id.rd_none:
                    mAnimationOption = NONE;
                    break;
            }

            mEditor.putInt(SHARED_PREF_ANIMATION_SPEED, mAnimationOption);

        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        mEditor.apply();
    }
}
