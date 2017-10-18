package animate.zero.com.animaterepository.circle_run_xiaomi;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.Random;

import animate.zero.com.animaterepository.R;

/**
 * Created by zzj on 17-10-18.
 */

public class CircleRun extends View {
    private Paint circlePaint;
    private Paint textPaint;
    private Paint pointPaint;
    private Paint shadowPaint;

    //圆环相关
    private final int circleRadius = 300;
    private final int offset = 8;
    private int circleRotateDegree = 0;
    Shader sweepGradientShader;
    Shader radialGradientShader;

    //烟花相关
    private final int pointAreaDegree = 30;
    private Random random;

    //连接完成后相关
    private float circleScale = 1.0f;
    private int targetDegree = 180;
    private boolean showData = false;

    private String kilometer = "0公里";
    private String kilocalorie = "0千卡";
    private final int paddingDelivery = 18;

    private ObjectAnimator circleRotate;
    private boolean connecting = true;

    private int width;
    private int height;

    public CircleRun(Context context) {
        super(context);
    }

    public CircleRun(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(6);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(180);

        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setColor(Color.WHITE);
        pointPaint.setStrokeCap(Paint.Cap.ROUND);
        pointPaint.setStrokeWidth(10);
        random = new Random();

        shadowPaint = new Paint();
        shadowPaint.setStyle(Paint.Style.STROKE);
        shadowPaint.setColor(Color.parseColor("#33FFFFFF"));
        shadowPaint.setAntiAlias(true);
        shadowPaint.setStrokeWidth(40);
    }

    public CircleRun(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();

        if(sweepGradientShader == null)
            sweepGradientShader = new SweepGradient(width/2, height/2,
                    Color.parseColor("#00FFFFFF"), Color.parseColor("#77FFFFFF"));

        if(radialGradientShader == null)
            radialGradientShader = new RadialGradient(width/2, height/2,
                    1.35f*circleRadius, Color.parseColor("#99FFFFFF"), Color.parseColor("#99FFFFFF"), Shader.TileMode.CLAMP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.waterBlue));

        if(connecting) {
            if (circlePaint.getShader() != null)
                circlePaint.setShader(sweepGradientShader);
            circlePaint.setShadowLayer(5, 0, 0, Color.parseColor("#33FFFFFF"));
            circlePaint.setPathEffect(null);
            circlePaint.setStrokeWidth(6);
            connectingAnimator(canvas);
        } else {
            circlePaint.setShadowLayer(0, 0, 0, Color.parseColor("#33FFFFFF"));
            circlePaint.setPathEffect(null);
            circlePaint.setShader(null);
            circlePaint.setColor(Color.parseColor("#99FFFFFF"));
            circlePaint.setStrokeWidth(40);
            canvas.drawCircle(width/2, height/2, circleRadius*circleScale, circlePaint);


            if (showData) {
                PathEffect pathEffect = new DashPathEffect(new float[]{5, 5}, 0);
                circlePaint.setShadowLayer(0, 0, 0, Color.parseColor("#33FFFFFF"));
                circlePaint.setPathEffect(pathEffect);
                circlePaint.setShader(null);
                circlePaint.setColor(Color.parseColor("#99FFFFFF"));
                circlePaint.setStrokeWidth(6);
                canvas.drawCircle(width / 2, height / 2, circleRadius, circlePaint);

                circlePaint.setShadowLayer(0, 0, 0, Color.parseColor("#33FFFFFF"));
                circlePaint.setPathEffect(null);
                circlePaint.setColor(Color.parseColor("#FFFFFF"));
                canvas.drawArc(width / 2 - circleRadius,
                        height / 2 - circleRadius,
                        width / 2 + circleRadius,
                        height / 2 + circleRadius,
                        -90, targetDegree + 90, false, circlePaint);


                pointPaint.setAlpha(255);
                pointPaint.setStrokeWidth(20);
                canvas.drawPoint(width / 2 + circleRadius * (float) Math.cos(targetDegree * Math.PI / 180),
                        height / 2 + circleRadius * (float) Math.sin(targetDegree * Math.PI / 180),
                        pointPaint);

                if(shadowPaint.getShader() == null)
                    shadowPaint.setShader(radialGradientShader);
                shadowPaint.setShadowLayer(15, -10, 0, Color.parseColor("#33FFFFFF"));
                canvas.drawCircle(width/2-15, height/2, 1.3f*circleRadius+5, shadowPaint);
            }
        }

