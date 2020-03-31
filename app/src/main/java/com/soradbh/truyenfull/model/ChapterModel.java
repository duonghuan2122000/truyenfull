package com.soradbh.truyenfull.model;

public class ChapterModel {
    private String nameStory;
    private String nameChapter;
    private String contentChapter;

    public ChapterModel(String nameStory, String nameChapter, String contentChapter) {
        this.nameStory = nameStory;
        this.nameChapter = nameChapter;
        this.contentChapter = contentChapter;
    }

    public String getNameStory() {
        return nameStory;
    }

    public void setNameStory(String nameStory) {
        this.nameStory = nameStory;
    }

    public String getNameChapter() {
        return nameChapter;
    }

    public void setNameChapter(String nameChapter) {
        this.nameChapter = nameChapter;
    }

    public String getContentChapter() {
        return contentChapter;
    }

    public void setContentChapter(String contentChapter) {
        this.contentChapter = contentChapter;
    }
}
