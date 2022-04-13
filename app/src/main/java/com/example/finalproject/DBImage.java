package com.example.finalproject;

/**
 * @author Aladah M + Will B
 * @version 1.00
 * DBImage class is used to hold information about each saved image
 */
public class DBImage {
    //class to hold image info in database and in list view
    protected String title, date, url, HDurl, description;
    protected long ID;

    //constructor
    public DBImage(String t, String u, long i, String d, String desc, String hd) {
        title = t;
        url = u;
        ID = i;
        date = d;
        description = desc;
        HDurl = hd;
    }

    //constructor
    public DBImage(String t, String u, String hd, long i, String d, String desc) {
        title = t;
        url = u;
        HDurl = hd;
        ID = i;
        date = d;
        description = desc;
    }
    //chaining
    public DBImage(String t, String u, String hd, String d, String desc) { this(t,u,  hd, 0, d, desc);}
    public String getTitle() {return title;}
    public String getUrl() {return url;}
    public String getHDurl() {return HDurl;}
    public long getID() {return ID;}
    public String getDate() {return date;}
    public String getDescription() {return description;}
}