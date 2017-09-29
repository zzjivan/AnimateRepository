package animate.zero.com.animaterepository.heart_fly_up;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

import animate.zero.com.animaterepository.R;
import animate.zero.com.animaterepository.Utils.Util;

/**
 * Created by zzj on 17-9-29.
 */

public class HeartFlyUpLayout extends RelativeLayout {
    Button bt_start;
    ImageView heart;

    private LayoutParams layoutParams;
    private int[] bitmapResources = {
            R.drawable.heart_blue,
            R.drawable.heart_border,
            R.drawable.heart_green,
            R.drawable.heart_red
    };
    private Random random = new Random();

    PointF start_point = new PointF();
    PointF end_point = new PointF();
    PointF controll_point1 = new PointF(300, 300);
    PointF controll_point2 = new PointF(350, 780);

    {
        layoutParams = new LayoutParams((int)Util.dpToPixel(48), (int)Util.dpToPixel(48));
        layoutParams.addRule(CENTER_HORIZONTAL, TRUE);
        layoutParams.addRule(ALIGN_PARENT_BOTTOM, TRUE);
    }

    public HeartFlyUpLayout(Context context) {
        super(context);
    }

    public HeartFlyUpLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeartFlyUpLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        bt_start = findViewById(R.id.bt_start);
        heart = findViewById(R.id.iv_heart);
        bt_start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addHeart();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //初始化气泡的起点，终点坐标
        start_point.set(getWidth()/2, getHeight() - heart.getHeight()/2);
        end_point.set(getWidth()/2, 0);
    }

    private void addHeart() {
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(bitmapResources[random.nextInt(4)]);
        addView(imageView);

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, View.ALPHA, 0.2f, 1.0f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, View.SCALE_X, 0.2f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, View.SCALE_Y, 0.2f, 1.0f);

        //animatorSet.setDuration(500);
        animatorSet.playTogether(alpha,scaleX,scaleY);

        ValueAnimator move = ValueAnimator.ofObject(new BesselEvalulator(controll_point1, controll_point2),
                start_point, end_point);
        move.setTarget(imageView);
        move.setDuration(3000);
        move.addUpdateListener(new BesselListener(imageView));

        AnimatorSet finalSet = new AnimatorSet();
        finalSet.playSequentially(animatorSet);
        finalSet.playSequentially(animatorSet, move);
        finalSet.setTarget(imageView);
        finalSet.setInterpolator(new LinearInterpolator());
        finalSet.start();
    }

    class BesselListener implements ValueAnimator.AnimatorUpdateListener {
        private View target;

        BesselListener(View view) {
            this.target = view;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            PointF value = (PointF) valueAnimator.getAnimatedValue();
            target.setX(value.x);
            target.setY(value.y);
        }
    }

    class BesselEvalulator implements TypeEvaluator<PointF> {
        //控制点
        PointF p1;
        PointF p2;

        BesselEvalulator(PointF p1, PointF p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        @Override
        public PointF evaluate(float fraction, PointF start, PointF end) {
            PointF ret = new PointF(start.x + (end.x - start.x) * fraction,
                    start.y + (end.y - start.y) * fraction);
            return ret;
        }
    }


    //这么写的，不知道为什么move这个动画不执行
//    private void addHeart() {
//        ImageView imageView = new ImageView(getContext());
//        imageView.setLayoutParams(layoutParams);
//        imageView.setImageResource(bitmapResources[random.nextInt(4)]);
//        addView(imageView);
//
//        AnimatorSet animatorSet = new AnimatorSet();
//        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, View.ALPHA, 0.2f, 1.0f);
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, View.SCALE_X, 0.2f, 1.0f);
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, View.SCALE_Y, 0.2f, 1.0f);
//
//        animatorSet.setDuration(500);
//        animatorSet.playTogether(alpha,scaleX,scaleY);
//
//        ValueAnimator move = ValueAnimator.ofObject(new BesselEvalulator(start_point, end_point), start_point, end_point);
//        move.setTarget(imageView);
//        move.setInterpolator(new LinearInterpolator());
//        move.setDuration(3000);
//
//        AnimatorSet finalSet = new AnimatorSet();
//        finalSet.playSequentially(animatorSet);
//        finalSet.playSequentially(animatorSet, move);
//        finalSet.setTarget(imageView);
//        finalSet.setInterpolator(new LinearInterpolator());
//        finalSet.start();
//    }
}
