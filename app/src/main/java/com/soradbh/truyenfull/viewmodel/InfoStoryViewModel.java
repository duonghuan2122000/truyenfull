package com.soradbh.truyenfull.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.soradbh.truyenfull.model.InfoStoryModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class InfoStoryViewModel extends ViewModel {
    private MutableLiveData<InfoStoryModel> _infoStory = new MutableLiveData<>();
    private String mUrlStory = "";

    public LiveData<InfoStoryModel> getInfoStory(){
        return _infoStory;
    }

    public void fetchInfoStory(final String urlStory){
        if(mUrlStory.equals(urlStory)) return;
        mUrlStory = urlStory;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document document = null;
                try {
                    document = Jsoup.connect(urlStory).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String imageStory = document.selectFirst("#truyen > div.col-xs-12.col-sm-12.col-md-9.col-truyen-main > div.col-xs-12.col-info-desc > div.col-xs-12.col-sm-4.col-md-4.info-holder > div.books > div > img").attr("src");
                String nameStory = document.selectFirst("#truyen > div.col-xs-12.col-sm-12.col-md-9.col-truyen-main > div.col-xs-12.col-info-desc > h3").text();
                String descriptionStory = document.selectFirst("#truyen > div.col-xs-12.col-sm-12.col-md-9.col-truyen-main > div.col-xs-12.col-info-desc > div.col-xs-12.col-sm-8.col-md-8.desc > div.desc-text").html();
                String urlFirstChapter = document.selectFirst("#list-chapter > div.row > div:nth-child(1) > ul > li:nth-child(1) > a").attr("href");
                String authorStory = document.selectFirst("#truyen > div.col-xs-12.col-sm-12.col-md-9.col-truyen-main > div.col-xs-12.col-info-desc > div.col-xs-12.col-sm-4.col-md-4.info-holder > div.info > div:nth-child(1) > a").text();
                Elements cats = document.select("#truyen > div.col-xs-12.col-sm-12.col-md-9.col-truyen-main > div.col-xs-12.col-info-desc > div.col-xs-12.col-sm-4.col-md-4.info-holder > div.info > div:nth-child(2) > a");
                String catStory = "";
                for(Element cat: cats){
                    catStory += ", " + cat.text();
                }
                catStory = catStory.replaceFirst(",", "");
                String truyenId = document.selectFirst("input#truyen-id").attr("value");
                _infoStory.postValue(new InfoStoryModel(imageStory, nameStory, authorStory, catStory, descriptionStory, urlFirstChapter, truyenId));
            }
        }).start();

    }
}
