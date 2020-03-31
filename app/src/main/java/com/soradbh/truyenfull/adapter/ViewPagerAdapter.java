package com.soradbh.truyenfull.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.soradbh.truyenfull.ui.EachChapterFragment;

public class ViewPagerAdapter extends FragmentStateAdapter{
    private int totalChapters;
    public ViewPagerAdapter(@NonNull Fragment fragment, int totalChapters) {
        super(fragment);
        this.totalChapters = totalChapters;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new EachChapterFragment();
    }

    @Override
    public int getItemCount() {
        return totalChapters;
    }
}
