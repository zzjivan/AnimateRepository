package animate.zero.com.animaterepository.circle_run_xiaomi;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import animate.zero.com.animaterepository.R;

/**
 * Created by zzj on 17-10-18.
 */

public class CircleRunLayout extends RelativeLayout {
    private CircleRun circleRun;
    private Button button;
    private Runnable delayAction;

    private Handler handler = new Handler();

    public CircleRunLayout(Context context) {
        super(context);
    }

    public CircleRunLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleRunLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        circleRun = findViewById(R.id.circle_run_view);
        button = findViewById(R.id.bt_start);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                circleRun.reset();
                circleRun.startConnectingAnimation();
                handler.postDelayed(delayAction, 1000);
            }
        });

        delayAction = new Runnable() {
            @Override
            public void run() {
                circleRun.ConnectedAnimation();
            }
        };
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacks(delayAction);
    }
}
