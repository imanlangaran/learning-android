package com.iman.xmlanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spinnerAnim;
    Button btnStart;
    ImageView imageView;

    String [] animations = {"fade in", "fade out", "move", "move back", "rotate", "blink", "zoom in",
            "zoom out", "offf", "slide up", "slide down", "bounce", "sequential animation"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerAnim = findViewById(R.id.spinner_anim);
        btnStart = findViewById(R.id.btn_start);
        imageView = findViewById(R.id.imageView);

        seUpSpinner();

        btnStart.setOnClickListener(this);
    }

    private void seUpSpinner() {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, animations);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnim.setAdapter(spinnerAdapter);
    }

    @Override
    public void onClick(View v) {
        int index = spinnerAnim.getSelectedItemPosition();
        switch (index){
            case 0:
                fadeIn();
                break;
            case 1:
                fadeOut();
                break;
            case 2:
                move();
                break;
            case 3:
                moveBack();
                break;
            case 4:
                rotate();
                break;
            case 5:
                blink();
                break;
            case 6:
                zoomIn();
                break;
            case 7:
                zoomOut();
                break;
            case 8:
                offf();
                break;
            case 9:
                slideUp();
                break;
            case 10:
                slideDown();
                break;
            case 11:
                bounce();
                break;
            case 12:
                sequential();
                break;
            default:
                Toast.makeText(this, "Choose another animation", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void sequential() {
        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.sequential));

    }

    private void bounce() {
        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce));
    }

    private void slideDown() {
        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slidedown));
    }

    private void slideUp() {
        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slideup));
    }

    private void offf() {
        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.offf));
    }

    private void zoomOut() {
        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoomout));
    }

    private void zoomIn() {
        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoomin));
    }

    private void blink() {
        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blink));
    }

    private void rotate() {
        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate));
    }

    private void moveBack() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.moveback);
        imageView.startAnimation(anim);
    }

    private void move() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.move);
        imageView.startAnimation(anim);
    }

    private void fadeOut() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        imageView.startAnimation(anim);
    }

    private void fadeIn() {
        Animation animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        imageView.startAnimation(animFadeIn);
    }

    public void reset(View view){
        imageView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.reset));
    }
}
