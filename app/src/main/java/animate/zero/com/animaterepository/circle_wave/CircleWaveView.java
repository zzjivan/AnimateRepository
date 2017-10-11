package animate.zero.com.animaterepository.circle_wave;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import animate.zero.com.animaterepository.R;

/**
 * Created by zzj on 17-10-9.
 */

public class CircleWaveView extends View implements SensorEventListener{

    private float defaultAnchor1;

    private Paint circlePaint = new Paint();
    private Paint wavePaint = new Paint();
    private Paint mViewPaint = new Paint();
    private Shader shader;
    private final float waveInternal = 90;

    private int circleRadius;
    private float swing;
    private int waterColor;
    private int waterHeight;

    private SensorManager sensorManager;
    private float[] accelerometerValues = new float[3];
    private float[] magneticFieldValues = new float[3];
    private float orientation0 = 0;
    private float orientation1 = 0;
    private float orientation2 = 0;

    public CircleWaveView(Context context) {
        super(context, null);
    }

    public CircleWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleWaveView, 0, 0);
        waterColor = typedArray.getColor(R.styleable.CircleWaveView_waterColor,
                getResources().getColor(R.color.waterBlue));
        swing = typedArray.getFloat(R.styleable.CircleWaveView_waveSwing, 0.3f);
        circleRadius = typedArray.getDimensionPixelSize(R.styleable.CircleWaveView_circleRadius,
                getResources().getDimensionPixelSize(R.dimen.wave_circle_radius));
        waterHeight = typedArray.getDimensionPixelSize(R.styleable.CircleWaveView_waterHeight,
                circleRadius);

        circlePaint.setColor(Color.LTGRAY);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);

        wavePaint.setColor(waterColor);
        wavePaint.setAntiAlias(true);

        mViewPaint.setColor(waterColor);
        mViewPaint.setAntiAlias(true);

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor aSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this, aSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, mSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    public CircleWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        createShader();
        if(mViewPaint.getShader() != null)
            mViewPaint.setShader(shader);
        canvas.drawCircle(getWidth()/2, getHeight()/2, circleRadius, circlePaint);
        canvas.drawCircle(getWidth()/2, getHeight()/2, circleRadius, mViewPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        sensorManager.unregisterListener(this);
        super.onDetachedFromWindow();
    }

    private void createShader() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.rotate(-orientation2, getWidth()/2, getHeight()/2);
        float startY1 = getHeight()/2;
        float startY2 = getHeight()/2;
        for(int i = getWidth()/2-circleRadius; i <= getWidth()/2+circleRadius; i++) {
            canvas.drawLine(i, startY1,
                    i+1, getHeight(), wavePaint);
            startY1 = startY1 + swing * (float)Math.sin((i+defaultAnchor1) * Math.PI / 180);

            canvas.drawLine(i, startY2,
                    i+1, getHeight(), wavePaint);
            startY2 = startY2 + swing * (float)Math.sin((waveInternal+i+defaultAnchor1) * Math.PI / 180);
        }

        shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
        mViewPaint.setShader(shader);
        bitmap.recycle();
    }

    public float getDefaultAnchor1() {
        return defaultAnchor1;
    }

    public void setDefaultAnchor1(float anchor) {
        defaultAnchor1 = anchor;
        invalidate();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            magneticFieldValues = sensorEvent.values;
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            accelerometerValues = sensorEvent.values;
        float[] values = new float[3];
        float[] R = new float[9];
        SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticFieldValues);
        SensorManager.getOrientation(R, values);
        for(int i = 0; i < 3; i ++) {
            values[i] = (float) Math.toDegrees(values[i]);
            if(Math.abs(values[0] - orientation0) > 20) {//X
                Log.d("zjzhu","value0 diff > 10");
                orientation0 = values[0];
                //invalidate();
            }
            if(Math.abs(values[1] - orientation1) > 20) {//Z
                Log.d("zjzhu","value1 diff > 10");
                orientation1 = values[1];
                //invalidate();
            }
            if(Math.abs(values[2] - orientation2) > 10) {//Y
                Log.d("zjzhu","value2 diff > 10");
                orientation2 = values[2];
                invalidate();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
