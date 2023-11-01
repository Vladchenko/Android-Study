package com.example.vladislav.androidstudy.activities.dynamic_layout.buttons_inserting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vladislav.androidstudy.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.vladislav.androidstudy.Utils.showToast;

// This activity is able to dynamically add or remove a buttons

// 2018-02-11
// - Activity rotation implemented
// Something of a notice (javadoc) in onRestoreInstanceState()
// - Back button implemented
// - Gridlayout border implemented

public class AddButtonsActivity extends AppCompatActivity {

    private static final String BUTTONS_PARCELLABLE_KEY = "BUTTONS_PARCELLABLE_KEY";

    private final List<Button> mButtons = new ArrayList<>();
    private final List<String> mButtonNames = new ArrayList<>();
    private GridLayout mLayout;
    private EditText mButtonName;
    private Activity mActivity;

    /**
     * Получить Intent для запуска активити
     *
     * @param context {@link android.content.Context}
     * @return инстанс Intent для запуска AddButtonsActivity
     */
    /**
     * Start this activity
     * @param context to start activity
     * @return  intent that starts this activity
     */
    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, AddButtonsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_buttons);

        mLayout = findViewById(R.id.dynamic_components_layout);
        mButtonName =  findViewById(R.id.button_name_edit_text);
        mActivity = this;

        final Button addButton =  findViewById(R.id.add_button);
        addButton.setOnClickListener(getAddButtonListener());
        final Button removeButton =  findViewById(R.id.remove_button);
        removeButton.setOnClickListener(getRemoveButtonListener());

        // Implementing a back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // MAKE UP A JAVADOC !
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUTTONS_PARCELLABLE_KEY, new ButtonNames(mButtonNames));
        outState.putBundle(BUTTONS_PARCELLABLE_KEY, bundle);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Bundle bundle = savedInstanceState.getParcelable(BUTTONS_PARCELLABLE_KEY);
        ButtonNames buttons = bundle.getParcelable(BUTTONS_PARCELLABLE_KEY);
        Button button;
        for (String string : buttons.getButtonNames()) {
            button = new Button(this);
            button.setText(string);
            mButtonNames.add(string);
            mButtons.add(button);
            mLayout.addView(button);
            // Strangely, operating with a Button resulted in exception saying the view (button)
            // already has a parent and google said that one needs to remove the view from a parent
            // to resolve that. But it didn't help, I had to switch to sending a strings.
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // Add new button, in case such button doesn't exist
    private boolean isAddButton(String name) {
        boolean result = true;
        for (int i = 0; i < mButtons.size(); i++) {
            if (mButtons.get(i).getText().toString().equals(name)) {
                result = false;
                break;
            }
        }
        return result;
    }

    // Remove button, if it exists
    private boolean removeButton(String name) {
        boolean result = false;
        for (int i = 0; i < mButtons.size(); i++) {
            if (mButtons.get(i).getText().toString().equals(name)) {
                mLayout.removeView(mButtons.get(i));
                mButtons.remove(i);
                result = true;
            }
        }
        return result;
    }

    // Button click listener that adds a new button, in case it doesn't exist
    private View.OnClickListener getAddButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = 0;
                if (isAddButton(mButtonName.getText().toString())) {
                    Button newButton = new Button(mActivity);
                    newButton.setText(mButtonName.getText());
                    // Why is it that adding an id, doesn't make an android to recover this view in
                    // layout ?
                    newButton.setId(index++);
                    mButtons.add(newButton);
                    mButtonNames.add(mButtonName.getText().toString());
                    mLayout.addView(newButton);
                    showToast(mActivity, "Button named "
                            + mButtonName.getText().toString()
                            + " was added");
                } else {
                    showToast(mActivity, "Such button exists");
                }
            }
        };
    }

    // Button click listener that removes a button, in case it exists
    private View.OnClickListener getRemoveButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (removeButton(mButtonName.getText().toString())) {
                    showToast(mActivity, "Button named "
                            + mButtonName.getText().toString()
                            + " has been removed");
                } else {
                    showToast(mActivity, "No such button");
                }
            }
        };
    }
}
