package com.soradbh.truyenfull.ui;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.soradbh.truyenfull.R;
import com.soradbh.truyenfull.model.ChapterModel;
import com.soradbh.truyenfull.viewmodel.ChapterViewModel;

public class EachChapterFragment extends Fragment {
    private ChapterViewModel viewModel;

    private TextView textViewNameStory, textViewNameChapter, textViewContentChapter;
    private ScrollView container;
    private ProgressBar progressBar;

    public EachChapterFragment(){
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.each_chapter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        container = view.findViewById(R.id.container_eachchapter);
        progressBar = view.findViewById(R.id.progress_bar);
        textViewNameStory = view.findViewById(R.id.textview_namestory_chapter);
        textViewNameChapter = view.findViewById(R.id.textview_name_chapter);
        textViewContentChapter = view.findViewById(R.id.textview_content_chapter);
        viewModel = new ViewModelProvider(requireActivity()).get(ChapterViewModel.class);
        viewModel.getSpinner().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {
                if(loading){
                    progressBar.setVisibility(View.VISIBLE);
                    container.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    container.setVisibility(View.VISIBLE);
                }
            }
        });
        viewModel.getChapter().observe(getViewLifecycleOwner(), new Observer<ChapterModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(ChapterModel data) {
                textViewNameStory.setText(data.getNameStory());
                textViewNameChapter.setText(data.getNameChapter());
                Spanned html = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) ? Html.fromHtml(data.getContentChapter(), Html.FROM_HTML_MODE_COMPACT) : Html.fromHtml(data.getContentChapter());
                textViewContentChapter.setText(html);
            }
        });
    }
}
