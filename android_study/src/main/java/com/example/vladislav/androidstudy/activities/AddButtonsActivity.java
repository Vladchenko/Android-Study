package com.example.vladislav.androidstudy.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.logic.Utils;

import java.util.ArrayList;
import java.util.List;

public class AddButtonsActivity extends AppCompatActivity {

    private List<Button> mButtons = new ArrayList<>();
    private GridLayout mLayout;
    private EditText mButtonName;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_buttons);

        mLayout = (GridLayout) findViewById(R.id.dynamic_components_layout);
        mButtonName = (EditText) findViewById(R.id.button_name_edit_text);
        mActivity = this;

        final Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(getAddButtonListener());
        final Button removeButton = (Button) findViewById(R.id.remove_button);
        removeButton.setOnClickListener(getRemoveButtonListener());
    }

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

    private View.OnClickListener getAddButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAddButton(mButtonName.getText().toString())) {
                    Button newButton = new Button(mActivity);
                    newButton.setText(mButtonName.getText());
                    mButtons.add(newButton);
                    mLayout.addView(newButton);
                    Utils.showToast(mActivity, "Button named "
                            + mButtonName.getText().toString()
                            + " was added");
                } else {
                    Utils.showToast(mActivity, "Such button exists");
                }
            }
        };
    }

    private View.OnClickListener getRemoveButtonListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!removeButton(mButtonName.getText().toString())) {
                    Utils.showToast(mActivity, "No such button");
                } else {
                    Utils.showToast(mActivity, "Button named "
                            + mButtonName.getText().toString()
                            + " has been removed");
                }
            }
        };
    }
}
