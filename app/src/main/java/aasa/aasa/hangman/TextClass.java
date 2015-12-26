package aasa.aasa.hangman;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by sneha on 11/12/15.
 */
public class TextClass extends TextView {
    public TextClass(Context context) {
        super(context);
        init();
    }

    public TextClass(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextClass(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TextClass(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "friday13sh.ttf");
        setTypeface(tf, 1);
    }


}
