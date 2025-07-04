package app.jasonhk.hkiit.fifteentwenty.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import app.jasonhk.hkiit.fifteentwenty.entity.GameRecord;

@Dao
@SuppressWarnings("unused")
public interface GameRecordDao
{
    @Query("SELECT * FROM `GameRecord` ORDER BY `timestamp` DESC")
    List<GameRecord> getAll();

    @Insert
    void insertAll(GameRecord... sessions);

    @Delete
    void delete(GameRecord session);

    @Query("DELETE FROM `GameRecord`")
    void deleteAll();
}
