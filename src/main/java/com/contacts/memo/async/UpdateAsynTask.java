package com.contacts.memo.async;

import android.os.AsyncTask;

import com.contacts.memo.models.Note;
import com.contacts.memo.persistence.NoteDao;

public class UpdateAsynTask extends AsyncTask<Note,Void,Void> {

    private NoteDao mNoteDao;
    public UpdateAsynTask(NoteDao dao) {
        mNoteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        mNoteDao.update(notes);
        return null;
    }
}
