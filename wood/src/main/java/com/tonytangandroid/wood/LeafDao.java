package com.tonytangandroid.wood;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;
import androidx.annotation.IntRange;
import android.util.Log;

import java.util.List;

@Dao
abstract class LeafDao {
    public static final int SEARCH_DEFAULT = Log.VERBOSE;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertTransaction(Leaf leaf);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract int updateTransaction(Leaf leafs);

    @Delete
    public abstract int deleteTransactions(Leaf... leaves);

    @Query("DELETE FROM Leaf WHERE createAt < :beforeDate")
    public abstract int deleteTransactionsBefore(long beforeDate);

    @Query("DELETE FROM Leaf")
    public abstract int clearAll();

    @Query("SELECT * FROM Leaf ORDER BY id DESC")
    public abstract DataSource.Factory<Integer, Leaf> getPagedTransactions();

    @Query("SELECT * FROM Leaf ORDER BY id DESC")
    public abstract List<Leaf> getAllTransactions();

    @Query("SELECT * FROM Leaf WHERE id = :id")
    public abstract LiveData<Leaf> getTransactionsWithId(long id);

    public DataSource.Factory<Integer, Leaf> getAllTransactionsWith(String key, @IntRange(from = 2, to = 7) int priority) {
        String endWildCard = key + "%";
        String doubleSideWildCard = "%" + key + "%";
        return getAllTransactionsIncludeRequestResponse(endWildCard, doubleSideWildCard, priority);
    }

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT id,createAt,length,priority,body FROM Leaf WHERE (tag LIKE :endWildCard OR body LIKE :doubleWildCard) AND priority >= :priority ORDER BY id DESC")
    abstract DataSource.Factory<Integer, Leaf> getAllTransactionsIncludeRequestResponse(String endWildCard, String doubleWildCard, int priority);

}
