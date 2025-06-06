package app.jasonhk.hkiit.fifteentwenty.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import app.jasonhk.hkiit.fifteentwenty.entity.GameSession;

@Dao
public interface GameSessionDao
{
    @Query("SELECT * FROM `GameSession` ORDER BY `timestamp` DESC")
    List<GameSession> getAll();

    @Insert
    void insertAll(GameSession... sessions);

    @Delete
    void delete(GameSession session);

    @Query("DELETE FROM `GameSession`")
    void deleteAll();
}
