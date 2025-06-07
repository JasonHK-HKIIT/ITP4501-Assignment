package app.jasonhk.hkiit.fifteentwenty.entity;

import java.time.LocalDateTime;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "GameRecord")
public class GameRecord
{
    @PrimaryKey()
    @ColumnInfo(name = "timestamp")
    public LocalDateTime timestamp;

    @ColumnInfo(name = "opponent")
    public String opponent;

    @ColumnInfo(name = "rounds")
    public int rounds;

    @ColumnInfo(name = "player_won")
    public boolean isPlayerWon;
}
