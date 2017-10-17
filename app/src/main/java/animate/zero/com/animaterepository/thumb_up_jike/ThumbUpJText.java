package animate.zero.com.animaterepository.thumb_up_jike;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by zzj on 17-10-13.
 */

public class ThumbUpJText extends View {

    private int width;
    private int height;

    private int paddingStart = 0;

    private Paint fixedTextPaint;
    private Paint paintCount;
    private Paint paintCountPlusOne;
    private int textHeight;

    private String bigger = "0";
    private String count = "0";
    private char[] biggerArray;
    private char[] countArray;

    private int animateByteCount = 0;
    private float fixedTextWidth = 0; //固定不动部分文本的宽度
    private float offsetY = 0;
    private float textAlpha = 255;

    private boolean is9Repeat = false;

    public ThumbUpJText(Context context) {
        super(context);
    }

    public ThumbUpJText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paintCount = new Paint();
        paintCount.setAntiAlias(true);
        paintCount.setTextSize(40);
        paintCount.setAlpha((int)textAlpha);
        paintCount.setColor(Color.GRAY);
        Rect rect = new Rect();
        paintCount.getTextBounds(count, 0 ,1, rect);
        textHeight = rect.height();

        paintCountPlusOne = new Paint();
        paintCountPlusOne.setAntiAlias(true);
        paintCountPlusOne.setTextSize(40);
        paintCountPlusOne.setColor(Color.GRAY);
        paintCountPlusOne.setAlpha(255-(int)textAlpha);

        fixedTextPaint = new Paint();
        fixedTextPaint.setAntiAlias(true);
        fixedTextPaint.setTextSize(40);
        fixedTextPaint.setColor(Color.GRAY);

        paddingStart = 0;
        width = (int) paintCount.measureText(bigger) + paddingStart;
        height = (int) paintCount.getFontSpacing() * 3;
    }

    public ThumbUpJText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //getHeight()/2 - paint.measureText(String.valueOf(bigger)) + 2     上
        //getHeight()/2 + paint.measureText(String.valueOf(count))/2 + 2    中
        //getHeight()/2 + paint.measureText(String.valueOf(smaller))*2 + 2  下

        //固定的数字单独用一个paint来绘制
        canvas.drawText(countArray,
                0,
                countArray.length - animateByteCount,
                paddingStart,
                getHeight()/2 + textHeight/2,
                fixedTextPaint);

        //通过offsetY属性和textAlpha属性，制作属性动画效果
        canvas.drawText(countArray,
                countArray.length - animateByteCount,
                animateByteCount,
                paddingStart + fixedTextWidth,
                getHeight()/2 + textHeight/2 + offsetY,
                paintCount);
        canvas.drawText(biggerArray,
                biggerArray.length - animateByteCount - (is9Repeat ? 1 : 0), //若当前是99这样的数，需要多操作1位
                animateByteCount + (is9Repeat ? 1 : 0),
                paddingStart + fixedTextWidth,
                getHeight()/2 + +textHeight/2 + paintCountPlusOne.getFontSpacing() + offsetY,
                paintCountPlusOne);
    }

    public void setText(int count) {
        this.count = String.valueOf(count);
        bigger = String.valueOf(count + 1);

        //setText后需要更新view的宽度
        width = (int) paintCount.measureText(bigger) + paddingStart;

        countArray = this.count.toCharArray();
        biggerArray = bigger.toCharArray();

        is9Repeat = is9Repeat(this.count);
        operate(false);
        invalidate();
    }


    public void operate(boolean operation) {
        animateByteCount = getAnimateByteCount(operation);
        fixedTextWidth = paintCount.measureText(countArray, 0, countArray.length - animateByteCount);
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
        invalidate();
    }

    public void setTextAlpha(float alpha) {
        this.textAlpha = alpha;
        paintCount.setAlpha(255-(int)textAlpha);
        paintCountPlusOne.setAlpha((int)textAlpha);
    }

    public float getFontSpacing() {
        return paintCount.getFontSpacing();
    }

    /**
     * 依据操作类型，计算有动画效果的字符的个数。
     * 9,  99，  999 这类会使整个数位数加一的数比较特别
     * @param operation 点赞操作还是取消操作。true：点赞；false：取消
     * @return
     */
    private int getAnimateByteCount(boolean operation) {
        int ret = 0;
        int count = Integer.parseInt(this.count);
        if (operation) {
            while (count % 10 == 9) {
                ret ++;
                count /= 10;
            }
        } else {
            count ++;
            while (count % 10 == 0) {
                ret ++;
                count /= 10;
            }
        }

        if (!is9Repeat(this.count))
            ret ++;
        return ret;
    }

    private boolean is9Repeat(String count) {
        for (int i = 0; i < count.length(); i ++) {
            if(count.charAt(i) != '9')
                return false;
        }
        return true;
    }

 }
