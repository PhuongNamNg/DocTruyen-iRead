package com.example.doctruyen_iread.Module;

public class Chapter {
    private String chapterId;
    private String chapterTitle;
    private String chapterDescription;
    private String chapterContent;
    private Integer chapterIndex;
    private Integer chapterToken;

    public Chapter(){}

    public Chapter(String chapterId, String chapterTitle, String chapterDescription, String chapterContent, Integer chapterToken, Integer chapterIndex) {
        this.chapterId = chapterId;
        this.chapterTitle = chapterTitle;
        this.chapterDescription = chapterDescription;
        this.chapterContent = chapterContent;
        this.chapterToken = chapterToken;
        this.chapterIndex = chapterIndex;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterDescription() {
        return chapterDescription;
    }

    public void setChapterDescription(String chapterDescription) {
        this.chapterDescription = chapterDescription;
    }

    public String getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(String chapterContent) {
        this.chapterContent = chapterContent;
    }

    public Integer getChapterToken() {
        return chapterToken;
    }

    public void setChapterToken(Integer chapterToken) {
        this.chapterToken = chapterToken;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public Integer getChapterIndex() {
        return chapterIndex;
    }

    public void setChapterIndex(Integer chapterIndex) {
        this.chapterIndex = chapterIndex;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "chapterId='" + chapterId + '\'' +
                ", chapterTitle='" + chapterTitle + '\'' +
                ", chapterDescription='" + chapterDescription + '\'' +
                ", chapterContent='" + chapterContent + '\'' +
                ", chapterToken=" + chapterToken +
                '}';
    }
}
