package app.jasonhk.hkiit.fifteentwenty.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import app.jasonhk.hkiit.fifteentwenty.dao.GameLogDao;
import app.jasonhk.hkiit.fifteentwenty.entity.GameLog;

@Database(entities = { GameLog.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase
{
    public abstract GameLogDao gameLogDao();
}
