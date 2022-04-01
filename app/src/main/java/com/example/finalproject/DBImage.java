package com.example.finalproject;

public class DBImage {
    //class to hold image info in database and in list view
    protected String title, date, url, HDurl, description;
    protected long ID;

    //constructor
    public DBImage(String t, String u, long i, String d) {
        title = t;
        url = u;
        ID = i;
        date = d;
    }

    //constructor
    public DBImage(String t, String u, String hd, long i, String d) {
        title = t;
        url = u;
        HDurl = hd;
        ID = i;
        date = d;
    }
    //chaining
    public DBImage(String t, String u, String hd, String d) { this(t,u, hd, 0, d);}

    public String getTitle() {return title;}
    public String getUrl() {return url;}
    public String getHDurl() {return HDurl;}
    public long getID() {return ID;}
    public String getDate() {return date;}
}