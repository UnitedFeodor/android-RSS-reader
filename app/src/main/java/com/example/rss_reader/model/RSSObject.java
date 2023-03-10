package com.example.rss_reader.model;

import java.util.ArrayList;

class Enclosure{
    public String link;
}

public class RSSObject{
    public String status;
    public Feed feed;
    public ArrayList<Item> items;

    public RSSObject(String status, Feed feed, ArrayList<Item> items) {
        this.status = status;
        this.feed = feed;
        this.items = items;
    }

    public RSSObject(Feed feed, ArrayList<Item> items) {
        this.feed = feed;
        this.items = items;
    }

    public RSSObject(ArrayList<Item> items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}


