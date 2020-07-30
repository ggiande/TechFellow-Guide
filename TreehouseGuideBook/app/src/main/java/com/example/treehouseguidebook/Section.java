package com.example.treehouseguidebook;

public class Section {
    private String desc;
    private String link;

    public Section() {
    }

    public Section(String desc, String link) {
        this.desc = desc;
        this.link = link;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
