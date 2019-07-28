package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItineraryViewRecyclerAdapter extends RecyclerView.Adapter<ItineraryViewRecyclerAdapter.RecyclerViewHolder> {
    private ArrayList<ListViewRecyclerItem> mRecyclerList;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onDoneClick(int position);
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1, mTextView2;
        public EditText mHours;
        public ImageView mDeleteImage;
        public ImageView mDoneImage;

        public RecyclerViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.recycler_textView);
            mTextView2 = itemView.findViewById(R.id.recycler_textView2);
            mHours = itemView.findViewById(R.id.recycler_hours);
            mDeleteImage = itemView.findViewById(R.id.recycler_delete);
            mDoneImage = itemView.findViewById(R.id.recycler_done);

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            mDoneImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDoneClick(position);
                        }
                    }
                }
            });
        }
    }

    public ItineraryViewRecyclerAdapter(ArrayList<ListViewRecyclerItem> recyclerList){
        mRecyclerList = recyclerList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_recycler_item, parent, false);
        RecyclerViewHolder rvh = new RecyclerViewHolder(v, mListener);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        ListViewRecyclerItem currentItem = mRecyclerList.get(i);
        recyclerViewHolder.mTextView1.setText(currentItem.getText1());
        recyclerViewHolder.mTextView2.setText(currentItem.getText2());
        if (currentItem.getTextHours() != 0){
            recyclerViewHolder.mHours.setText(currentItem.getTextHours());
        }
    }

    @Override
    public int getItemCount() {
        return mRecyclerList.size();
    }
}
