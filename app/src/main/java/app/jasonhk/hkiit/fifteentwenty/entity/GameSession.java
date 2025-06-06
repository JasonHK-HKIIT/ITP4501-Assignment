package app.jasonhk.hkiit.fifteentwenty.entity;

import java.time.LocalDateTime;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "GameSession")
public class GameSession
{
    public static final long NO_SESSION = -1;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "session_id")
    public long id;

    @ColumnInfo(name = "timestamp")
    public LocalDateTime timestamp;

    @ColumnInfo(name = "opponent")
    public String opponent;

    @ColumnInfo(name = "rounds")
    public int rounds;

    @ColumnInfo(name = "player_won")
    public boolean isPlayerWon;
}
