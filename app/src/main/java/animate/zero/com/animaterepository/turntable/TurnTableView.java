package animate.zero.com.animaterepository.turntable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import animate.zero.com.animaterepository.R;

/**
 * Created by zzj on 17-10-10.
 */

public class TurnTableView extends View {
    private Paint outsideCirclePaint = new Paint();
    private Paint innerCirclePaint = new Paint();

    private final int count = 20;
    private final int radius = 700;

    private int width;
    private int height;

    private float defaultAnchor;

    public TurnTableView(Context context) {
        super(context);
    }

    public TurnTableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        outsideCirclePaint.setAntiAlias(true);
        outsideCirclePaint.setStyle(Paint.Style.STROKE);
        outsideCirclePaint.setStrokeWidth(50);
        outsideCirclePaint.setColor(Color.BLACK);

        innerCirclePaint.setAntiAlias(true);
        innerCirclePaint.setStyle(Paint.Style.STROKE);
        innerCirclePaint.setStrokeWidth(50);
        innerCirclePaint.setColor(getResources().getColor(R.color.lightGray));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        Shader sweepGradient1 = new SweepGradient(width/2, height/2,
                Color.parseColor("#ff0000"), Color.parseColor("#00ff00"));
        Shader sweepGradient2 = new SweepGradient(width/2, height/2,
                Color.parseColor("#ff2222"), Color.parseColor("#22ff22"));
        outsideCirclePaint.setShader(sweepGradient1);
        innerCirclePaint.setShader(sweepGradient2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.rotate(-90, width/2, height/2);
        for(int i = 0; i < count; i += 2) {
            canvas.drawArc(width/2-radius/2, height/2-radius/2,
                    width/2+radius/2, height/2+radius/2,
                    18*i+defaultAnchor, 18, false, outsideCirclePaint);
            canvas.drawArc(width/2-radius/2, height/2-radius/2,
                    width/2+radius/2, height/2+radius/2,
                    18*(i+1)+defaultAnchor, 18, false, innerCirclePaint);

            canvas.drawArc(width/2-radius/2+50, height/2-radius/2+50,
                    width/2+radius/2-50, height/2+radius/2-50,
                    18*i+360-defaultAnchor, 18, false, outsideCirclePaint);
            canvas.drawArc(width/2-radius/2+50, height/2-radius/2+50,
                    width/2+radius/2-50, height/2+radius/2-50,
                    18*(i+1)+360-defaultAnchor, 18, false, innerCirclePaint);
        }
        canvas.restore();
    }

    public void setDefaultAnchor(float defaultAnchor) {
        this.defaultAnchor = defaultAnchor;
        invalidate();
    }
}
