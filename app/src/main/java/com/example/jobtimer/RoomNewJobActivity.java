package com.example.jobtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class RoomNewJobActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_new_job);

        Intent replyIntent = new Intent();
            String title = "titletest";
            replyIntent.putExtra(EXTRA_REPLY, title);
            setResult(RESULT_OK, replyIntent);
//        if (TextUtils.isEmpty(mEditWordView.getText())) {
//            setResult(RESULT_CANCELED, replyIntent);
//        } else {
//            String word = mEditWordView.getText().toString();
//            replyIntent.putExtra(EXTRA_REPLY, word);
//            setResult(RESULT_OK, replyIntent);
//        }
        finish();

    }
}