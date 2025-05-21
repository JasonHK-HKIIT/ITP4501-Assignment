package app.jasonhk.hkiit.mathsgame.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import app.jasonhk.hkiit.mathsgame.dao.GameLogDao;
import app.jasonhk.hkiit.mathsgame.entity.GameLog;

@Database(entities = { GameLog.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase
{
    public abstract GameLogDao gameLogDao();
}
