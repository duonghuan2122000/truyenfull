package com.soradbh.truyenfull.model;

public class ListStoryModel {
    private String urlStory;
    private String nameStory;
    private String imageStory;

    public ListStoryModel(String urlStory, String nameStory, String imageStory) {
        this.urlStory = urlStory;
        this.nameStory = nameStory;
        this.imageStory = imageStory;
    }

    public String getUrlStory() {
        return urlStory;
    }

    public void setUrlStory(String urlStory) {
        this.urlStory = urlStory;
    }

    public String getNameStory() {
        return nameStory;
    }

    public void setNameStory(String nameStory) {
        this.nameStory = nameStory;
    }

    public String getImageStory() {
        return imageStory;
    }

    public void setImageStory(String imageStory) {
        this.imageStory = imageStory;
    }
}
