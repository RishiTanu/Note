package com.contacts.memo.async;

import android.os.AsyncTask;

import com.contacts.memo.models.Note;
import com.contacts.memo.persistence.NoteDao;

public class InsertAsynTask extends AsyncTask<Note,Void,Void> {

    private NoteDao mNoteDao;
    public InsertAsynTask(NoteDao dao) {
        mNoteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        mNoteDao.insertNotes(notes);
        return null;
    }
}
