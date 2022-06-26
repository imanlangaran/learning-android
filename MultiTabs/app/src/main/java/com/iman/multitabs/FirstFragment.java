package com.iman.multitabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;

public class FirstFragment extends Fragment {

    private int bgColor = 0;
    private int imageResId = 0;
    private String title = "";
    private String description = "";

    public static FirstFragment newInstance(int bgColor, int imageResId, String title, String description) {

        Bundle args = new Bundle();
        args.putInt("bgColor", bgColor);
        args.putInt("imageResId", imageResId);
        args.putString("title", title);
        args.putString("description", description);
        FirstFragment fragment = new FirstFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args == null) return;
        bgColor = args.getInt("bgColor");
        imageResId = args.getInt("imageResId");
        title = args.getString("title");
        description = args.getString("description");
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.slide, container, false);

        ((ImageView) view.findViewById(R.id.image)).setImageResource(imageResId);
        ((TextView) view.findViewById(R.id.title)).setText(title);
        ((TextView) view.findViewById(R.id.description)).setText(description);
        view.findViewById(R.id.parent).setBackgroundColor(bgColor);

        return view;
    }


}
