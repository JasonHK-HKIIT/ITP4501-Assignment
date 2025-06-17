package app.jasonhk.hkiit.fifteentwenty;

import java.util.stream.Stream;

public enum Theme
{
    AUTO,
    LIGHT,
    DARK;

    public static String[] names()
    {
        return Stream.of(values()).map(Theme::name).toArray(String[]::new);
    }
}
