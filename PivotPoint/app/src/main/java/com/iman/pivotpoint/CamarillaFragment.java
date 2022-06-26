package com.iman.pivotpoint;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.iman.pivotpoint.MainActivity.format0;


public class CamarillaFragment extends Fragment {

    private AppCompatEditText input_high;
    private AppCompatEditText input_low;
    private AppCompatEditText input_close;
    private AppCompatTextView tv_r4;
    private AppCompatTextView tv_r3;
    private AppCompatTextView tv_r2;
    private AppCompatTextView tv_r1;
    private AppCompatTextView tv_s1;
    private AppCompatTextView tv_s2;
    private AppCompatTextView tv_s3;
    private AppCompatTextView tv_s4;



    private float h = 0;
    private float l = 0;
    private float c = 0;
    private float r4, r3, r2, r1, s1, s2, s3, s4;

    private InputMethodManager imm;

    private static final String fileName = "camarilla";
    private SharedPreferences pref;

    public CamarillaFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        pref = getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camarilla, container, false);

        init(view);

        setTvsFromPref();


        view.findViewWithTag("camarilla_reset").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { reset(); }
        });
        view.findViewWithTag("camarilla_cal").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { calculate(); }
        });


        input_high.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                    if (input_high.getText().toString().isEmpty()) {
                        input_high.setError("Fill The Blank");
                    }
                }
                return false;
            }
        });

        input_low.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                    if (input_low.getText().toString().isEmpty()) {
                        input_low.setError("Fill The Blank");
                    }
                }
                return false;
            }
        });

        input_close.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (input_close.getText().toString().isEmpty()) {
                        input_close.setError("Fill The Blank");
                    }
                }
                v.getRootView().findViewWithTag("camarilla_cal").callOnClick();
                return false;
            }
        });


        view.findViewById(R.id.info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setMessage(getResources().getString(R.string.camarilla_info));
                dialog.setCancelable(true);
                dialog.show();
            }
        });


        view.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder data = new StringBuilder();

                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

                data.append("Market Date : ")
                        .append((cal.get(Calendar.YEAR) < 10) ? "0" + (cal.get(Calendar.YEAR))
                                : (cal.get(Calendar.YEAR))).append("/")
                        .append((cal.get(Calendar.MONTH)+1 < 10) ? "0" + (cal.get(Calendar.MONTH)+1)
                                : (cal.get(Calendar.MONTH)+1)).append("/")
                        .append((cal.get(Calendar.DAY_OF_MONTH) < 10) ? "0" + (cal.get(Calendar.DAY_OF_MONTH))
                                : (cal.get(Calendar.DAY_OF_MONTH))).append("\n");

                data.append("Market Day  : ");

                switch (cal.get(Calendar.DAY_OF_WEEK)){
                    case 1:
                        data.append("Sunday").append("\n");
                        break;
                    case 2:
                        data.append("Monday").append("\n");
                        break;
                    case 3:
                        data.append("Thursday").append("\n");
                        break;
                    case 4:
                        data.append("Wednesday").append("\n");
                        break;
                    case 5:
                        data.append("Tuesday").append("\n");
                        break;
                    case 6:
                        data.append("Friday").append("\n");
                        break;
                    case 7:
                        data.append("Saturday").append("\n");
                        break;
                    default:break;
                }

                data.append("Pivot Point Type : Camarilla\n");


                data.append("\nR4 = ").append(tv_r4.getText()).append("\n")
                        .append("R3 = ").append(tv_r3.getText()).append("\n")
                        .append("R2 = ").append(tv_r2.getText()).append("\n")
                        .append("R1 = ").append(tv_r1.getText()).append("\n\n")
                        .append("S1 = ").append(tv_s1.getText()).append("\n")
                        .append("S2 = ").append(tv_s2.getText()).append("\n")
                        .append("S3 = ").append(tv_s3.getText()).append("\n")
                        .append("S4 = ").append(tv_s4.getText());


                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, data.toString());

                Intent copyIntent = new Intent(getContext(), Copy.class);
                copyIntent.putExtra("text", data.toString());


                startActivity(Intent.createChooser(intent, "Share With ...").putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {copyIntent}));

            }
        });

        return view;
    }

    private void reset() {
        r1 = 0;
        r2 = 0;
        r3 = 0;
        r4 = 0;
        s1 = 0;
        s2 = 0;
        s3 = 0;
        s4 = 0;
        h = 0;
        l = 0;
        c = 0;
        saveDataToPref();
        setTvsFromValues();
        Snackbar.make(getView(), "Data Cleared.", Snackbar.LENGTH_LONG).show();
    }

    private void setTvsFromPref() {
        input_high.setText(String.format(Locale.getDefault(), format0, pref.getFloat("h", 0)));
        input_low.setText(String.format(Locale.getDefault(), format0, pref.getFloat("l", 0)));
        input_close.setText(String.format(Locale.getDefault(), format0, pref.getFloat("c", 0)));
        tv_r1.setText(String.format(Locale.getDefault(), format0, pref.getFloat("r1", 0)));
        tv_r2.setText(String.format(Locale.getDefault(), format0, pref.getFloat("r2", 0)));
        tv_r3.setText(String.format(Locale.getDefault(), format0, pref.getFloat("r3", 0)));
        tv_r4.setText(String.format(Locale.getDefault(), format0, pref.getFloat("r4", 0)));
        tv_s1.setText(String.format(Locale.getDefault(), format0, pref.getFloat("s1", 0)));
        tv_s2.setText(String.format(Locale.getDefault(), format0, pref.getFloat("s2", 0)));
        tv_s3.setText(String.format(Locale.getDefault(), format0, pref.getFloat("s3", 0)));
        tv_s4.setText(String.format(Locale.getDefault(), format0, pref.getFloat("s4", 0)));
    }

    private void init(View view) {
        input_high = view.findViewById(R.id.camarilla_input_high);
        input_low = view.findViewById(R.id.camarilla_input_low);
        input_close = view.findViewById(R.id.camarilla_input_close);
        tv_s4 = view.findViewById(R.id.camarilla_s4);
        tv_s3 = view.findViewById(R.id.camarilla_s3);
        tv_s2 = view.findViewById(R.id.camarilla_s2);
        tv_s1 = view.findViewById(R.id.camarilla_s1);
        tv_r1 = view.findViewById(R.id.camarilla_r1);
        tv_r2 = view.findViewById(R.id.camarilla_r2);
        tv_r3 = view.findViewById(R.id.camarilla_r3);
        tv_r4 = view.findViewById(R.id.camarilla_r4);
    }

    private void saveDataToPref() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat("r1", r1);
        editor.putFloat("r2", r2);
        editor.putFloat("r3", r3);
        editor.putFloat("r4", r4);
        editor.putFloat("s1", s1);
        editor.putFloat("s2", s2);
        editor.putFloat("s3", s3);
        editor.putFloat("s4", s4);
        editor.putFloat("h", h);
        editor.putFloat("l", l);
        editor.putFloat("c", c);
        editor.apply();
    }

    private void calculate() {
        String h_text = input_high.getText().toString();
        String l_text = input_low.getText().toString();
        String c_text = input_close.getText().toString();


        if (h_text.isEmpty()) {
            input_high.setError("Fill The Blank");
            input_high.requestFocus();
            imm.toggleSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.SHOW_FORCED, 1);
            return;
        } else if (l_text.isEmpty()) {
            input_low.setError("Fill The Blank");
            input_low.requestFocus();
            imm.toggleSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.SHOW_FORCED, 1);
            return;
        } else if (c_text.isEmpty()) {
            input_close.setError("Fill The Blank.");
            input_close.requestFocus();
            imm.toggleSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.SHOW_FORCED, 1);

            return;
        }


        h = Float.valueOf(h_text);
        l = Float.valueOf(l_text);
        c = Float.valueOf(c_text);

        if (h <= l || c < l || c > h) {
            Snackbar.make(getView(), "Invalid inputs!", BaseTransientBottomBar.LENGTH_LONG).show();
            return;
        }


        r1 = (float) ((h - l) * 1.1 / 12 + c);
        r2 = (float) ((h - l) * 1.1 / 6 + c);
        r3 = (float) ((h - l) * 1.1 / 4 + c);
        r4 = (float) ((h - l) * 1.1 / 2 + c);
        s1 = (float) (c - (h - l) * 1.1 / 12);
        s2 = (float) (c - (h - l) * 1.1 / 6);
        s3 = (float) (c - (h - l) * 1.1 / 4);
        s4 = (float) (c - (h - l) * 1.1 / 2);


        setTvsFromValues();


        saveDataToPref();


        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

