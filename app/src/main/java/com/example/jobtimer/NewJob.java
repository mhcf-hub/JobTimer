package com.example.jobtimer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import static android.provider.Contacts.SettingsColumns.KEY;

public class NewJob extends AppCompatActivity {

    AllJobs allJobs;

    View viewColor0;
    View viewColor1;
    View viewColor2;
    View viewColor3;

    View viewColorChosen;

    EditText title;

    Button newJobSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_job);

        allJobs = new AllJobs();

        viewColor0  = (View) findViewById(R.id.viewColor0);
        viewColor1  = (View) findViewById(R.id.viewColor1);
        viewColor2  = (View) findViewById(R.id.viewColor2);
        viewColor3  = (View) findViewById(R.id.viewColor3);

        viewColorChosen = (View) findViewById(R.id.viewColorChosen);
        viewColorChosen.setBackgroundColor(getResources().getColor(R.color.blue));



        viewColor0.setBackgroundColor(getResources().getColor(R.color.blue));
        viewColor1.setBackgroundColor(getResources().getColor(R.color.red));
        viewColor2.setBackgroundColor(getResources().getColor(R.color.green));
        viewColor3.setBackgroundColor(getResources().getColor(R.color.yellow));

        viewColor0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewColorChosen.setBackgroundColor(getResources().getColor(R.color.blue));
            }
        });

        viewColor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewColorChosen.setBackgroundColor(getResources().getColor(R.color.red));
            }
        });

        viewColor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewColorChosen.setBackgroundColor(getResources().getColor(R.color.green));
            }
        });

        viewColor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewColorChosen.setBackgroundColor(getResources().getColor(R.color.yellow));
            }
        });

        newJobSave = (Button) findViewById(R.id.buttonSingleSave);
        newJobSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveJob();
            }
        });




        load();
    }

    public void saveJob(){
        title = (EditText) findViewById(R.id.editTextTitle);
        String titleSet = title.getText().toString();

        int color = Color.TRANSPARENT;
        Drawable background = viewColorChosen.getBackground();
        if (background instanceof ColorDrawable)
            color = ((ColorDrawable) background).getColor();

        Job job = new Job(titleSet, color, allJobs.getJobs().size());
        job.setSeconds(0);
        allJobs.addJob(job);
        save();

        Intent intent = new Intent(NewJob.this, MainActivity.class);
        startActivity(intent);




    }

    public void load(){
        try {
            allJobs =  (AllJobs) InternalStorage.readObject(this, KEY);
        } catch (IOException e) {
            System.out.println(e + " e1");
        } catch (ClassNotFoundException e) {
            System.out.println(e + " e2");
        }


    }


    public void save(){

        try {
            // Save the list of entries to internal storage
            InternalStorage.writeObject(this, KEY, allJobs);
        } catch (IOException e) {
            System.out.println(e + " e2");
        }
    }
}
