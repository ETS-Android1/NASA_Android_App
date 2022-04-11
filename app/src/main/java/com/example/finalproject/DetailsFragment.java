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

public class DetailsFragment extends Fragment {

    //to hold data passed from parent/ShowSavedImageActivity?
    private Bundle dataFromSavedImageActivity;
    //variables to hold bundle data
    private String imageTitleText;
    private String imageDateText;
    private String imageURLText;
    private String imageHDurlText;
    private String imageDescriptionText;
    private Button viewImageButton;

    private AppCompatActivity parentActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_details_fragment);
        //get data passed in bundle and save to variables
        dataFromSavedImageActivity = getArguments();
        assert dataFromSavedImageActivity != null;
        imageTitleText = dataFromSavedImageActivity.getString("imageTitle");
        imageDateText = dataFromSavedImageActivity.getString("imageDate");
        imageDescriptionText = dataFromSavedImageActivity.getString("imageDescription");
        imageURLText = dataFromSavedImageActivity.getString("imageURL");
        imageHDurlText = dataFromSavedImageActivity.getString("imageHDurl");
        //inflate the layout for this fragment
        View result = inflater.inflate(R.layout.activity_details_fragment, container, false);

        TextView imageTitle = (TextView) result.findViewById(R.id.fragmentImageTitle);
        imageTitle.setText(getResources().getString(R.string.imageTitleDetails) + imageTitleText);

        TextView imageDate = (TextView) result.findViewById(R.id.fragmentImageDate);
        imageDate.setText(getResources().getString(R.string.imageDateDetails) + imageDateText);

        TextView imageDescription = (TextView) result.findViewById(R.id.fragmentImageDescription);
        imageDescription.setText(getResources().getString(R.string.imageDescriptionDetails) + " " + imageDescriptionText);

        TextView imageHDurl = (TextView) result.findViewById(R.id.fragmentImageHDurl);
        imageHDurl.setText(getResources().getString(R.string.imageHdUrlDetails) + imageHDurlText);

        //TextView imageUrl = (TextView)result.findViewById(R.id.fragmentImageUrl);
        //imageUrl.setText("URL: " +imageURLText);

        viewImageButton = result.findViewById(R.id.view_image_button);
        viewImageButton.setOnClickListener(click -> {
            String dateSelected = imageDateText;

            Intent sendDateInformation = new Intent(parentActivity.getBaseContext(), ShowImageActivity.class);
            sendDateInformation.putExtra("Date", dateSelected);
            startActivity(sendDateInformation);
        });

        return result;

    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //context to be empty activity for phone
        parentActivity = (AppCompatActivity) context;
    }
}