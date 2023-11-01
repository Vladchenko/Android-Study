package com.example.vladislav.androidstudy.activities.dynamic_layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import androidx.annotation.NonNull;

import com.example.vladislav.androidstudy.R;

/**
 * Task taken from a link followed
 * http://startandroid.ru/ru/uroki/vse-uroki-spiskom/51-urok-18-menjaem-layoutparams-v-rabochem-prilozhenii.html
 */
public class DynamicButtonsSizeActivity extends Activity {

    private LinearLayout.LayoutParams lParams1;
    private LinearLayout.LayoutParams lParams2;

    /**
     * Start this activity
     * @param context to start activity
     * @return  intent that starts this activity
     */
    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, DynamicButtonsSizeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_buttons_size);

        SeekBar seekBar = (SeekBar)findViewById(R.id.buttons_size_seekbar);
        seekBar.setMax(100);
        final Button leftButton = findViewById(R.id.left_button);
        final Button rightButton = findViewById(R.id.right_button);
        lParams1 = (LinearLayout.LayoutParams) leftButton.getLayoutParams();
        lParams2 = (LinearLayout.LayoutParams) rightButton.getLayoutParams();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int leftValue = progress;
                int rightValue = seekBar.getMax() - progress;
                // настраиваем вес
                lParams1.weight = leftValue;
                lParams2.weight = rightValue;
                // в текст кнопок пишем значения переменных
                leftButton.setText(String.valueOf(leftValue));
                rightButton.setText(String.valueOf(rightValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
