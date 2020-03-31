package com.soradbh.truyenfull.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soradbh.truyenfull.R;

import java.util.ArrayList;
import java.util.List;

public class ListChapterAdapter extends RecyclerView.Adapter<ListChapterAdapter.ListChapterViewHolder> {
    private OnItemClickListener listener;
    private int positionSelectedChapter;
    public interface OnItemClickListener{
        void setClick(int position);
    }
    private List<String> listChapters = new ArrayList<>();
    public ListChapterAdapter(List<String> listChapters, int positionSelectedChapter){
        this.listChapters = listChapters;
        this.positionSelectedChapter = positionSelectedChapter;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void setBackgroundSelected(int position){
        positionSelectedChapter = position;
        notifyItemChanged(position);
    }
    @NonNull
    @Override
    public ListChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ListChapterViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListChapterViewHolder holder, int position) {
        holder.textViewName.setText(listChapters.get(position));
        if(positionSelectedChapter == position){
            holder.textViewName.setTextColor(Color.BLUE);
        }
    }

    public void addListChapters(List<String> data){
        listChapters.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listChapters.size();
    }

    public static class ListChapterViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName;
        public ListChapterViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textview_list);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.setClick(position);
                        }
                    }
                }
            });
        }
    }
}
