package com.example.vladislav.androidstudy.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.beans.Planet;

/**
 * To send data from one activity to another, one may use Parcelable.
 * 1. In one activity:
 *      intent = new Intent(this, ParcelableActivity.class);
 *      intent.putExtra("Planet", new Planet(1,1,1));
 *       startActivity(intent);
 * 2. In ParcelableActivity:
 *      planet = getIntent().getParcelableExtra("Planet");
 */
public class ParcelableActivity extends AppCompatActivity {

    private static final String PARCELABLE_PLANET =
            "com.example.vladislav.androidstudy.activities.ParcelableActivity.PARCELABLE_PLANET";

    private Planet planet;
    private TextView planetSizeTextView;
    private TextView planetWeightTextView;
    private TextView planetDistanceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelable);
        // Reading parcelable from an intent.
        planet = getIntent().getParcelableExtra(PARCELABLE_PLANET);

        planetSizeTextView =  findViewById(R.id.planet_size_text_view);
        planetWeightTextView =  findViewById(R.id.planet_weight_text_view);
        planetDistanceTextView =  findViewById(R.id.planet_distance_text_view);

        planetSizeTextView.setText(Double.toString(planet.getSize()));
        planetWeightTextView.setText(Double.toString(planet.getWeight()));
        planetDistanceTextView.setText(Double.toString(planet.getDistance()));

    }

    /**
     * Start this activity
     * @param context to start activity
     * @return  intent that starts this activity
     */
    public static Intent newIntent(@NonNull Context context, Parcelable planet) {
        Intent intent = new Intent(context, ParcelableActivity.class);
        intent.putExtra(PARCELABLE_PLANET, planet);
        return intent;
    }
}
