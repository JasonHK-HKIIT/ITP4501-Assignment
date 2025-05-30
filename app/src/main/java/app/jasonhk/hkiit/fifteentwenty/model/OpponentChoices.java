package app.jasonhk.hkiit.fifteentwenty.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpponentChoices
{
    @JsonProperty("left")
    private int handLeft;

    @JsonProperty("right")
    private int handRight;

    @JsonProperty("guess")
    private int guess;

    public Hands toHands()
    {
        return new Hands((handLeft == 5), (handRight == 5));
    }
}
