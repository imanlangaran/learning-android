package com.iman.fragment1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class FragmentStudentDetails extends DialogFragment {
    TextView tvId, tvName, tvScore;

    public static FragmentStudentDetails newInstance(String id, String name, int score) {

        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("name", name);
        args.putInt("score", score);
        FragmentStudentDetails fragment = new FragmentStudentDetails();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_student_details, container, false);
        tvId = rootView.findViewById(R.id.tv_student_id);
        tvName = rootView.findViewById(R.id.tv_student_name);
        tvScore = rootView.findViewById(R.id.tv_student_score);

        tvId.setText(getArguments().getString("id"));
        tvName.setText(getArguments().getString("name"));
        tvScore.setText(String.valueOf(getArguments().getInt("score")));

        return rootView;
    }
}
