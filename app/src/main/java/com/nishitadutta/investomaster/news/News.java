package com.nishitadutta.investomaster.news;

/**
 * Created by Nishita on 08-01-2017.
 */
public class News {
    String title, desc, img_url, url;

    public News(String title, String desc, String url, String url_d) {
        this.title = title;
        this.desc = desc;
        this.url=url_d;
        this.img_url=url;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {

        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
