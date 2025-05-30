package app.jasonhk.hkiit.fifteentwenty.ui.game.round;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import app.jasonhk.hkiit.fifteentwenty.R;
import app.jasonhk.hkiit.fifteentwenty.model.Hands;

public class HandsView extends LinearLayout
{
    private final Context context;

    public HandsView(Context context)
    {
        super(context);
        this.context = context;

        init();
    }

    public HandsView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;

        init();
    }

    private void init()
    {
        inflate(context, R.layout.view_hands, this);
    }

    public void setHands(@NonNull Hands hands)
    {

    }
}
