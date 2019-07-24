package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private ArrayList<RecyclerItem> mRecyclerList;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1, mTextView2;
        public ImageView mDeleteImage;

        public RecyclerViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.recycler_textView);
            mTextView2 = itemView.findViewById(R.id.recycler_textView2);
            mDeleteImage = itemView.findViewById(R.id.recycler_delete);

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
        }
    }

    public RecyclerViewAdapter(ArrayList<RecyclerItem> recyclerList){
        mRecyclerList = recyclerList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        RecyclerViewHolder rvh = new RecyclerViewHolder(v, mListener);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        RecyclerItem currentItem = mRecyclerList.get(i);
        recyclerViewHolder.mTextView1.setText(currentItem.getText1());
        recyclerViewHolder.mTextView2.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return mRecyclerList.size();
    }
}
