package animate.zero.com.animaterepository.circle_wave;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import animate.zero.com.animaterepository.R;

/**
 * Created by zzj on 17-10-9.
 */

public class CircleWaveView extends View {

    private float defaultAnchor1;

    private Paint wavePaint = new Paint();
    private Paint mViewPaint = new Paint();
    private Shader shader;
    private final float waveInternal = 90;

    private int circleRadius;
    private float swing;
    private int waterColor;
    private int waterHeight;

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

        wavePaint.setColor(waterColor);
        wavePaint.setAntiAlias(true);

        mViewPaint.setColor(waterColor);
        mViewPaint.setAntiAlias(true);
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
        canvas.drawCircle(getWidth()/2, getHeight()/2, circleRadius, mViewPaint);
    }

    private void createShader() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

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
}
