package com.example.jobtimer;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNoe(Note note);

    @Query("DELETE FROM notes_table")
    void deleteAll();

    @Query("SELECT * from notes_table")
    LiveData<List<Note>> getNotes();

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Note note);

}