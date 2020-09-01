package com.example.vladislav.androidstudy.activities.dynamic_layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import com.example.vladislav.androidstudy.R;
import com.example.vladislav.androidstudy.jobs.simple_jobs.ImageFragment;

/**
 * Fragment which layout is created dynamically.
 * Assign it to a CommonLayout to display.
 *
 * BUG - This fragment doesn't display its contents !!!
 *
 */
public class DynamicLayoutFragment extends Fragment {

    public static final String FRAGMENT_TAG = ImageFragment.class.getSimpleName();
    private Activity mActivity;

    public DynamicLayoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dynamic_layout_fragment, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mActivity = getActivity();
        FrameLayout fragmentContainer = (FrameLayout) mActivity.findViewById(R.id.fragment_container);

        // создание LinearLayout
        LinearLayout linLayout = new LinearLayout(mActivity);
        // установим вертикальную ориентацию
        linLayout.setOrientation(LinearLayout.VERTICAL);
        // создаем LayoutParams
        LinearLayoutCompat.LayoutParams linLayoutParam = new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT);
        // устанавливаем linLayout как корневой элемент экрана
        getActivity().setContentView(linLayout, linLayoutParam);

        LinearLayoutCompat.LayoutParams lpView = new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

        TextView tv = new TextView(mActivity);
        tv.setText("TextView");
        tv.setLayoutParams(lpView);
        linLayout.addView(tv);

        Button btn = new Button(mActivity);
        btn.setText("Button");
        linLayout.addView(btn, lpView);

        LinearLayout.LayoutParams leftMarginParams = new LinearLayout.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        leftMarginParams.leftMargin = 50;

        Button btn1 = new Button(mActivity);
        btn1.setText("Button1");
        linLayout.addView(btn1, leftMarginParams);

        LinearLayout.LayoutParams rightGravityParams = new LinearLayout.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        rightGravityParams.gravity = Gravity.RIGHT;

        Button btn2 = new Button(mActivity);
        btn2.setText("Button2");
        linLayout.addView(btn2, rightGravityParams);

        if (linLayout.getParent() != null) {
            ((ViewGroup) linLayout.getParent()).removeView(linLayout);
        }
        fragmentContainer.addView(linLayout);
    }
}
