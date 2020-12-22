package com.soradbh.truyenfull.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.soradbh.truyenfull.model.CategoryModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryViewModel extends ViewModel {
    private MutableLiveData<List<CategoryModel>> listCategories = new MutableLiveData<>();
    private MutableLiveData<Boolean> spinner = new MutableLiveData<>();
    private Boolean isFetched = false;

    public LiveData<List<CategoryModel>> getListCategories(){
        return listCategories;
    }

    public LiveData<Boolean> getSpinner(){
        return spinner;
    }

    public void setListCategories(){
        if(isFetched) return;
        spinner.setValue(true);
        isFetched = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<CategoryModel> data = fetchListCategories();
                spinner.postValue(false);
                listCategories.postValue(data);
            }
        }).start();
    }

    private List<CategoryModel> fetchListCategories(){
        Document document = null;
        List<CategoryModel> data = new ArrayList<>();
        try {
            document = Jsoup.connect("https://truyenfull.vn/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements divs = document.select("#nav > div.container > div.navbar-collapse.collapse > ul > li.dropdown > div.multi-column > div.row > div");
        for(Element div: divs){
            for(Element li: div.select("ul.dropdown-menu > li")){
                String url = li.selectFirst("a").attr("href");
                String name = li.selectFirst("a").text();
                data.add(new CategoryModel(name, url));
            }
        }
        return data;
    }
}
