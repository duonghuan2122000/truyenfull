package com.soradbh.truyenfull.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.soradbh.truyenfull.R;
import com.soradbh.truyenfull.adapter.CategoryAdapter;
import com.soradbh.truyenfull.model.CategoryModel;
import com.soradbh.truyenfull.viewmodel.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    private CategoryViewModel viewModel;
    private List<CategoryModel> listCategories = new ArrayList<>();

    private CategoryAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.nav_category);
        progressBar = view.findViewById(R.id.progress_bar);
        adapter = new CategoryAdapter(listCategories);
        recyclerView = view.findViewById(R.id.recyclerview_category);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        recyclerView.setAdapter(adapter);
        viewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        viewModel.getSpinner().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {
                if (loading) {
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
        viewModel.getListCategories().observe(getViewLifecycleOwner(), new Observer<List<CategoryModel>>() {
            @Override
            public void onChanged(List<CategoryModel> data) {
                Log.d("Cateogory", "" + data.size());
                adapter.addListCategories(data);
            }
        });
        adapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void setClick(CategoryModel categoryModel) {
                Bundle args = new Bundle();
                args.putString(StoryByCategoryFragment.NAME_CAT, categoryModel.getName());
                args.putString(StoryByCategoryFragment.URL_CAT, categoryModel.getUrl());
                Navigation.findNavController(requireView()).navigate(R.id.action_category_dest_to_storybycat_dest, args);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.setListCategories();
    }
}
