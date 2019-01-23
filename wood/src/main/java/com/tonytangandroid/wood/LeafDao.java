package com.tonytangandroid.wood;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;
import android.support.annotation.IntRange;
import android.util.Log;

import java.util.Date;

@Dao
abstract class LeafDao {
    public static final int SEARCH_DEFAULT = Log.VERBOSE;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertTransaction(Leaf leaf);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract int updateTransaction(Leaf leafs);

    @Delete
    public abstract int deleteTransactions(Leaf... leaves);

    @Query("DELETE FROM Leaf WHERE date < :beforeDate")
    public abstract int deleteTransactionsBefore(Date beforeDate);

    @Query("DELETE FROM Leaf")
    public abstract int clearAll();

    @Query("SELECT * FROM Leaf ORDER BY id DESC")
    public abstract DataSource.Factory<Integer, Leaf> getAllTransactions();

    @Query("SELECT * FROM Leaf WHERE id = :id")
    public abstract LiveData<Leaf> getTransactionsWithId(long id);

    public DataSource.Factory<Integer, Leaf> getAllTransactionsWith(String key, @IntRange(from = 2, to = 7) int priority) {
        String endWildCard = key + "%";
        String doubleSideWildCard = "%" + key + "%";
        return getAllTransactionsIncludeRequestResponse(endWildCard, doubleSideWildCard, priority);
    }

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT id,date,length,priority,body FROM Leaf WHERE (tag LIKE :endWildCard OR body LIKE :doubleWildCard) AND priority >= :priority ORDER BY id DESC")
    abstract DataSource.Factory<Integer, Leaf> getAllTransactionsIncludeRequestResponse(String endWildCard, String doubleWildCard, int priority);

}
