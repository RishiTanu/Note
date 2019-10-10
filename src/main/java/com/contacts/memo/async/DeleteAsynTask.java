package com.contacts.memo.async;

import android.os.AsyncTask;

import com.contacts.memo.models.Note;
import com.contacts.memo.persistence.NoteDao;

public class DeleteAsynTask extends AsyncTask<Note,Void,Void> {

    private NoteDao mNoteDao;
    public DeleteAsynTask(NoteDao dao) {
        mNoteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        mNoteDao.delete(notes);
        return null;
    }
}
