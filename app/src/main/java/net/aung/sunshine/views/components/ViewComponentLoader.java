package net.aung.sunshine.views.components;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by aungpyae on 24/3/15.
 */
public class ViewComponentLoader extends FrameLayout{

    private static final int LOADER_DISPLAY_OFFSET_MS = 1000;

    public ViewComponentLoader(Context context) {
        super(context);
    }

    public ViewComponentLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewComponentLoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void displayLoader(){
        setVisibility(View.VISIBLE);
    }

    public void dismissLoader(){
        if(getVisibility() == View.VISIBLE) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setVisibility(View.GONE);
                }
            }, LOADER_DISPLAY_OFFSET_MS);
        }
    }
}
