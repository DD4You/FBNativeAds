package in.dd4you.fbnativeads;

/**
 * Created by Vinay Singh (https://dd4you.in) on 08/01/2021
 * Copyright (c) 2018-2021. All rights reserved.
 * Project Name:- FBNativeAds
 * Package Name:- in.dd4you.fbnativeads
 */
public class MainModel {

    public String title;
    public String subtitle;

    public MainModel(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