        textPaint.setAlpha(255);
        textPaint.setTextSize(180);
        canvas.drawText("2274", width/2-textPaint.measureText("2274")/2, height/2+48, textPaint);

        textPaint.setAlpha(155);
        textPaint.setTextSize(48);
        canvas.drawText(kilometer, width/2-textPaint.measureText(kilometer)-paddingDelivery, height/2+circleRadius/2, textPaint);
        canvas.drawLine(width/2, height/2+circleRadius/2, width/2, height/2+circleRadius/2-36, textPaint);
        canvas.drawText(kilocalorie, width/2 + paddingDelivery, height/2+circleRadius/2, textPaint);
    }

    public void setCircleRotateDegree(int degree) {
        circleRotateDegree = degree;
        invalidate();
    }

    public void setCircleScale(float scale) {
        circleScale = scale;
        invalidate();
    }

    public void setKilometer(String kilometer) {
        this.kilometer = kilometer;
        invalidate();
    }

    public void setKilocalorie(String kilocalorie) {
        this.kilocalorie = kilocalorie;
        invalidate();
    }

    public void reset() {
        showData = false;
    }

    public void startConnectingAnimation() {
        connecting = true;
        circleRotate = ObjectAnimator.ofInt(this,
                "circleRotateDegree", 0, 360);
        circleRotate.setInterpolator(new LinearInterpolator());
        circleRotate.setDuration(2000);
        circleRotate.setRepeatCount(-1);
        circleRotate.start();
    }

    public void ConnectedAnimation() {
        connecting = false;
        circleRotate.cancel();

        Keyframe keyframe1 = Keyframe.ofFloat(0, 1.2f);
        Keyframe keyframe2 = Keyframe.ofFloat(0.5f, 1.6f);
        Keyframe keyframe3 = Keyframe.ofFloat(1.0f, 1.3f);
        PropertyValuesHolder keyFrame = PropertyValuesHolder.ofKeyframe("circleScale", keyframe1,keyframe2,keyframe3);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(this, keyFrame);
        objectAnimator.setDuration(300);
        objectAnimator.start();
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                showData = true;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    private void connectingAnimator (Canvas canvas) {
        canvas.save();
        canvas.rotate(circleRotateDegree, width/2, height/2);

        //TODO:画圈这里为了尽量看着和小米类似，写的很不优雅
        //每画一个圈，旋转2度。
        circlePaint.setShader(sweepGradientShader);
        canvas.drawCircle(width/2, height/2+offset/2, circleRadius+offset, circlePaint);

        canvas.rotate(2, width/2, height/2);
        canvas.drawCircle(width/2, height/2, circleRadius+offset/2, circlePaint);

        canvas.rotate(2, width/2, height/2);
        canvas.drawCircle(width/2, height/2, circleRadius, circlePaint);

        canvas.rotate(2, width/2, height/2);
        canvas.drawCircle(width/2-offset/2, height/2, circleRadius-offset/2, circlePaint);

        canvas.rotate(2, width/2, height/2);
        canvas.drawCircle(width/2-offset, height/2, circleRadius-offset, circlePaint);

        canvas.rotate(2, width/2, height/2);
        canvas.drawCircle(width/2-offset, height/2, circleRadius-offset/2*3, circlePaint);

        canvas.drawCircle(width/2, height/2, circleRadius-offset*2, circlePaint);

        //回退30度范围内，绘制小圆点。
        int pointRadius;
        pointPaint.setStrokeWidth(10);
        for (int degree = -1; degree >= -pointAreaDegree; degree --) {
            //越往后，圆点透明度越低
            pointPaint.setAlpha(255+240/pointAreaDegree*degree);
            //以最内侧圆圈的半径为基准，向两侧散列 小圆点。
            pointRadius = circleRadius-offset*2 + 2*(random.nextInt(-degree*2)+degree);
            canvas.drawPoint(width/2 + pointRadius * (float)Math.abs(Math.cos(degree*Math.PI/180)),
                    height/2 - pointRadius * (float)Math.abs(Math.sin(degree*Math.PI/180)),
                    pointPaint);
            //为了小圆点多一点，多画一次。
            pointRadius = circleRadius-offset*2 + 3*(random.nextInt(-degree*2)+degree);
            canvas.drawPoint(width/2 + pointRadius * (float)Math.abs(Math.cos(degree*Math.PI/180)),
                    height/2 - pointRadius * (float)Math.abs(Math.sin(degree*Math.PI/180)),
                    pointPaint);
        }
        canvas.restore();
    }
}
