package com.soradbh.truyenfull.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.soradbh.truyenfull.model.ListStoryModel;

public class StoryFactory extends DataSource.Factory<Integer, ListStoryModel> {
    private String url;
    private MutableLiveData<StoryDataSource> liveDataSource = new MutableLiveData<>();
    private StoryDataSource dataSource;

    public StoryFactory(String url){
        this.url = url;
    }

    @NonNull
    @Override
    public DataSource<Integer, ListStoryModel> create() {
        dataSource = new StoryDataSource(url);
        liveDataSource.postValue(dataSource);
        return dataSource;
    }
}
