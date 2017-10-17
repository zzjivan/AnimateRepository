package animate.zero.com.animaterepository.thumb_up_jike;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import animate.zero.com.animaterepository.R;

/**
 * Created by zzj on 17-10-17.
 */

public class OperateLayout extends RelativeLayout {
    private EditText editor;
    private Button editor_confirm;
    private ThumbUpJText thumbUpJText;
    public OperateLayout(Context context) {
        super(context);
    }

    public OperateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OperateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        editor = findViewById(R.id.editor);
        editor_confirm = findViewById(R.id.editor_confirm);
        thumbUpJText = findViewById(R.id.thumb_num);

        editor_confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                thumbUpJText.setText(Integer.parseInt(editor.getText().toString()));
            }
        });
    }
}
