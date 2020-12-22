package com.soradbh.truyenfull.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.soradbh.truyenfull.model.ListStoryModel;
import com.soradbh.truyenfull.repository.StoryFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SearchStoryViewModel extends ViewModel {
    private String mUrl = "";
    private Executor executor;
    private LiveData<PagedList<ListStoryModel>> listStory = new MutableLiveData<>();
    private MutableLiveData<Boolean> spinner = new MutableLiveData<>(false);

    public LiveData<PagedList<ListStoryModel>> getListStory(String url){
        if(mUrl.equals(url) || url == "") return listStory;
        spinner.setValue(true);
        mUrl = url;
        executor = Executors.newFixedThreadPool(5);
        StoryFactory storyFactory = new StoryFactory(url);
        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setPrefetchDistance(1)
                .build();
        listStory = new LivePagedListBuilder<>(storyFactory, pagedListConfig)
                .setFetchExecutor(executor)
                .build();
        return listStory;
    }

    public LiveData<Boolean> getSpinner(){
        return spinner;
    }

    public void setSpinner(Boolean loading){
        spinner.setValue(loading);
    }

}
