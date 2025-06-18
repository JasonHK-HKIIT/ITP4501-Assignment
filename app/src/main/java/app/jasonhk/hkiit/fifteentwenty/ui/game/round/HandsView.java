package app.jasonhk.hkiit.fifteentwenty.ui.game.round;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import app.jasonhk.hkiit.fifteentwenty.R;
import app.jasonhk.hkiit.fifteentwenty.model.Hands;

public class HandsView extends LinearLayout
{
    private final Context context;

    private Hands hands = new Hands(false, false);

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
        this.hands = hands;

        ImageView leftImage = findViewById(R.id.view_hands_image_left);
        leftImage.setImageResource(hands.left() ? R.drawable.ic_hand_opened_left : R.drawable.ic_hand_closed_left);

        ImageView rightImage = findViewById(R.id.view_hands_image_right);
        rightImage.setImageResource(hands.right() ? R.drawable.ic_hand_opened_right : R.drawable.ic_hand_closed_right);
    }

    public Hands getHands()
    {
        return hands;
    }
}
