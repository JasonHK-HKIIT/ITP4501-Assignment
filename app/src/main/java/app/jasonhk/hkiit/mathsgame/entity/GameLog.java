package app.jasonhk.hkiit.mathsgame.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "GameLog")
public class GameLog
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id")
    public int id;

    @ColumnInfo(name = "play_date")
    public int date;

    @ColumnInfo(name = "play_time")
    public int time;

    @ColumnInfo(name = "duration")
    public int duration;

    @ColumnInfo(name = "correct_count")
    public int correctCount;
}
