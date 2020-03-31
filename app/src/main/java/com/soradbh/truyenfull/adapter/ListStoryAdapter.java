package com.soradbh.truyenfull.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.soradbh.truyenfull.R;
import com.soradbh.truyenfull.model.ListStoryModel;
import com.squareup.picasso.Picasso;

public class ListStoryAdapter extends PagedListAdapter<ListStoryModel, ListStoryAdapter.ListStoryViewHolder> {
    private OnItemClickListener listener;
    public interface OnItemClickListener{
        void setClick(String urlStory);
    }
    public ListStoryAdapter() {
        super(diffCallback);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public String getCurrentUrlStory(int position){
        return getItem(position).getUrlStory();
    }

    private static DiffUtil.ItemCallback<ListStoryModel> diffCallback =
            new DiffUtil.ItemCallback<ListStoryModel>() {
                @Override
                public boolean areItemsTheSame(@NonNull ListStoryModel oldItem, @NonNull ListStoryModel newItem) {
                    return oldItem.getUrlStory().equals(newItem.getUrlStory());
                }

                @Override
                public boolean areContentsTheSame(@NonNull ListStoryModel oldItem, @NonNull ListStoryModel newItem) {
                    return oldItem.getNameStory().equals(newItem.getNameStory());
                }
            };

    @NonNull
    @Override
    public ListStoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_liststory, parent, false);
        return new ListStoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListStoryViewHolder holder, int position) {
        ListStoryModel currentItem = getItem(position);
        Picasso.get()
                .load(currentItem.getImageStory())
                .placeholder(R.drawable.loading)
                .into(holder.imageViewStory);
        holder.textViewNameStory.setText(currentItem.getNameStory());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    int position = holder.getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.setClick(getItem(position).getUrlStory());
                    }
                }
            }
        });
    }

    public static class ListStoryViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageViewStory;
        public TextView textViewNameStory;
        public ListStoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewStory = itemView.findViewById(R.id.imageview_item_liststory);
            textViewNameStory = itemView.findViewById(R.id.textview_name_item_liststory);
        }
    }
}
