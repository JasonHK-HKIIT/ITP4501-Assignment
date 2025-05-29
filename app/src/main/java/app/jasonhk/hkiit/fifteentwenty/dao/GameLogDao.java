package app.jasonhk.hkiit.fifteentwenty.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import app.jasonhk.hkiit.fifteentwenty.entity.GameLog;

@Dao
public interface GameLogDao
{
    @Query("SELECT * FROM GameLog")
    List<GameLog> getAll();

    @Insert
    void insertAll(GameLog... logs);

    @Delete
    void delete(GameLog log);
}
