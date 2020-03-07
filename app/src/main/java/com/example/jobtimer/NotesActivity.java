package com.example.jobtimer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    //TextView for displaying title
    TextView textViewNotestitle;

    //Button for new note
    Button newNoteButton;

    //Button to save new note
    Button saveNoteButton;

    //TextEdit for new Note
    EditText newNoteEdit;

    //ScrollView to display notes
    LinearLayout wrapperAll;

    //Viewmodel for Notes
    NoteViewModel noteViewModel;

    //int for identifying job
    int idIn;

    //ViewModel for Jobs
    RoomJobViewModel roomJobViewModel;

    //Job for usage
    RoomJob rJob;

    //Note List to update
    List<Note> notesAll;

    //Editable list
    List<EditText> contentsTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        //layout for all notes
        wrapperAll = (LinearLayout) findViewById(R.id.layoutForNotes);

        //Identify the job, the user has selected:
        Intent intent = getIntent();
        final int id = intent.getIntExtra("id", -1);
        idIn = id;

        //Editable List
        contentsTexts = new ArrayList<EditText>();

        //TextView For Title
        textViewNotestitle = (TextView) findViewById(R.id.textViewNotesTitle);

        //Prepare Variable for current Job
        rJob = new RoomJob("new Job");
        //Get Current Job
        roomJobViewModel = new ViewModelProvider(this).get(RoomJobViewModel.class);
        roomJobViewModel.getAllJobs().observe(this, new Observer<List<RoomJob>>() {
            @Override
            public void onChanged(@Nullable final List<RoomJob> rJobs) {

                //sort out current job
                for (RoomJob rIJob : rJobs){
                    if (rIJob.getRid() == idIn){
                        //get current Job
                        rJob = rIJob;
                        textViewNotestitle.setText(rJob.getTitle() + " Notes");
                    }
                }
            }
        });

        //List for NOtes
        notesAll = new ArrayList<Note>();

        //ViewModel for Notes
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable final List<Note> notes) {
                System.out.println(notes + " job timings");
                wrapperAll.removeAllViews();
                notesAll.clear();
                contentsTexts.clear();
                for(int i = notes.size() - 1; i > 0; i--){
                    if(notes.get(i).getJobId() == idIn){
                        notesAll.add(notes.get(i));
                        paintNote(notes.get(i));
                    }

                }
            }
        });

        //TextEdit new Note
        //newNoteEdit = (EditText) findViewById(R.id.editTextNewNote);

        //New Note Button
        newNoteButton = (Button) findViewById(R.id.buttonNewNote);



        //Clicklistener for new Note Button
        newNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewNote();

            }
        });

        //Save Note Button
        saveNoteButton = (Button) findViewById(R.id.buttonSaveNote);

        //Clicklistener for save Note Button
        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNotes();
            }
        });

    }



    public void paintNote(Note note){
        //Create EditText for new note
        EditText newNoteEdit2 = new EditText(this);

        //Give it the content of note
        newNoteEdit2.setText(note.getContent());

        //Set Backgroundcolor of edittext
        newNoteEdit2.setBackgroundColor(getResources().getColor(R.color.lightyellow));


        //Create Params for line seperators
        ConstraintLayout.LayoutParams constLineParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        constLineParams.setMargins(25, 10, 25, 0);

        //Create Line Seperators
        ConstraintLayout constline = new ConstraintLayout(this);
        constline.setLayoutParams(constLineParams);

        //Set Seperatorcolors and height
        final float scale = this.getResources().getDisplayMetrics().density;
        int constBottomlineHeight = (int) (5 * scale + 0.5f);
        constline.getLayoutParams().height = constBottomlineHeight;
        constline.setBackgroundColor(getResources().getColor(R.color.lightgreen));

        //add view to wrapper all linear layout
        wrapperAll.addView(newNoteEdit2);
        wrapperAll.addView(constline);
        contentsTexts.add(newNoteEdit2);
    }

    public void createNewNote(){

        //Create Note
        Note nextNote = new Note(idIn);

        //Set the Content since edittext will "Wrap content"
        nextNote.setContent("New Note");

        //insert Note into db
        noteViewModel.insertNote(nextNote);
    }

    public void saveNotes(){

        //iterate through all notes that have been extracted
        for(int i = 0; i < notesAll.size(); i++){
            //create an editable for multiline string, from edittext arraylist contentsTexts
            Editable contentEditable = contentsTexts.get(i).getEditableText();

            //set the content of the current note to current edittext string
            notesAll.get(i).setContent(contentEditable.toString().trim());

            //update note
            noteViewModel.update(notesAll.get(i));

            //make toast to inform user
            Toast.makeText(getApplicationContext(),"Notes Updated",Toast.LENGTH_SHORT).show();
        }
    }
}
