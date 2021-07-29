package com.mannosteven.mygradeapp;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class SyllabusItemAdapter extends RecyclerView.Adapter<SyllabusItemAdapter.SyllabusItemViewHolder> {

    private ArrayList<SyllabusItem> syllabusItemList;
    private SyllabusItemAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(SyllabusItemAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    public static class SyllabusItemViewHolder extends RecyclerView.ViewHolder{

        public EditText nameEditText;
        public EditText weightEditText;
        public EditText gradeEditText;
        public ImageView deleteImageView;

        public SyllabusItemViewHolder(View itemView, SyllabusItemAdapter.OnItemClickListener listener) {
            super(itemView);
            nameEditText = itemView.findViewById(R.id.name);
            weightEditText = itemView.findViewById(R.id.weight);
            gradeEditText = itemView.findViewById(R.id.grade);
            deleteImageView = itemView.findViewById(R.id.delete_btn);

           deleteImageView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(listener != null){
                       int position = getAdapterPosition();
                       if(position != RecyclerView.NO_POSITION){
                           listener.onDeleteClick(position);
                       }
                   }
               }
           });
        }
    }

    public SyllabusItemAdapter(ArrayList<SyllabusItem> syllabusItemList){
        this.syllabusItemList = syllabusItemList;
    }

    @Override
    public SyllabusItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.syllabus_item, viewGroup, false);
        //ExampleAdapter.ExampleViewHolder exampleViewHolder = new ExampleAdapter.ExampleViewHolder(v, mListener);
        SyllabusItemAdapter.SyllabusItemViewHolder syllabusItemViewHolder = new SyllabusItemAdapter.SyllabusItemViewHolder(v, mListener);
        return syllabusItemViewHolder;
    }

    @Override
    public void onBindViewHolder(SyllabusItemViewHolder syllabusItemViewHolder, int i) {
        SyllabusItem currentItem = syllabusItemList.get(i);
        syllabusItemViewHolder.nameEditText.setText(currentItem.getItemName());
        syllabusItemViewHolder.weightEditText.setText(currentItem.getWeight());
        syllabusItemViewHolder.gradeEditText.setText(currentItem.getGrade());
    }

    @Override
    public int getItemCount() {
        return (syllabusItemList == null) ? 0 : syllabusItemList.size();
    }

}
