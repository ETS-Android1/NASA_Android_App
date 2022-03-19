package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.databinding.ActivityShowImageBinding;
import com.example.finalproject.databinding.ActivityShowSavedImageBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ShowSavedImageActivity extends DrawerBaseActivity {

    String imageTitle;
    String imageURL;
    String imageHDurl;
    String imageDesc;

    ArrayList<DBImage> imageList = new ArrayList<>();
    private static int ACTIVITY_VIEW_IMAGE = 33;
    MyOwnAdapter myAdapter;
    SQLiteDatabase db;

    ActivityShowSavedImageBinding activityShowSavedImageBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityShowSavedImageBinding = ActivityShowSavedImageBinding.inflate(getLayoutInflater());
        allocateActivityTitle("Saved Images");
        setContentView(activityShowSavedImageBinding.getRoot());
        //setContentView(R.layout.activity_show_saved_image);
        ListView theList = (ListView)findViewById(R.id.the_list);
        //load previous images
        loadDataFromDatabase();

        //create adapter object and set to this listview
        myAdapter = new MyOwnAdapter();
        theList.setAdapter(myAdapter);

        theList.setOnItemClickListener(( parent,  view,  position,  id) -> {
            showImage( position );
        });

        //Receive date from DatePicker in previous Activity
        Intent receivedImageTitle = getIntent();
        //datePassed = receivedDate.getStringExtra("Date");
        imageTitle = receivedImageTitle.getStringExtra("Title");
        imageURL = receivedImageTitle.getStringExtra("url");
        imageHDurl = receivedImageTitle.getStringExtra("hdurl");
        imageDesc = receivedImageTitle.getStringExtra("desc");

        //add to database
        ContentValues newRowValues = new ContentValues();

        newRowValues.put(DBOpener.COL_IMAGE_TITLE, imageTitle);
        newRowValues.put(DBOpener.COL_IMAGE_URL, imageURL);
        newRowValues.put(DBOpener.COL_IMAGE_HDURL, imageHDurl );
        newRowValues.put(DBOpener.COL_IMAGE_DESC, imageDesc);

        //insert into DB
        long newId = db.insert(DBOpener.TABLE_NAME,null,newRowValues);
        //create new Image Object
        DBImage newImage = new DBImage(imageTitle, imageURL, newId);

        imageList.add(newImage);

        myAdapter.notifyDataSetChanged();

        Toast.makeText(this, "New Image saved ID: "+newId, Toast.LENGTH_LONG).show();

    }

    private void showImage(int position) {
        DBImage selectedImage = imageList.get(position);
        View image_view = getLayoutInflater().inflate(R.layout.image_delete, null);

        TextView imageToDelete = image_view.findViewById(R.id.imageToDelete);
        TextView rowId = image_view.findViewById(R.id.imageIdToDelete);

        imageToDelete.setText(selectedImage.getTitle());
        rowId.setText("Title: ");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete image: " +position +"?")
                .setMessage("This cannot be undone")
                .setView(image_view)
                .setNegativeButton("Confirm Delete", (click, b) -> {
                    deleteImage(selectedImage);
                    imageList.remove(position);
                    myAdapter.notifyDataSetChanged();
                })
                .setPositiveButton("Cancel", (click, b) -> { })
                .create().show();

    }

    private void deleteImage(DBImage selectedImage) {
        db.delete(DBOpener.TABLE_NAME, DBOpener.COL_ID + " = ?", new String[] {Long.toString(selectedImage.getID())});
    }

    private void loadDataFromDatabase() {
        //get db connection
        DBOpener dbOpener = new DBOpener(this);
        db = dbOpener.getWritableDatabase();

        String [] columns = {DBOpener.COL_ID, DBOpener.COL_IMAGE_TITLE, DBOpener.COL_IMAGE_URL, DBOpener.COL_IMAGE_HDURL, DBOpener.COL_IMAGE_DESC};

        //query all results in a cursor
        Cursor results = db.query(false, DBOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        int idColIndex = results.getColumnIndex(DBOpener.COL_ID);
        int titleColumnIndex = results.getColumnIndex(DBOpener.COL_IMAGE_TITLE);
        int urlColumnIndex = results.getColumnIndex(DBOpener.COL_IMAGE_URL);
        int HDurlColumnIndex = results.getColumnIndex(DBOpener.COL_IMAGE_HDURL);
        int descColumnIndex = results.getColumnIndex(DBOpener.COL_IMAGE_DESC);

        while(results.moveToNext())
        {
            String title = results.getString(titleColumnIndex);
            String url = results.getString(urlColumnIndex);
            long id = results.getLong(idColIndex);

            imageList.add(new DBImage(title, url, id));
        }
    }
    protected class MyOwnAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            //return count of images saved in database
            return imageList.size();
        }

        @Override
        public DBImage getItem(int i) {
            //return image at clicked index
            return imageList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return getItem(i).getID();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View newView = getLayoutInflater().inflate(R.layout.image_row, viewGroup, false);
            DBImage thisRow = getItem(i);

            //get textviews from rows
            TextView imageTitle = (TextView)newView.findViewById(R.id.rowTitle);
            TextView imageURL = (TextView) newView.findViewById(R.id.rowURL);
            TextView rowId = (TextView)newView.findViewById(R.id.row_id);

            imageTitle.setText( thisRow.getTitle());
            imageURL.setText( thisRow.getUrl());
            rowId.setText("ID: " + thisRow.getID());

            //return the row
            return newView;
        }
    }
}