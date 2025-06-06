package app.jasonhk.hkiit.fifteentwenty.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import app.jasonhk.hkiit.fifteentwenty.dao.GameSessionDao;
import app.jasonhk.hkiit.fifteentwenty.entity.GameSession;
import app.jasonhk.hkiit.fifteentwenty.room.TimestampConverter;

@Database(entities = { GameSession.class }, version = 1)
@TypeConverters({ TimestampConverter.class })
public abstract class SessionsDatabase extends RoomDatabase
{
    public static final String FILENAME = "sessions.db";

    public abstract GameSessionDao gameSessionDao();
}
