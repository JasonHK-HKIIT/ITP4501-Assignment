package app.jasonhk.hkiit.fifteentwenty.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import app.jasonhk.hkiit.fifteentwenty.dao.GameRecordDao;
import app.jasonhk.hkiit.fifteentwenty.entity.GameRecord;
import app.jasonhk.hkiit.fifteentwenty.room.TimestampConverter;

@Database(entities = { GameRecord.class }, version = 1)
@TypeConverters({ TimestampConverter.class })
public abstract class RecordsDatabase extends RoomDatabase
{
    public static final String FILENAME = "records.db";

    public abstract GameRecordDao gameRecordDao();
}
