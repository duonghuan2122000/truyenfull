package com.soradbh.truyenfull.model;

public class InfoStoryModel {
    private String imageStory;
    private String nameStory;
    private String authorStory;
    private String catStory;
    private String descriptionStory;
    private String urlFirstChapter;
    private String truyenId;

    public InfoStoryModel(String imageStory, String nameStory, String authorStory, String catStory, String descriptionStory, String urlFirstChapter, String truyenId) {
        this.imageStory = imageStory;
        this.nameStory = nameStory;
        this.authorStory = authorStory;
        this.catStory = catStory;
        this.descriptionStory = descriptionStory;
        this.urlFirstChapter = urlFirstChapter;
        this.truyenId = truyenId;
    }

    public String getTruyenId() {
        return truyenId;
    }

    public void setTruyenId(String truyenId) {
        this.truyenId = truyenId;
    }

    public String getImageStory() {
        return imageStory;
    }

    public void setImageStory(String imageStory) {
        this.imageStory = imageStory;
    }

    public String getNameStory() {
        return nameStory;
    }

    public void setNameStory(String nameStory) {
        this.nameStory = nameStory;
    }

    public String getAuthorStory() {
        return authorStory;
    }

    public void setAuthorStory(String authorStory) {
        this.authorStory = authorStory;
    }

    public String getCatStory() {
        return catStory;
    }

    public void setCatStory(String catStory) {
        this.catStory = catStory;
    }

    public String getDescriptionStory() {
        return descriptionStory;
    }

    public void setDescriptionStory(String descriptionStory) {
        this.descriptionStory = descriptionStory;
    }

    public String getUrlFirstChapter() {
        return urlFirstChapter;
    }

    public void setUrlFirstChapter(String urlFirstChapter) {
        this.urlFirstChapter = urlFirstChapter;
    }
}
