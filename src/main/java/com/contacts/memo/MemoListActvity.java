package com.contacts.memo;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.contacts.memo.adapters.NoteRecyclerviewAdapter;
import com.contacts.memo.models.Note;
import com.contacts.memo.persistence.NoteRepostary;
import com.contacts.memo.util.VerticalItemSpacingDecorator;

import java.util.ArrayList;
import java.util.List;

public class MemoListActvity extends AppCompatActivity implements
        NoteRecyclerviewAdapter.onNoteListner,
        View.OnClickListener{

    private RecyclerView mRecyclerView;

    private ArrayList<Note> mNote= new ArrayList<>();
    private NoteRecyclerviewAdapter mNoteRecyclerAdapter;
    private NoteRepostary mNoteRepostary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_list_activity);

        mRecyclerView = findViewById(R.id.recycle);
        findViewById(R.id.fab).setOnClickListener(this);

        mNoteRepostary = new NoteRepostary(this);

        inisilizeRecyclerview();
        retriveNotes();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setTitle("Title");
    }

    private void retriveNotes()
    {
        mNoteRepostary.retriveNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
              if(mNote.size() > 0) {
                  mNote.clear();
              }
              if(notes != null){
                  mNote.addAll(notes);
              }
              mNoteRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void inisilizeRecyclerview()
    {
        LinearLayoutManager  linearLayoutManager=new LinearLayoutManager(MemoListActvity.this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //for defining some margin between recycler view items
        VerticalItemSpacingDecorator verticalItemSpacingDecorator = new VerticalItemSpacingDecorator(10);
        mRecyclerView.addItemDecoration(verticalItemSpacingDecorator);
        new ItemTouchHelper(iTemHeler).attachToRecyclerView(mRecyclerView);

        mNoteRecyclerAdapter = new NoteRecyclerviewAdapter(mNote,this);
        mRecyclerView.setAdapter(mNoteRecyclerAdapter);
    }

    @Override
    public void onClickListner(int postion) {
        Intent intent = new Intent(MemoListActvity.this,MemoNoteActivity.class);
        intent.putExtra("object",mNote.get(postion));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
      startActivity(new Intent(MemoListActvity.this,MemoNoteActivity.class));
    }



    //on swiping recyclerView delete....
    private void deleteNote(Note note){
        mNote.remove(note);
        mNoteRecyclerAdapter.notifyDataSetChanged();

        mNoteRepostary.deleteNotes(note);
    }
    private ItemTouchHelper.SimpleCallback iTemHeler = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            deleteNote(mNote.get(viewHolder.getAdapterPosition()));
        }
    };
}
