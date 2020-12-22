package com.soradbh.truyenfull.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.soradbh.truyenfull.model.ChapterModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ChapterViewModel extends ViewModel {
    private MutableLiveData<ChapterModel> chapter = new MutableLiveData<>();
    private MutableLiveData<Boolean> spinner = new MutableLiveData<>();
    private int mPosition = 0;
    private String mUrlStory = "";

    public LiveData<ChapterModel> getChapter(){
        return chapter;
    }

    public LiveData<Boolean> getSpinner(){
        return spinner;
    }

    public void setChapter(final String truyenId, final int position, final String urlStory){
        if(mPosition == position && mUrlStory.equals(urlStory)) return;
        spinner.setValue(true);
        mPosition = position;
        mUrlStory = urlStory;
        new Thread(new Runnable() {
            @Override
            public void run() {
                ChapterModel data = fetchChapter(truyenId, position, urlStory);
                chapter.postValue(data);
                spinner.postValue(false);
            }
        }).start();
    }

    private ChapterModel fetchChapter(String truyenId, int position, String urlStory){
        Document document = null;
        try {
            document = Jsoup.connect("https://truyenfull.vn/ajax.php?type=chapter_option&data="+truyenId).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String urlChapter = urlStory + document.select("select > option").eq(position).attr("value");
        try {
            document = Jsoup.connect(urlChapter).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String nameStory = document.selectFirst("#chapter-big-container > div > div > a").text();
        String nameChapter = document.selectFirst("#chapter-big-container > div > div > h2 > a").text();
        String contentChapter = document.selectFirst("#chapter-c").html();
        return new ChapterModel(nameStory, nameChapter, contentChapter);
    }
}
