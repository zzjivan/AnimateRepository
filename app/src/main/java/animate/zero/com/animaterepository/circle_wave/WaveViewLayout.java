package animate.zero.com.animaterepository.circle_wave;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import animate.zero.com.animaterepository.R;


/**
 * Created by zzj on 17-10-9.
 */

public class WaveViewLayout extends RelativeLayout {

    private Button bt_start;
    private CircleWaveView circleWaveView;

    private ObjectAnimator objectAnimator1;

    public WaveViewLayout(Context context) {
        super(context);
    }

    public WaveViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WaveViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        bt_start = findViewById(R.id.bt_start);
        bt_start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(objectAnimator1.isPaused()) {
                    objectAnimator1.resume();
                } else if(objectAnimator1.isRunning()) {
                    objectAnimator1.pause();
                } else {
                    objectAnimator1.start();
                }
            }
        });
        circleWaveView = findViewById(R.id.wave_view);
        objectAnimator1 = ObjectAnimator.ofFloat(circleWaveView, "defaultAnchor1", 0, 360);
        objectAnimator1.setInterpolator(new LinearInterpolator());
        objectAnimator1.setDuration(1000);
        objectAnimator1.setRepeatCount(-1);
    }
}
