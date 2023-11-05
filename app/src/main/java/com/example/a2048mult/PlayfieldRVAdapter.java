package com.example.a2048mult;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlayfieldRVAdapter extends RecyclerView.Adapter<PlayfieldRVAdapter.PlayfieldViewHolder> {

    PlayfieldRVAdapter(){
        //empty constructor
    }

    @NonNull
    @Override
    public PlayfieldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayfieldViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    PlayfieldRVAdapter(int width, int height, int[][] data){

    }

    class PlayfieldViewHolder extends RecyclerView.ViewHolder{

        public PlayfieldViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        //private void bind()
    }
}
