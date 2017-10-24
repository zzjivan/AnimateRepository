package animate.zero.com.animaterepository.circle_run_xiaomi;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
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

    private Bitmap background;

    //圆环相关
    private final int circleRadius = 300;
    private final int offset = 8;
    private int circleRotateDegree = 0;
    Shader sweepGradientShader;
    Shader sweepGradientShader1;
    Shader shadowLinearGradient;

    //烟花相关
    private final int pointAreaDegree = 30;
    private Random random;

    //连接完成后相关
    private float circleScale = 1.0f;
    private float finalCircleScale = 1.3f;
    private int targetDegree = 180;
    private boolean showData = false;
    private int shadowRotateDegree = 0;
    private final int shadowLayerCount = 5;
    private final int shadowLayerOffset = 10;

    private String kilometer = "1.5公里";
    private String kilocalorie = "34千卡";
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

        background = BitmapFactory.decodeResource(getResources(), R.drawable.bg_step_law);

        //关闭硬件加速
        //setLayerType(View.LAYER_TYPE_SOFTWARE, null);

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
        shadowPaint.setStrokeWidth(40);
        shadowPaint.setAntiAlias(true);
        //shadowPaint.setStyle(Paint.Style.STROKE);
        //shadowPaint.setStrokeCap(Paint.Cap.ROUND);
        //shadowPaint.setColor(Color.parseColor("#AAFFFFFF"));
        //shadowPaint.setAntiAlias(true);
        //shadowPaint.setStrokeWidth(40);
        //shadowPaint.setShadowLayer(30, 30, 0, Color.parseColor("#ffffff"));

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

        if(sweepGradientShader1 == null)
            sweepGradientShader1 = new SweepGradient(width/2, height/2,
                    new int[] {Color.parseColor("#FFFFFF"), Color.parseColor("#00FFFFFF"), Color.parseColor("#00FFFFFF"), Color.parseColor("#00FFFFFF"), Color.parseColor("#FFFFFF")},
                    new float[] {0, 0.25f, 0.5f, 0.75f, 1.0f});

        if (shadowLinearGradient == null)
            shadowLinearGradient = new LinearGradient(
                    width/2, height/2,
                    width/2 + finalCircleScale*circleRadius, height/2,
                    Color.parseColor("#00000000"),
                    Color.parseColor("#FFFFFF"),
                    Shader.TileMode.CLAMP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect src = new Rect(0,0,background.getWidth(),background.getHeight());
        Rect dist = new Rect(0,0,width,height);
        canvas.drawBitmap(background, src, dist, new Paint());

        if(connecting) {//连接中 动画
            if (circlePaint.getShader() != null)
                circlePaint.setShader(sweepGradientShader);
            circlePaint.setShadowLayer(5, 0, 0, Color.parseColor("#33FFFFFF"));
            circlePaint.setPathEffect(null);
            circlePaint.setStrokeWidth(6);
            connectingAnimator(canvas);
        } else {//接入后 动画
            circlePaint.setShadowLayer(0, 0, 0, Color.parseColor("#33FFFFFF"));
            circlePaint.setPathEffect(null);
            circlePaint.setShader(null);
            circlePaint.setColor(Color.parseColor("#99FFFFFF"));
            circlePaint.setStrokeWidth(40);
            canvas.drawCircle(width/2, height/2, circleRadius*circleScale, circlePaint);


            if (showData) {
                //绘制虚线圈
                PathEffect pathEffect = new DashPathEffect(new float[]{5, 5}, 0);
                circlePaint.setShadowLayer(0, 0, 0, Color.parseColor("#33FFFFFF"));
                circlePaint.setPathEffect(pathEffect);
                circlePaint.setShader(null);
                circlePaint.setColor(Color.parseColor("#99FFFFFF"));
                circlePaint.setStrokeWidth(6);
                canvas.drawCircle(width / 2, height / 2, circleRadius, circlePaint);

                //绘制虚线上的 进度圈
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

                //阴影环绕的效果：SweepGradient + setShadowLayer解决的比较完美。
//                if(shadowPaint.getShader() == null)
//                    shadowPaint.setShader(sweepGradientShader1);
//                canvas.save();
//                canvas.rotate(shadowRotateDegree, width/2, height/2);
//                canvas.drawArc(width/2 - 1.3f*circleRadius,
//                        height/2 - 1.3f*circleRadius,
//                        width/2 + 1.3f*circleRadius,
//                        height/2 + 1.3f*circleRadius,
//                        -90, 180, false, shadowPaint);

                //修改了阴影生成的方法，用画椭圆的方法来实现，更接近小米原生效果，而且不用关闭硬件加速。
                canvas.save();
                canvas.rotate(shadowRotateDegree, width/2, height/2);
                if(shadowPaint.getShader() == null)
                    shadowPaint.setShader(shadowLinearGradient);
                for(int i = 0; i < shadowLayerCount; i ++) {
                    shadowPaint.setAlpha(0xff * (shadowLayerCount - i) / (shadowLayerCount * 3));
                    canvas.drawOval(width/2 - finalCircleScale*circleRadius,
                            height/2 - finalCircleScale*circleRadius,
                            width/2 + finalCircleScale*circleRadius + i * shadowLayerOffset,
                            height/2 + finalCircleScale*circleRadius,
                            shadowPaint);
                }
                canvas.restore();
            }
        }

        //绘制步数
        textPaint.setAlpha(255);
        textPaint.setTextSize(180);
        canvas.drawText("2274", width/2-textPaint.measureText("2274")/2, height/2+48, textPaint);

        //绘制 公里 分割线 卡路里
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

    public void setShadowRotateDegree(int degree) {
        shadowRotateDegree = degree;
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

        Keyframe circleScaleKeyframe1 = Keyframe.ofFloat(0, 1.2f);
        Keyframe circleScaleKeyframe2 = Keyframe.ofFloat(0.5f, 1.5f);
        Keyframe circleScaleKeyframe3 = Keyframe.ofFloat(1.0f, finalCircleScale);
        PropertyValuesHolder circleScaleKeyframe = PropertyValuesHolder.ofKeyframe("circleScale", circleScaleKeyframe1,circleScaleKeyframe2,circleScaleKeyframe3);

        ObjectAnimator circleScale = ObjectAnimator.ofPropertyValuesHolder(this, circleScaleKeyframe);
        circleScale.setDuration(500);
        ObjectAnimator shadowRun = ObjectAnimator.ofInt(this, "shadowRotateDegree", 0, 360);
        shadowRun.setDuration(5000);
        shadowRun.setInterpolator(new LinearInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(circleScale);
        animatorSet.play(shadowRun).after(circleScale);
        animatorSet.start();

        circleScale.addListener(new Animator.AnimatorListener() {
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
        circlePaint.setShader(sweepGradientShader);

        Path path = new Path();
        //回退30度范围内，绘制小圆点。
        int pointRadius;
        pointPaint.setStrokeWidth(10);
        for (int degree = -1; degree >= -pointAreaDegree; degree --) {
            //越往后，圆点透明度越低
            pointPaint.setAlpha(255+240/pointAreaDegree*degree);
            //以最内侧圆圈的半径为基准，向两侧散列 小圆点。
            //小圆点所处的圆半径 = 最内侧圆半径 + 正负2倍degree范围内的随机数。
            pointRadius = circleRadius-offset*2 + 2*(random.nextInt(-degree*2)+degree);
            canvas.drawPoint(width/2 + pointRadius * (float)Math.abs(Math.cos(degree*Math.PI/180)),
                    height/2 - pointRadius * (float)Math.abs(Math.sin(degree*Math.PI/180)),
                    pointPaint);

            //为了小圆点多一点，多画一次。
            pointRadius = circleRadius-offset*2 + 2*(random.nextInt(-degree*2)+degree);
            canvas.drawPoint(width/2 + pointRadius * (float)Math.abs(Math.cos(degree*Math.PI/180)),
                    height/2 - pointRadius * (float)Math.abs(Math.sin(degree*Math.PI/180)),
                    pointPaint);

        }

        //每画一个圈，旋转2度。
        path.reset();
        canvas.drawCircle(width/2, height/2, circleRadius-offset*2, circlePaint);

        canvas.rotate(-2, width/2, height/2);
        canvas.drawCircle(width/2-offset, height/2, circleRadius-offset*3/2, circlePaint);

        canvas.rotate(-2, width/2, height/2);
        canvas.drawCircle(width/2-offset, height/2, circleRadius-offset, circlePaint);

        canvas.rotate(-2, width/2, height/2);
        canvas.drawCircle(width/2-offset/2, height/2, circleRadius-offset/2, circlePaint);

        canvas.rotate(-2, width/2, height/2);
        canvas.drawCircle(width/2, height/2, circleRadius, circlePaint);

        canvas.rotate(-2, width/2, height/2);
        canvas.drawCircle(width/2, height/2, circleRadius+offset/2, circlePaint);

        canvas.drawCircle(width/2, height/2+offset/2, circleRadius+offset, circlePaint);

        canvas.restore();
    }
}
