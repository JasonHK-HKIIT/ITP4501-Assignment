package app.jasonhk.hkiit.fifteentwenty.room;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import androidx.room.TypeConverter;

public class TimestampConverter
{
    @TypeConverter
    public static LocalDateTime fromTimestamp(long timestamp)
    {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneOffset.systemDefault());
    }

    @TypeConverter
    public static long toTimestamp(LocalDateTime time)
    {
        return time.toInstant(ZoneOffset.systemDefault().getRules().getOffset(time)).getEpochSecond();
    }
}
