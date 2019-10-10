package com.contacts.memo;

import android.app.Activity;
import android.gesture.GestureOverlayView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.contacts.memo.models.Note;
import com.contacts.memo.persistence.NoteRepostary;
import com.contacts.memo.util.TimeUtility;

public class MemoNoteActivity extends AppCompatActivity
        implements View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener,
        TextWatcher
{

    //widgrts
    private LineEditText mLineEditText; //for mltiple line edittext
    private EditText mEditTitle; //for title edit
    private TextView mViewTitle; //for title see

    private RelativeLayout mCheckContainer,mBackArraoContainer;
    private ImageButton mCheck,mArraow;
    private NoteRepostary mNoteRepostary;
    //vars
    private boolean mIsNewNote;
    private Note mInitialNote;
    private Note mFinalNote;

    private GestureDetector mGestureDetector;
    public static final int EDIT_MODE_ENABLED = 1;
    public static final int EDIT_MODE_DISABLED = 0;

    private int mMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_note);

        mLineEditText = findViewById(R.id.text);
        mEditTitle = findViewById(R.id.note_edit_title);
        mViewTitle = findViewById(R.id.note_text_title);
        //RElative layouts
        mCheckContainer = findViewById(R.id.check_arraow_container);
        mBackArraoContainer = findViewById(R.id.back_arraow_container);
        //imagebuttons
        mCheck = findViewById(R.id.toolbar_check);
        mArraow = findViewById(R.id.toolbar_back);

        mNoteRepostary = new NoteRepostary(this);

        if(getIncomingIntent()){
            //EditMode
            setNewNoteProperties();
            eEnabledEditMode();

        }else{
            //TextMode
            setNoteProperties();
            disAnbledEditInteraction();
        }
        setListner();
    }

    private boolean getIncomingIntent()
    {
        if(getIntent().hasExtra("object")){

            mInitialNote = getIntent().getParcelableExtra("object");

            mFinalNote = new Note();
            mFinalNote.setTitle(mInitialNote.getTitle());
            mFinalNote.setContent(mInitialNote.getContent());
            mFinalNote.setTimestamp(mInitialNote.getTimestamp());
            mFinalNote.setId(mInitialNote.getId());

            mMode = EDIT_MODE_DISABLED;
            mIsNewNote = false;
            return false;
        }
        mMode = EDIT_MODE_ENABLED;
        mIsNewNote = true;
        return  true;
    }

    private void saveChanges(){
      if(mIsNewNote) {
          saveNewNote();
      }else{
          updateNote();
      }
    }

   private void saveNewNote(){
      mNoteRepostary.insertNotes(mFinalNote);
   }

    private void eEnabledEditMode() {

        mBackArraoContainer.setVisibility(View.GONE);
        mCheckContainer.setVisibility(View.VISIBLE);

        mViewTitle.setVisibility(View.GONE);
        mEditTitle.setVisibility(View.VISIBLE);

        mMode = EDIT_MODE_ENABLED;
        enableContentInteraction();
    }

    private void disabledEditMode() {
        mBackArraoContainer.setVisibility(View.VISIBLE);
        mCheckContainer.setVisibility(View.GONE);

        mViewTitle.setVisibility(View.VISIBLE);
        mEditTitle.setVisibility(View.GONE);

        mMode = EDIT_MODE_DISABLED;
        disAnbledEditInteraction();

        //when the custom Edittext nothing data on check not saved title and content in database....

        String temp = mLineEditText.getText().toString().trim();
        temp = temp.replace("\n","");
        temp = temp.replace(" ","");


        if(temp.length() >0){
            mFinalNote.setTitle(mEditTitle.getText().toString().trim());
            mFinalNote.setContent(mLineEditText.getText().toString().trim());

            String timestamp = TimeUtility.getCurrentTime();
            mFinalNote.setTimestamp(timestamp); //set the current month and year....

            if(!mFinalNote.getContent().equals(mInitialNote.getContent())
                    || !mFinalNote.getTitle().equals(mInitialNote.getTitle())){
                saveChanges();
            }
        }
       // saveNewNote();
    }

    private void updateNote(){
      mNoteRepostary.updateNotes(mFinalNote);
    }

    private void setListner(){
        mLineEditText.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this,this);

        mCheck.setOnClickListener(this);
        mViewTitle.setOnClickListener(this);
        mArraow.setOnClickListener(this);
        mEditTitle.addTextChangedListener(this);
    }

    private void setNoteProperties(){
        mViewTitle.setText(mInitialNote.getTitle());
        mEditTitle.setText(mInitialNote.getTitle());
        mLineEditText.setText(mInitialNote.getContent());
    }

    private void hideSoftKeyboard(){
        InputMethodManager inputMethodManager =(InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();

        if(view ==  null){
            view = new View(this);
        }
         inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    private void disAnbledEditInteraction(){
        mLineEditText.setKeyListener(null);
        mLineEditText.setFocusable(false);
        mLineEditText.setFocusableInTouchMode(false);
        mLineEditText.setCursorVisible(false);
        mLineEditText.clearFocus();
    }

    private void enableContentInteraction(){
        mLineEditText.setKeyListener(new EditText(this).getKeyListener());
        mLineEditText.setFocusable(true);
        mLineEditText.setFocusableInTouchMode(true);
        mLineEditText.setCursorVisible(true);
        mLineEditText.requestFocus();
    }
    private void setNewNoteProperties() {
        mEditTitle.setText("Note Title");
        mViewTitle.setText("Note Title");

        mInitialNote = new Note();
        mFinalNote = new Note();
        mInitialNote.setTitle("Note title");
        mFinalNote.setTitle("Note title");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    //GestureDector functions.
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    //OnDoubleTap Listner function
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        eEnabledEditMode();
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.toolbar_check:
                hideSoftKeyboard();
                disabledEditMode();
                break;

            case R.id.note_text_title:
                eEnabledEditMode();
                mEditTitle.requestFocus();
                mEditTitle.setSelection(mEditTitle.length());
                break;

            case R.id.toolbar_back:
               finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {

        if (mMode == EDIT_MODE_ENABLED) {
            onClick(mCheck);
        } else {
            super.onBackPressed();
        }
    }

    //whenn the activity is paused this method trigger..
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode",mMode);
    }

    //when activity recreate this method triger
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
       mMode = savedInstanceState.getInt("mode");
       if(mMode == EDIT_MODE_ENABLED){
           eEnabledEditMode();
       }
    }

    //text watcher interface...
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mViewTitle.setText(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