//        ((ScrollView)getView().findViewById(R.id.scrollView))
//                .smoothScrollTo(0, ((ScrollView)getView().findViewById(R.id.scrollView)).getBottom())
//                .fullScroll(ScrollView.FOCUS_DOWN);

        ObjectAnimator.ofInt(((ScrollView) getView().findViewById(R.id.camarilla_scrollView)),
                "scrollY",
                ((ScrollView) getView().findViewById(R.id.camarilla_scrollView)).getChildAt(0).getBottom())
                .setDuration(600).start();

        Snackbar.make(getView(), "Data Calculated", Snackbar.LENGTH_LONG).show();
    }

    private void setTvsFromValues() {
        tv_r1.setText(String.format(Locale.getDefault(), format0, r1));
        tv_r2.setText(String.format(Locale.getDefault(), format0, r2));
        tv_r3.setText(String.format(Locale.getDefault(), format0, r3));
        tv_r4.setText(String.format(Locale.getDefault(), format0, r4));
        tv_s1.setText(String.format(Locale.getDefault(), format0, s1));
        tv_s2.setText(String.format(Locale.getDefault(), format0, s2));
        tv_s3.setText(String.format(Locale.getDefault(), format0, s3));
        tv_s4.setText(String.format(Locale.getDefault(), format0, s4));
        input_high.setText(String.format(Locale.getDefault(), format0, h));
        input_low.setText(String.format(Locale.getDefault(), format0, l));
        input_close.setText(String.format(Locale.getDefault(), format0, c));
    }





}
