package com.iman.fragment1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment {

    private EditText input_email;
    private Button btn_submit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);

        input_email = view.findViewById(R.id.input_email);
        btn_submit = view.findViewById(R.id.btn_submit);

        btn_submit.setEnabled(false);

        input_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = input_email.getText().toString().trim();
                if (email.isEmpty() || !email.contains("@") || email.lastIndexOf("@") > email.lastIndexOf(".")
                        || email.split("@").length != 2 || email.indexOf("@") == 0 || email.charAt(email.length()-1) == '.'){
                    btn_submit.setEnabled(false);
                }else {
                    btn_submit.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = input_email.getText().toString().trim();
                Toast.makeText(getContext(), "Email : \n" + email, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
