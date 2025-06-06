package app.jasonhk.hkiit.fifteentwenty.model;

public enum Side
{
    PLAYER,
    OPPONENT;

    public static Side fromRound(int round)
    {
        return (((round % 2) == 0) ? OPPONENT : PLAYER);
    }
}
