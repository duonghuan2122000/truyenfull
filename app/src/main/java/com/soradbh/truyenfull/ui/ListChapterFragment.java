package com.soradbh.truyenfull.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soradbh.truyenfull.R;
import com.soradbh.truyenfull.adapter.ListChapterAdapter;
import com.soradbh.truyenfull.viewmodel.ListChapterViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListChapterFragment extends Fragment {
    private String truyenId;
    private ListChapterViewModel viewModel;
    private List<String> listChapters = new ArrayList<>();
    private int totalChapters;
    private int positionChapter;
    private String urlStory;
    private ListChapterAdapter adapter;
    private RecyclerView recyclerView;

    public ListChapterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        truyenId = getArguments().getString(ChapterFragment.TRUYEN_ID);
        urlStory = getArguments().getString(ChapterFragment.URL_STORY);
        positionChapter = getArguments().getInt(ChapterFragment.POSITION_CHAPTER);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_chapter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.list_chapter);

        adapter = new ListChapterAdapter(listChapters, positionChapter);
        recyclerView = view.findViewById(R.id.recyclerview_listchapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity()).get(ListChapterViewModel.class);
        viewModel.getListChapters().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> data) {
                adapter.addListChapters(data);
                totalChapters = data.size();
            }
        });

        adapter.setOnItemClickListener(new ListChapterAdapter.OnItemClickListener() {
            @Override
            public void setClick(int position) {
                Bundle args = new Bundle();
                args.putInt(ChapterFragment.POSITION_CHAPTER, position);
                args.putInt(ChapterFragment.TOTAL_CHAPTERS, totalChapters);
                args.putString(ChapterFragment.TRUYEN_ID, truyenId);
                args.putString(ChapterFragment.URL_STORY, urlStory);
                Navigation.findNavController(requireView()).navigate(R.id.action_listchapter_dest_to_chapter_dest, args);
            }
        });
        recyclerView.scrollToPosition(positionChapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.fetchListChapters(truyenId);
    }
}
