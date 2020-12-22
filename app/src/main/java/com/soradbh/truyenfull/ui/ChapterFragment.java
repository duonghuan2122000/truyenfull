package com.soradbh.truyenfull.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.soradbh.truyenfull.R;
import com.soradbh.truyenfull.adapter.ViewPagerAdapter;
import com.soradbh.truyenfull.viewmodel.ChapterViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChapterFragment extends Fragment {
    public static final String TOTAL_CHAPTERS = "TOTAL_CHAPTERS";
    public static final String POSITION_CHAPTER = "POSITION_CHAPTER";
    public static final String TRUYEN_ID = "TRUYEN_ID";
    public static final String URL_STORY = "URL_STORY";

    private int totalChapters, positionChapter;
    private String truyenId, urlStory;

    private ChapterViewModel viewModel;

    private ViewPagerAdapter adapter;
    private ViewPager2 viewPager2;

    public ChapterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        totalChapters = getArguments().getInt(TOTAL_CHAPTERS);
        positionChapter = getArguments().getInt(POSITION_CHAPTER);
        truyenId = getArguments().getString(TRUYEN_ID);
        urlStory = getArguments().getString(URL_STORY);

        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chapter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.chapter_title);
        viewModel = new ViewModelProvider(requireActivity()).get(ChapterViewModel.class);
        adapter = new ViewPagerAdapter(this, totalChapters);
        viewPager2 = view.findViewById(R.id.viewpager_chapter);
        viewPager2.setAdapter(adapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                positionChapter = position;
                viewModel.setChapter(truyenId, position, urlStory);
            }

        });
        viewPager2.setCurrentItem(positionChapter, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.list_chapter_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_chapter:
                navigateToListChapter();
                return true;
            case R.id.back_infostory:
                navigateUpInfoStory();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void navigateToListChapter() {
        Bundle args = new Bundle();
        args.putString(TRUYEN_ID, truyenId);
        args.putString(URL_STORY, urlStory);
        args.putInt(POSITION_CHAPTER, positionChapter);
        Navigation.findNavController(requireView()).navigate(R.id.action_chapter_dest_to_listchapter_dest, args);
    }

    private void navigateUpInfoStory() {
        Bundle args = new Bundle();
        args.putString(URL_STORY, urlStory);
        Navigation.findNavController(requireView()).navigate(R.id.action_chapter_dest_to_infostory_dest, args);
    }
}
