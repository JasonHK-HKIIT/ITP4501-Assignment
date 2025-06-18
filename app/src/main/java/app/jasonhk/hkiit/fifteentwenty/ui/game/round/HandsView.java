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

    private int iconSize = 0;

    private ImageView leftImage;
    private ImageView rightImage;

    private Hands hands = new Hands(false, false);

    public HandsView(Context context)
    {
        super(context);
        this.context = context;

        init();
    }

    public HandsView(Context context, @Nullable AttributeSet attributeSet)
    {
        super(context, attributeSet);
        this.context = context;

        try (var attrs = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.HandsView, 0, 0))
        {
            iconSize = attrs.getDimensionPixelSize(R.styleable.HandsView_iconSize, iconSize);
        }

        init();
    }

    private void init()
    {
        inflate(context, R.layout.view_hands, this);

        leftImage = findViewById(R.id.view_hands_image_left);
        leftImage.getLayoutParams().width = iconSize;
        leftImage.getLayoutParams().height = iconSize;

        rightImage = findViewById(R.id.view_hands_image_right);
        rightImage.getLayoutParams().width = iconSize;
        rightImage.getLayoutParams().height = iconSize;
    }

    public void setHands(@NonNull Hands hands)
    {
        this.hands = hands;

        leftImage.setImageResource(hands.left() ? R.drawable.ic_hand_opened_left : R.drawable.ic_hand_closed_left);
        rightImage.setImageResource(hands.right() ? R.drawable.ic_hand_opened_right : R.drawable.ic_hand_closed_right);
    }

    public Hands getHands()
    {
        return hands;
    }
}
