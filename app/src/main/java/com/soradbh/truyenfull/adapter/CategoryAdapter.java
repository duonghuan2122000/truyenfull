package com.soradbh.truyenfull.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soradbh.truyenfull.R;
import com.soradbh.truyenfull.model.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private List<CategoryModel> listCategories;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void setClick(CategoryModel categoryModel);
    }

    public CategoryAdapter(List<CategoryModel> listCategories) {
        this.listCategories = listCategories;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void addListCategories(List<CategoryModel> data){
        listCategories.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder holder, int position) {
        CategoryModel currentItem = listCategories.get(position);
        holder.textViewCategory.setText(currentItem.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    int position = holder.getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.setClick(listCategories.get(position));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCategories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewCategory;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCategory = itemView.findViewById(R.id.textview_list);
        }
    }
}
