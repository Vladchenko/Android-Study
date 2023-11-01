package com.example.vladislav.androidstudy.jobs.simple_jobs;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.ScrollingActivity;

/**
 * Activity demonstrating a way views are put in activity
 */
public class LayoutingActivity extends AppCompatActivity {

    private LinearLayout mLinearLayout;

    /**
     * Start this activity
     * @param context to start activity
     * @return  intent that starts this activity
     */
    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, LayoutingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Info about linear mLayout.
        // http://devpad.ru/post/item/2/android-vse-o-linearlayout-1.html

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layouting);
        Button button = findViewById(R.id.button);
        mLinearLayout = findViewById(R.id.layouting_activity);
        // Showing that we can do something with this view.
        mLinearLayout.setBackground(getDrawable(R.drawable.customborder));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Getting a layout attributes programmatically.
                LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                System.out.println("Linear mLayout gravity is: " + mLayoutParams.gravity);
                // This row does nothing.
                setContentView(mLinearLayout, mLayoutParams);

            }
        });

    }

    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {

        return super.onCreateView(name, context, attrs);
    }

    public static class GridLayoutActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.grid_layout_activity);
        }
    }
}
