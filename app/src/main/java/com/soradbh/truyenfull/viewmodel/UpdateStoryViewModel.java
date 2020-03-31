package com.soradbh.truyenfull.viewmodel;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.soradbh.truyenfull.model.ListStoryModel;
import com.soradbh.truyenfull.repository.StoryFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UpdateStoryViewModel extends ViewModel {

    private Executor executor;
    private LiveData<PagedList<ListStoryModel>> listStory;

    public UpdateStoryViewModel(){
        init();
    }

    private void init(){
        executor = Executors.newFixedThreadPool(5);
        StoryFactory storyFactory = new StoryFactory("https://truyenfull.vn/danh-sach/truyen-moi/");
        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setPrefetchDistance(1)
                .build();
        listStory = new LivePagedListBuilder<>(storyFactory, pagedListConfig)
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<PagedList<ListStoryModel>> getListStory(){
        return listStory;
    }

}
