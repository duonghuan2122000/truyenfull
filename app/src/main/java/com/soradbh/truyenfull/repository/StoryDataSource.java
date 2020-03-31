package com.soradbh.truyenfull.repository;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.soradbh.truyenfull.model.ListStoryModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StoryDataSource extends PageKeyedDataSource<Integer, ListStoryModel> {
    private String url;
    public StoryDataSource(String url){
        this.url = url;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, ListStoryModel> callback) {
        List<ListStoryModel> data = fetchStory(url,1);
        callback.onResult(data, null, 2);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ListStoryModel> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ListStoryModel> callback) {
        List<ListStoryModel> data = fetchStory(url, params.key);
        callback.onResult(data, params.key + 1);
    }

    private List<ListStoryModel> fetchStory(String url, int page){
        List<ListStoryModel> data = new ArrayList<>();
        Document document = null;
        String link;
        if(url.contains("https://truyenfull.vn/tim-kiem/?tukhoa")){
            link = url + "&page=" + page;
        } else {
            link = url + "trang-" + page;
        }
        try {
            document = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements divs = document.select("#list-page > div.col-xs-12.col-sm-12.col-md-9.col-truyen-main > div.list.list-truyen.col-xs-12 > div.row");
        for(Element div: divs){
            String imageStory = div.child(0).child(0).child(0).attr("data-image");
            String nameStory = div.child(1).select("h3.truyen-title a").text();
            String urlStory = div.child(1).select("h3.truyen-title a").attr("href");
            data.add(new ListStoryModel(urlStory, nameStory, imageStory));
        }
        return data;
    }
}
