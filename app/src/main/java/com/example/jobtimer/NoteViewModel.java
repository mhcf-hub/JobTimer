package com.example.jobtimer;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NoteViewModel extends AndroidViewModel {


    private JTRepo JTRepo;

    private LiveData<List<Note>> allNotes;

    public NoteViewModel(Application application) {
        super(application);
        JTRepo = new JTRepo(application);
        allNotes = JTRepo.getNotes();
    }

    LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public void insertNote(Note note) {
        JTRepo.insertNote(note);
    }

    public void update(Note note) {
        JTRepo.updateNote(note);
    }
}
