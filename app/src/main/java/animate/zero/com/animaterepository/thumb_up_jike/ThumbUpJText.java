package animate.zero.com.animaterepository.thumb_up_jike;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zzj on 17-10-13.
 */

public class ThumbUpJText extends View {

    Paint paint;
    public ThumbUpJText(Context context) {
        super(context);
    }

    public ThumbUpJText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ThumbUpJText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawText("0", 0, 0, paint);
    }
}
