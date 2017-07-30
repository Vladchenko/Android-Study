package com.example.vladislav.androidstudy.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.logic.Utils;

import java.util.ArrayList;
import java.util.List;

public class AddButtonsActivity extends AppCompatActivity {

    List<Button> buttons = new ArrayList<>();
    GridLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_buttons);
        layout = (GridLayout) findViewById(R.id.dynamic_components_layout);
        final Activity activity = this;

        final EditText buttonname = (EditText) findViewById(R.id.button_name_edit_text);

        final Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isAddButton(buttonname.getText().toString())) {
                    Button newButton = new Button(activity);
                    newButton.setText(buttonname.getText());
                    buttons.add(newButton);
                    layout.addView(newButton);
                    Utils.showToast(activity, "Button named "
                            + buttonname.getText().toString()
                            + " was added");
                } else {
                    Utils.showToast(activity, "Such button exists");
                }
            }
        });
        final Button removeButton = (Button) findViewById(R.id.remove_button);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!removeButton(buttonname.getText().toString())) {
                    Utils.showToast(activity, "No such button");
                } else {
                    Utils.showToast(activity, "Button named "
                            + buttonname.getText().toString()
                            + " has been removed");
                }
            }
        });
    }

    public boolean isAddButton(String name) {
        boolean result = true;
        for (int i=0; i < buttons.size(); i++) {
            if (buttons.get(i).getText().toString().equals(name)) {
                result = false;
                break;
            }
        }
        return result;
    }

    public boolean removeButton(String name) {
        boolean result = false;
        for (int i=0; i < buttons.size(); i++) {
            if (buttons.get(i).getText().toString().equals(name)) {
                layout.removeView(buttons.get(i));
                buttons.remove(i);
                result = true;
            }
        }
        return result;
    }
}
