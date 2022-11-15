package com.example.doctruyen_iread.Module;

public class Story {
    private Integer storyId;
    private String storyTitle;
    private String storyContent;
    private String authorsName;
    private String storyDatePost;
    private Integer storyView;

    public Story() {
    }

    public Story(Integer storyId, String storyTitle, String storyContent,
                 String authorsName, String storyDatePost, Integer storyView) {
        this.storyId = storyId;
        this.storyTitle = storyTitle;
        this.storyContent = storyContent;
        this.authorsName = authorsName;
        this.storyDatePost = storyDatePost;
        this.storyView = storyView;
    }

    public Integer getStoryId() {
        return storyId;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    public String getStoryContent() {
        return storyContent;
    }

    public void setStoryContent(String storyContent) {
        this.storyContent = storyContent;
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

    public Integer getStoryView() {
        return storyView;
    }

    public void setStoryView(Integer storyView) {
        this.storyView = storyView;
    }
}
