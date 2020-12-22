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

public class UpdateStoryViewModel extends ViewModel {

    private Executor executor;
    private LiveData<PagedList<ListStoryModel>> listStory;
    private MutableLiveData<Boolean> spinner = new MutableLiveData<>(false);

    public UpdateStoryViewModel(){
        init();
    }

    private void init(){
        setSpinner(true);
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

    public LiveData<Boolean> getSpinner(){
        return spinner;
    }

    public void setSpinner(Boolean loading){
        spinner.setValue(loading);
    }

}
