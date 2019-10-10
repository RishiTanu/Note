package com.contacts.memo.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.contacts.memo.R;
import com.contacts.memo.models.Note;
import com.contacts.memo.util.TimeUtility;

import java.util.ArrayList;

public class NoteRecyclerviewAdapter extends RecyclerView.Adapter<NoteRecyclerviewAdapter.ControlView> {

   private ArrayList<Note> arrayList;
   private onNoteListner onNoteListner;

    public NoteRecyclerviewAdapter(ArrayList<Note> arrayList,onNoteListner onNoteListner) {
        this.arrayList = arrayList;
        this.onNoteListner = onNoteListner;
    }

    @NonNull
    @Override
    public ControlView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_list_item,null);
        return new ControlView(view,onNoteListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ControlView controlView, int i) {

        try{
            String month = arrayList.get(i).getTimestamp().substring(0,2); //between 0 and 2 value select....
            month = TimeUtility.getMonthFromNumber(month);

            String year = arrayList.get(i).getTimestamp().substring(3); //afetr third all values select
            String timestamp = month+" "+year;

            controlView.timestamp.setText(timestamp);
            controlView.title.setText(arrayList.get(i).getTitle());

        }catch (Exception x){
            x.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ControlView extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title,timestamp;
        onNoteListner onNoteListner;

        public ControlView(@NonNull View itemView, onNoteListner onNoteListner) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            timestamp = itemView.findViewById(R.id.timestamp);
            this.onNoteListner = onNoteListner;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListner.onClickListner(getAdapterPosition());
        }
    }

    public interface onNoteListner{
        void onClickListner(int postion);
    }
}
