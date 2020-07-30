package com.lcb.study.vipcourseuitest.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lcb.study.vipcourseuitest.R;
import com.lcb.study.vipcourseuitest.animation.animator.MyObjectAnimator;
import com.lcb.study.vipcourseuitest.animation.animator.TimeInterpolator;

public class AnimationActivity extends AppCompatActivity {
    Button button=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        button=findViewById(R.id.btn);
    }

    public void scale(View view) {
        MyObjectAnimator objectAnimator = MyObjectAnimator.
                ofFloat(button, "scaleX", 1.0f, 2.0f);
        objectAnimator.setDuration(3000);
        objectAnimator.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                return input;//匀速
//                return (float)(Math.cos((input + 1) * Math.PI) / 2.0f) + 0.5f;//先加速匀速
            }
        });
        objectAnimator.start();
        MyObjectAnimator objectAnimator2 = MyObjectAnimator.
                ofFloat(button, "scaleY", 1.0f, 2.0f);
        objectAnimator2.setDuration(3000);
        objectAnimator2.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                return input; //匀速
//                return (float)(Math.cos((input + 1) * Math.PI) / 2.0f) + 0.5f;//先加速匀速
            }
        });
        objectAnimator2.start();
    }
}
