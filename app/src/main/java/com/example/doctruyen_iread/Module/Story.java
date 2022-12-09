package com.example.doctruyen_iread.Module;

public class Story {
    private String storyId;
    private String storyTitle;
    private String authorsName;
    private String storyDatePost;
    private String storyDescription;
    private String storyCategory;
    private Integer storyViews;
    private boolean storyCheck;
    private Integer storyToken;

    public Story() {

    }

    public Story(String storyId, String storyTitle, String authorsName, String storyDatePost, String storyDescription, String storyCategory, Integer storyViews, boolean storyCheck, Integer storyToken) {
        this.storyId = storyId;
        this.storyTitle = storyTitle;
        this.authorsName = authorsName;
        this.storyDatePost = storyDatePost;
        this.storyDescription = storyDescription;
        this.storyCategory = storyCategory;
        this.storyViews = storyViews;
        this.storyCheck = storyCheck;
        this.storyToken = storyToken;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    public String getAuthorsName() {
        return authorsName;
    }

    public void setAuthorsName(String authorsName) {
        this.authorsName = authorsName;
    }

    public String getStoryDatePost() {
        return storyDatePost;
    }

    public void setStoryDatePost(String storyDatePost) {
        this.storyDatePost = storyDatePost;
    }

    public String getStoryDescription() {
        return storyDescription;
    }

    public void setStoryDescription(String storyDescription) {
        this.storyDescription = storyDescription;
    }

    public Integer getStoryViews() {
        return storyViews;
    }

    public void setStoryViews(Integer storyViews) {
        this.storyViews = storyViews;
    }

    public boolean isStoryCheck() {
        return storyCheck;
    }

    public void setStoryCheck(boolean storyCheck) {
        this.storyCheck = storyCheck;
    }

    public Integer getStoryToken() {
        return storyToken;
    }

    public void setStoryToken(Integer storyToken) {
        this.storyToken = storyToken;
    }

    public String getStoryCategory() {
        return storyCategory;
    }

    public void setStoryCategory(String storyCategory) {
        this.storyCategory = storyCategory;
    }
}
