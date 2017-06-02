package com.example.vladislav.androidstudy.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

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

    private Planet planet;
    private TextView planetSizeTextView;
    private TextView planetWeightTextView;
    private TextView planetDistanceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelable);
        // Reading parcelable from an intent.
        planet = getIntent().getParcelableExtra("Planet");

        planetSizeTextView = (TextView) findViewById(R.id.planet_size_text_view);
        planetWeightTextView = (TextView) findViewById(R.id.planet_weight_text_view);
        planetDistanceTextView = (TextView) findViewById(R.id.planet_distance_text_view);

        planetSizeTextView.setText(Double.toString(planet.getSize()));
        planetWeightTextView.setText(Double.toString(planet.getWeight()));
        planetDistanceTextView.setText(Double.toString(planet.getDistance()));

    }

}
