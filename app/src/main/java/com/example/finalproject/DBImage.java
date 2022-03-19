package com.example.finalproject;

public class DBImage {
    //class to hold image info in database and in list view
    protected String title, url, HDurl, description;
    protected long ID;

    //constructor
    public DBImage(String t, String u, String hd, long i) {
        title = t;
        url = u;
        HDurl = hd;
        ID = i;
    }
    //chaining
    public DBImage(String t, String u, String hd) { this(t,u, hd, 0);}

    public String getTitle() {return title;}
    public String getUrl() {return url;}
    public String getHDurl() {return HDurl;}
    public long getID() {return ID;}
}