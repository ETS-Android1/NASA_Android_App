package com.example.finalproject;

import androidx.appcompat.app.ActionBar;
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
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ShowSavedImageActivity extends DrawerBaseActivity {

    String imageTitle;
    String imageURL;
    String imageHDurl;
    String imageDesc;
    String imageDate;

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
        //set title of nav drawer to activity name
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headView = navigationView.getHeaderView(0);
        ((TextView) headView.findViewById(R.id.activityTitle)).setText("Saved Images");

        //setContentView(R.layout.activity_show_saved_image);
        ListView theList = (ListView)findViewById(R.id.the_list);
        //load previous images
        loadDataFromDatabase();

        //create adapter object and set to this listview
        myAdapter = new MyOwnAdapter();
        theList.setAdapter(myAdapter);

        //if a list item is clicked
        theList.setOnItemClickListener(( parent,  view,  position,  id) -> {
            //show text box with option to delete
            //showImage( position );

            //for bundle to fragment
            Bundle dataToPass = new Bundle();

            String imageTitle = imageList.get( position).getTitle();
            String imageDate = imageList.get( position ).getDate();
            String imageDescription = imageList.get( position ).getDescription();
            String imageHDurl = imageList.get( position ).getHDurl();
            String imageUrl = imageList.get( position ).getUrl();

            dataToPass.putString("imageTitle", imageTitle);
            dataToPass.putString("imageDate", imageDate);
            dataToPass.putString("imageDescription", imageDescription);
            dataToPass.putString("imageHDurl", imageHDurl);
            dataToPass.putString("imageUrl", imageUrl);

            //activity to open fragment
            Intent nextActivity = new Intent(ShowSavedImageActivity.this, EmptyActivity.class);
            //put selected image information in that opened fragment
            nextActivity.putExtras(dataToPass);
            //start activity
            startActivity(nextActivity);


        });

        //Receive data from show image activity of image user wants to save
        Intent receivedImageTitle = getIntent();
        imageTitle = receivedImageTitle.getStringExtra("Title");
        imageURL = receivedImageTitle.getStringExtra("url");
        imageHDurl = receivedImageTitle.getStringExtra("HDurl");
        imageDesc = receivedImageTitle.getStringExtra("explanation");
        imageDate = receivedImageTitle.getStringExtra("date");


        //add to database
        ContentValues newRowValues = new ContentValues();
        //add logic to only create new DB entry if received parameters from previous activity
        if (imageTitle != null) {
            //get image information from last activity and create row values
            newRowValues.put(DBOpener.COL_IMAGE_TITLE, imageTitle);
            newRowValues.put(DBOpener.COL_IMAGE_URL, imageURL);
            newRowValues.put(DBOpener.COL_IMAGE_HDURL, imageHDurl);
            newRowValues.put(DBOpener.COL_IMAGE_DESC, imageDesc);
            newRowValues.put(DBOpener.COL_IMAGE_DATE, imageDate);
            //insert row values into DB and create ID number
            long newId = db.insert(DBOpener.TABLE_NAME, null, newRowValues);
            //create new Image Object
            DBImage newImage = new DBImage(imageTitle, imageURL, imageHDurl, newId, imageDate, imageDesc);
            //add image object to list
            imageList.add(newImage);

            Toast.makeText(this, "New Image saved ID: "+newId, Toast.LENGTH_LONG).show();
        }
        myAdapter.notifyDataSetChanged();



    }
    //function to show text box pop up with option to delete image from DB
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

        String [] columns = {DBOpener.COL_ID, DBOpener.COL_IMAGE_TITLE, DBOpener.COL_IMAGE_DATE, DBOpener.COL_IMAGE_URL, DBOpener.COL_IMAGE_HDURL, DBOpener.COL_IMAGE_DESC};

        //query all results in a cursor
        Cursor results = db.query(false, DBOpener.TABLE_NAME, columns, null, null, null, null, null, null, null);

        int idColIndex = results.getColumnIndex(DBOpener.COL_ID);
        int titleColumnIndex = results.getColumnIndex(DBOpener.COL_IMAGE_TITLE);
        int urlColumnIndex = results.getColumnIndex(DBOpener.COL_IMAGE_URL);
        int HDurlColumnIndex = results.getColumnIndex(DBOpener.COL_IMAGE_HDURL);
        int descColumnIndex = results.getColumnIndex(DBOpener.COL_IMAGE_DESC);
        int dateColumnIndex = results.getColumnIndex(DBOpener.COL_IMAGE_DATE);

        while(results.moveToNext())
        {
            String title = results.getString(titleColumnIndex);
            String url = results.getString(urlColumnIndex);
            String HDurl = results.getString(HDurlColumnIndex);
            long id = results.getLong(idColIndex);
            String date = results.getString(dateColumnIndex);
            String desc = results.getString(descColumnIndex);

            imageList.add(new DBImage(title, url, HDurl, id, date, desc));
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
            TextView imageDate = (TextView) newView.findViewById(R.id.rowDate);
            //TextView rowId = (TextView)newView.findViewById(R.id.row_id);

            imageTitle.setText( thisRow.getTitle());
            imageDate.setText( thisRow.getDate());
            //rowId.setText("ID: " + thisRow.getID());

            //return the row
            return newView;
        }
    }
}