package com.example.vladislav.androidstudy.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vladislav.androidstudy.R;

public class ContextMenuActivity extends AppCompatActivity {

    private static final int MENU_COLOR_RED = 1;
    private static final int MENU_COLOR_GREEN = 2;
    private static final int MENU_COLOR_BLUE = 3;

    private static final int MENU_SIZE_22 = 4;
    private static final int MENU_SIZE_26 = 5;
    private static final int MENU_SIZE_30 = 6;

    TextView tvSizeTextView;
    TextView tvColorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context_menu);

        tvSizeTextView = findViewById(R.id.tvSize);
        tvColorTextView = findViewById(R.id.tvColor);

        // для tvColor и tvSize необходимо создавать контекстное меню
        registerForContextMenu(tvColorTextView);    // same as tvColor.setOnCreateContextMenuListener(this);
        registerForContextMenu(tvSizeTextView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (tvColorTextView.equals(v)) {
            menu.add(0, MENU_COLOR_RED, 0, "Red");
            menu.add(0, MENU_COLOR_GREEN, 0, "Green");
            menu.add(0, MENU_COLOR_BLUE, 0, "Blue");
        } else if (tvSizeTextView.equals(v)) {
            menu.add(0, MENU_SIZE_22, 0, "22");
            menu.add(0, MENU_SIZE_26, 0, "26");
            menu.add(0, MENU_SIZE_30, 0, "30");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // пункты меню для tvColor
            case MENU_COLOR_RED -> tvColorTextView.setTextColor(Color.RED);
            case MENU_COLOR_GREEN -> tvColorTextView.setTextColor(Color.GREEN);
            case MENU_COLOR_BLUE -> tvColorTextView.setTextColor(Color.BLUE);

            // пункты меню для tvSize
            case MENU_SIZE_22 -> tvSizeTextView.setTextSize(22);
            case MENU_SIZE_26 -> tvSizeTextView.setTextSize(26);
            case MENU_SIZE_30 -> tvSizeTextView.setTextSize(30);
        }
        return super.onContextItemSelected(item);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ContextMenuActivity.class);
    }
}
