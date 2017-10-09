package animate.zero.com.animaterepository.circle_wave;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import animate.zero.com.animaterepository.R;


/**
 * Created by zzj on 17-10-9.
 */

public class WaveViewLayout extends RelativeLayout {

    private CircleWaveView circleWaveView;

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
        circleWaveView = findViewById(R.id.wave_view);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(circleWaveView, "defaultAnchor1", 0, 360);
        objectAnimator1.setInterpolator(new LinearInterpolator());
        objectAnimator1.setDuration(1000);
        objectAnimator1.setRepeatCount(-1);
        objectAnimator1.start();
    }
}
