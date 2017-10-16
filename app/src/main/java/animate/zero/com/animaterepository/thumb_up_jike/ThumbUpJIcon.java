package animate.zero.com.animaterepository.thumb_up_jike;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import animate.zero.com.animaterepository.R;

/**
 * Created by zzj on 17-10-13.
 */

public class ThumbUpJIcon extends View {

    private Paint paint;
    private Paint shiningCirclePaint;

    private Bitmap bitmapUnselected;
    private Bitmap bitmapSelected;
    private Bitmap bitmapShining;

    private float shiningCircleRadius = 0;

    public ThumbUpJIcon(Context context) {
        super(context);
    }

    public ThumbUpJIcon(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmapUnselected = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_unselected);
        bitmapSelected = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected);
        bitmapShining = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected_shining);

        paint = new Paint();
        paint.setAntiAlias(true);

        shiningCirclePaint = new Paint();
        shiningCirclePaint.setAntiAlias(true);
        shiningCirclePaint.setColor(getResources().getColor(R.color.thumbUp_J_orange));
        shiningCirclePaint.setStyle(Paint.Style.STROKE);
        shiningCirclePaint.setStrokeWidth(10);
    }

    public ThumbUpJIcon(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //alpha变化：1.0f -> 0f   255 -> 0
        shiningCirclePaint.setAlpha((int) (255 - shiningCircleRadius * 255 / getShiningCircleMaxRadius()));
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, shiningCircleRadius, shiningCirclePaint);

        if(shiningCircleRadius == 0) {
            canvas.drawBitmap(bitmapUnselected,
                    getWidth() / 2 - bitmapUnselected.getWidth() / 2,
                    getHeight() / 2 - bitmapUnselected.getHeight() / 2,
                    paint);
        } else if(shiningCircleRadius >= getShiningCircleMaxRadius()*0.8) {
            canvas.drawBitmap(bitmapSelected,
                    getWidth() / 2 - bitmapSelected.getWidth() / 2,
                    getHeight() / 2 - bitmapSelected.getHeight() / 2,
                    paint);
            canvas.drawBitmap(bitmapShining,
                    getWidth() / 2 - bitmapShining.getWidth() / 2,
                    getHeight() / 2 - bitmapSelected.getHeight() / 2 - bitmapShining.getHeight() / 2,
                    paint);
        }
    }

    public void setShiningCircleRadius(float radius) {
        shiningCircleRadius = radius;
        invalidate();
    }

    public int getShiningCircleMaxRadius() {
        return bitmapSelected.getWidth() / 2 + bitmapShining.getWidth() / 2;
    }
}
