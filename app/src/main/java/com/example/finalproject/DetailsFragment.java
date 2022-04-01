package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsFragment extends Fragment {

    //to hold data passed from parent/ShowSavedImageActivity?
    private Bundle dataFromSavedImageActivity;
    //variables to hold bundle data
    private String imageTitleText;
    private String imageDateText;
    private String imageURLText;
    private String imageHDurlText;
    private String imageDescriptionText;

    private AppCompatActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_details_fragment);
        //get data passed in bundle and save to variables
        dataFromSavedImageActivity = getArguments();
        imageTitleText = dataFromSavedImageActivity.getString("imageTitle");
        imageDateText = dataFromSavedImageActivity.getString("imageDate");
        imageDescriptionText = dataFromSavedImageActivity.getString("imageDescription");
        imageURLText = dataFromSavedImageActivity.getString("imageURL");
        imageHDurlText = dataFromSavedImageActivity.getString("imageHDurl");
        //inflate the layout for this fragment
        View result = inflater.inflate(R.layout.activity_details_fragment,container, false);

        TextView imageTitle = (TextView)result.findViewById(R.id.fragmentImageTitle);
        imageTitle.setText("Title: " +imageTitleText);

        TextView imageDate = (TextView)result.findViewById(R.id.fragmentImageDate);
        imageDate.setText("Date: " +imageDateText);

        TextView imageDescription = (TextView)result.findViewById(R.id.fragmentImageDescription);
        imageDescription.setText("Description: " +imageDescriptionText);

        TextView imageHDurl = (TextView)result.findViewById(R.id.fragmentImageHDurl);
        imageHDurl.setText("HD URL: " +imageHDurlText);

        //TextView imageUrl = (TextView)result.findViewById(R.id.fragmentImageUrl);
        //imageUrl.setText("URL: " +imageURLText);

        return result;

    }

    public void onAttach(Context context) {
        super.onAttach(context);
        //context to be empty activity for phone
        parentActivity = (AppCompatActivity)context;
    }
}