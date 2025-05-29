package app.jasonhk.hkiit.fifteentwenty.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class RankingItem
{
    @JsonProperty("Name")
    public String name;

    @JsonProperty("Correct")
    public int correctCount;

    @JsonProperty("Time")
    public int timeUsed;

    public RankingItem(String name, int correctCount, int timeUsed)
    {
        this.name = name;
        this.correctCount = correctCount;
        this.timeUsed = timeUsed;
    }
}
