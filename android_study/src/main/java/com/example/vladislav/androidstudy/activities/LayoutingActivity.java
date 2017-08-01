package com.example.vladislav.androidstudy.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.vladislav.androidstudy.R;

public class LayoutingActivity extends AppCompatActivity {

    private Button button;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Info about linear mLayout.
        // http://devpad.ru/post/item/2/android-vse-o-linearlayout-1.html

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layouting);
        button = (Button) findViewById(R.id.button);
        linearLayout = (LinearLayout) findViewById(R.id.activity_layouting);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Getting a mLayout attributes programmatically.
                LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                System.out.println("Linear mLayout gravity is: " + mLayoutParams.gravity);
                // This row does nothing.
                setContentView(linearLayout, mLayoutParams);

            }
        });

    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {

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
