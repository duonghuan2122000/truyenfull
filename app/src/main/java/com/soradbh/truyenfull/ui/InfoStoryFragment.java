package com.soradbh.truyenfull.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.soradbh.truyenfull.R;
import com.soradbh.truyenfull.model.InfoStoryModel;
import com.soradbh.truyenfull.viewmodel.InfoStoryViewModel;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoStoryFragment extends Fragment {
    public static final String URL_STORY = "URL_STORY";

    private String truyenId;
    private String urlStory;

    private InfoStoryViewModel viewModel;

    private Handler mHandler = new Handler();

    private ImageView imageViewStory;
    private TextView textViewNameStory, textViewAuthorStory, textViewCatStory, textViewDescriptionStory;
    private Button buttonReadStory, buttonListChapters;
    private ScrollView container;
    private ProgressBar progressBar;


    public InfoStoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        urlStory = getArguments().getString(URL_STORY);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_story, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.infostory_title);

        // Ánh xạ
        mapping();

        setupViewModel();

        eventButton();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.setInfoStory(urlStory);
    }

    private void mapping() {
        container = getView().findViewById(R.id.container_infostory);
        progressBar = getView().findViewById(R.id.progress_bar);
        imageViewStory = getView().findViewById(R.id.imageview_infostory);
        textViewNameStory = getView().findViewById(R.id.textview_name_infostory);
        textViewAuthorStory = getView().findViewById(R.id.textview_author_infostory);
        textViewCatStory = getView().findViewById(R.id.textview_category_infostory);
        textViewDescriptionStory = getView().findViewById(R.id.textview_description_infostory);
        buttonReadStory = getView().findViewById(R.id.button_read_infostory);
        buttonListChapters = getView().findViewById(R.id.button_list_chapter_infostory);
    }

    private void setupViewModel(){
        viewModel = new ViewModelProvider(requireActivity()).get(InfoStoryViewModel.class);
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
        viewModel.getInfoStory().observe(getViewLifecycleOwner(), new Observer<InfoStoryModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(InfoStoryModel data) {
                Picasso.get()
                        .load(data.getImageStory())
                        .placeholder(R.drawable.loading)
                        .into(imageViewStory);

                textViewNameStory.setText(data.getNameStory());
                textViewAuthorStory.setText(data.getAuthorStory());
                textViewCatStory.setText(data.getCatStory());
                Spanned html = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) ? Html.fromHtml(data.getDescriptionStory(), Html.FROM_HTML_MODE_COMPACT) : Html.fromHtml(data.getDescriptionStory());
                textViewDescriptionStory.setText(html);
                truyenId = data.getTruyenId();
            }
        });
    }

    private void eventButton(){
        buttonReadStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickReadStory(v);
            }
        });
        buttonListChapters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString(ChapterFragment.TRUYEN_ID, truyenId);
                args.putString(ChapterFragment.URL_STORY, urlStory);
                args.putInt(ChapterFragment.POSITION_CHAPTER, -1);
                Navigation.findNavController(requireView()).navigate(R.id.action_infostory_dest_to_listchapter_dest, args);
            }
        });
    }

    private void onClickReadStory(final View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document document = null;
                try {
                    document = Jsoup.connect("https://truyenfull.vn/ajax.php?type=chapter_option&data=" + truyenId).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final int totalChapters = document.select("select > option").size();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Bundle args = new Bundle();
                        args.putInt(ChapterFragment.TOTAL_CHAPTERS, totalChapters);
                        args.putInt(ChapterFragment.POSITION_CHAPTER, 0);
                        args.putString(ChapterFragment.TRUYEN_ID, truyenId);
                        args.putString(ChapterFragment.URL_STORY, urlStory);
                        Navigation.findNavController(requireView()).navigate(R.id.action_infostory_dest_to_chapter_dest, args);
                    }
                });
            }
        }).start();
    }
}
