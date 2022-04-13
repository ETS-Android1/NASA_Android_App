package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


/**
 * Details Fragment class extends Fragment from the static library
 *
 * @author Aladah M + Will B
 *  @version 1.00
 *
 * When  user clicks on a saved image from the list on ShowSavedImageActivity this details fragment class will populate
 * The details fragement will show information about the item that was clicked on
 *
 * @see  com.example.finalproject.ShowSavedImageActivity
 *
 */
public class DetailsFragment extends Fragment {
    //to hold data passed from parent/ShowSavedImageActivity?
    private Bundle dataFromSavedImageActivity;
    //variables to hold bundle data
    private String imageTitleText;
    private String imageDateText;
    private String imageURLText;
    private String imageHDurlText;
    private String imageDescriptionText;
    //button to view image
    private Button viewImageButton;

    private AppCompatActivity parentActivity;

    /**
     * This method is called to instantiate the user interface for the fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get data passed in bundle and save to variables
        dataFromSavedImageActivity = getArguments();
        //check if data successfully retrieved from bundle
        assert dataFromSavedImageActivity != null;
        //use string variables to hold data retrieved in bundle
        imageTitleText = dataFromSavedImageActivity.getString("imageTitle");
        imageDateText = dataFromSavedImageActivity.getString("imageDate");
        imageDescriptionText = dataFromSavedImageActivity.getString("imageDescription");
        imageURLText = dataFromSavedImageActivity.getString("imageURL");
        imageHDurlText = dataFromSavedImageActivity.getString("imageHDurl");
        //inflate activity_details_fragment.xml
        View result = inflater.inflate(R.layout.activity_details_fragment, container, false);
        //set textviews in fragment to variables with bundle data
        TextView imageTitle = (TextView) result.findViewById(R.id.fragmentImageTitle);
        imageTitle.setText(getResources().getString(R.string.imageTitleDetails) + imageTitleText);

        TextView imageDate = (TextView) result.findViewById(R.id.fragmentImageDate);
        imageDate.setText(getResources().getString(R.string.imageDateDetails) + imageDateText);

        TextView imageDescription = (TextView) result.findViewById(R.id.fragmentImageDescription);
        imageDescription.setText(getResources().getString(R.string.imageDescriptionDetails) + " " + imageDescriptionText);

        TextView imageHDurl = (TextView) result.findViewById(R.id.fragmentImageHDurl);
        imageHDurl.setText(getResources().getString(R.string.imageHdUrlDetails) + imageHDurlText);

        //attach view image button to fragment layout
        viewImageButton = result.findViewById(R.id.view_image_button);
       //if button is clicked
        viewImageButton.setOnClickListener(click -> {
            //save date associated with image, retrieved from bundle
            String dateSelected = imageDateText;
            //create intent object to send to ShowImageActivity
            Intent sendDateInformation = new Intent(parentActivity.getBaseContext(), ShowImageActivity.class);
            //put date in intent of image to show
            sendDateInformation.putExtra("Date", dateSelected);
            //if button is clicked use intent to start activity
            startActivity(sendDateInformation);
        });
        //return the fragment layout with everything added
        return result;

    }

    /**
     * This method attaches fragment to its context
     * For phone screen we are using EmptyActivity
     * @param context
     */
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //context to be empty activity for phone
        parentActivity = (AppCompatActivity) context;
    }
}