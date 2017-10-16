package animate.zero.com.animaterepository.thumb_up_jike;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import animate.zero.com.animaterepository.R;

/**
 * Created by zzj on 17-10-13.
 */

public class ThumbUpJLayout extends RelativeLayout {

    private ThumbUpJIcon icon;
    private ThumbUpJText text;

    private boolean selected = false;
    private int count = 1699;

    public ThumbUpJLayout(Context context) {
        super(context);
    }

    public ThumbUpJLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThumbUpJLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        icon = findViewById(R.id.icon);
        text = findViewById(R.id.thumb_num);
        text.setText(count);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                text.operate(!selected);
                if (!selected) {
                    //点击时候，扩张的波纹效果。
                    //波纹的透明度变化和半径的变化是有关系的，直接计算（在ThumbUpJIcon中），就不用另外的动画了。
                    ObjectAnimator circleRadius = ObjectAnimator.ofFloat(icon, "shiningCircleRadius", 0,
                            icon.getShiningCircleMaxRadius());
//                    circleRadius.setDuration(300);
//                    circleRadius.start();

                    PropertyValuesHolder offsetY = PropertyValuesHolder.ofFloat("offsetY", 0, -text.getFontSpacing());
                    PropertyValuesHolder textAlpha = PropertyValuesHolder.ofFloat("textAlpha", 0, 255);
                    ObjectAnimator textTranslate =  ObjectAnimator.ofPropertyValuesHolder(text, offsetY, textAlpha);

                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(circleRadius, textTranslate);
                    set.setDuration(300);
                    set.start();

                    //count ++;
                } else {
                    icon.setShiningCircleRadius(0);

                    PropertyValuesHolder offsetY = PropertyValuesHolder.ofFloat("offsetY", -text.getFontSpacing(), 0);
                    PropertyValuesHolder textAlpha = PropertyValuesHolder.ofFloat("textAlpha", 255, 0);
                    ObjectAnimator textTranslate =  ObjectAnimator.ofPropertyValuesHolder(text, offsetY, textAlpha);
                    textTranslate.setDuration(300);
                    textTranslate.start();

                    //count --;
                }
                selected = !selected;
            }
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //按下时缩小icon
                        icon.setScaleX(0.8f);
                        icon.setScaleY(0.8f);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        //取消或者点击会有一个扩大操作，放大需要放在onClick之前。
                        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(icon, "scaleX", 0.8f, 1.0f);
                        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(icon, "scaleY", 0.8f, 1.0f);
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(objectAnimatorX, objectAnimatorY);
                        set.setDuration(100);
                        set.start();
                }
                return false;
            }
        });
    }
}
