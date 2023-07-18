package com.allNetworks.Tools;

public class GuideModel {
    String Title,Content,Imglink;

    public GuideModel(String title, String content, String imglink) {
        Title = title;
        Content = content;
        Imglink = imglink;
    }

    public GuideModel(String title, String content) {
        Title = title;
        Content = content;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getImglink() {
        return Imglink;
    }

    public void setImglink(String imglink) {
        Imglink = imglink;
    }

    @Override
    public String toString() {
        return "GuideModel{" +
                "Title='" + Title + '\'' +
                ", Content='" + Content + '\'' +
                ", Imglink='" + Imglink + '\'' +
                '}';
    }
}
