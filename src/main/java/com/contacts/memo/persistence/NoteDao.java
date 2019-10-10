package com.contacts.memo.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.contacts.memo.models.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    long[] insertNotes(Note... notes);

    //{1,2,3} //when  multiple notes insert it will return this type value..
    //... called elpsi == Note[] notes

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotesData();

    //if you want to make a custom query..
    //@Query("SELECT * FROM notes WHERE id = :id")
    //List<Note> getAllData(int id);

    //@Query("SELECT * FROM notes WHERE title LIKE :title")
    //List<Note> getAllData(String title);

    @Update
    int update(Note... notes);

    @Delete
    int delete(Note... notes);
}
