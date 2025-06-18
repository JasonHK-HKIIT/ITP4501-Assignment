package app.jasonhk.hkiit.fifteentwenty.ui.game.choices;

import androidx.annotation.NonNull;

import com.google.android.material.slider.LabelFormatter;

public class GuessLabelFormatter implements LabelFormatter
{
    private final String[] guesses;

    public GuessLabelFormatter(@NonNull String[] guesses)
    {
        this.guesses = guesses;
    }

    @NonNull
    @Override
    public String getFormattedValue(float value)
    {
        return guesses[(int) (value / guesses.length)].toUpperCase();
    }
}
