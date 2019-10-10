package com.contacts.memo.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.ColumnInfo;
import android.content.Context;

import com.contacts.memo.async.DeleteAsynTask;
import com.contacts.memo.async.InsertAsynTask;
import com.contacts.memo.async.UpdateAsynTask;
import com.contacts.memo.models.Note;

import java.util.List;

public class NoteRepostary {

    private NoteDatabase mNoteDatabase;
    public NoteRepostary(Context context) {
        mNoteDatabase = NoteDatabase.getInstance(context);
    }

    public void insertNotes(Note note){
        new InsertAsynTask(mNoteDatabase.getNotedao()).execute(note);
    }

    public void updateNotes(Note note){
        new UpdateAsynTask(mNoteDatabase.getNotedao()).execute(note);
    }

    public LiveData<List<Note>> retriveNotes(){
        return mNoteDatabase.getNotedao().getNotesData();
    }

    public void deleteNotes(Note note){
        new DeleteAsynTask(mNoteDatabase.getNotedao()).execute(note);
    }
}
