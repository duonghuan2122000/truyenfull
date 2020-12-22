package com.soradbh.truyenfull.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.soradbh.truyenfull.R;
import com.soradbh.truyenfull.adapter.ListStoryAdapter;
import com.soradbh.truyenfull.model.ListStoryModel;
import com.soradbh.truyenfull.viewmodel.StoryByCategoryViewModel;
import com.soradbh.truyenfull.viewmodel.UpdateStoryViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoryByCategoryFragment extends Fragment {
    public static final String NAME_CAT = "NAME_CAT";
    public static final String URL_CAT = "URL_CAT";
    private String nameCategory;
    private String urlCategory;

    private StoryByCategoryViewModel viewModel;
    private ListStoryAdapter adapter;
    private ProgressBar progressBar;

    private RecyclerView recyclerView;

    public StoryByCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        nameCategory = getArguments().getString(NAME_CAT);
        urlCategory = getArguments().getString(URL_CAT);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_story_by_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.nav_category) + " " + nameCategory);
        progressBar = view.findViewById(R.id.progress_bar);
        adapter = new ListStoryAdapter();
        recyclerView = view.findViewById(R.id.recyclerview_storybycat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
        viewModel = new ViewModelProvider(requireActivity()).get(StoryByCategoryViewModel.class);
        viewModel.init(urlCategory);
        viewModel.getSpinner().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {
                if(loading){
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
        viewModel.getListStory().observe(getViewLifecycleOwner(), new Observer<PagedList<ListStoryModel>>() {
            @Override
            public void onChanged(PagedList<ListStoryModel> data) {
                viewModel.setSpinner(false);
                adapter.submitList(data);
            }
        });
        adapter.setOnItemClickListener(new ListStoryAdapter.OnItemClickListener() {
            @Override
            public void setClick(String urlStory) {
                Bundle args = new Bundle();
                args.putString(InfoStoryFragment.URL_STORY, urlStory);
                Navigation.findNavController(requireView()).navigate(R.id.action_storybycat_dest_to_infostory_dest, args);
            }
        });
    }
}
