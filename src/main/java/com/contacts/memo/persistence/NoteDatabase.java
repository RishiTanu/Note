package com.contacts.memo.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.contacts.memo.models.Note;

@Database(entities = Note.class,version = 1)
public abstract class NoteDatabase extends RoomDatabase {

   public static final String DATABASE_NAME = "notes_db";

   public static NoteDatabase mNooteinstance;

   static NoteDatabase getInstance(Context context){

       if(mNooteinstance == null){
           mNooteinstance = Room.databaseBuilder(
                   context.getApplicationContext(),
                   NoteDatabase.class,
                   DATABASE_NAME
           ).build();
       }
       return mNooteinstance;
   }

   public abstract NoteDao getNotedao();
}
