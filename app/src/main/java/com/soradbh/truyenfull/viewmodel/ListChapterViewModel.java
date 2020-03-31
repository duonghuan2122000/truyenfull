package com.soradbh.truyenfull.viewmodel;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.soradbh.truyenfull.R;
import com.soradbh.truyenfull.ui.ChapterFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListChapterViewModel extends ViewModel {
    private MutableLiveData<List<String>> listChapters = new MutableLiveData<>();
    private String mTruyenId = "";

    public LiveData<List<String>> getListChapters(){
        return listChapters;
    }

    public void fetchListChapters(final String truyenId){
        if(mTruyenId.equals(truyenId)) return;
        mTruyenId = truyenId;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document document = null;
                List<String> data = new ArrayList<>();
                try {
                    document = Jsoup.connect("https://truyenfull.vn/ajax.php?type=chapter_option&data="+truyenId).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements options = document.select("select > option");
                for(Element option: options){
                    data.add(option.text());
                }
                listChapters.postValue(data);
            }
        }).start();
    }
}
