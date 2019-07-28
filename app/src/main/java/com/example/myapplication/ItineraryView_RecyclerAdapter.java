package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ItineraryView_RecyclerAdapter extends RecyclerView.Adapter<ItineraryView_RecyclerAdapter.RecyclerViewHolder> {
    private ArrayList<ItineraryView_RecyclerItem> mRecyclerList;

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1, mTextView2;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.Itineraryrecycler_textView);
            mTextView2 = itemView.findViewById(R.id.Itineraryrecycler_textView2);
        }
    }

    public ItineraryView_RecyclerAdapter(ArrayList<ItineraryView_RecyclerItem> recyclerList){
        mRecyclerList = recyclerList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itineraryview_recycler_item, parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        ItineraryView_RecyclerItem currentItem = mRecyclerList.get(i);
        recyclerViewHolder.mTextView1.setText("ItineraryView_Day " + currentItem.getDay_number());
        recyclerViewHolder.mTextView2.setText(currentItem.getDay());

    }

    @Override
    public int getItemCount() {
        return mRecyclerList.size();
    }
}
