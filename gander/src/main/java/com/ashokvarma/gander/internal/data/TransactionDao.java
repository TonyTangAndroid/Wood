package com.ashokvarma.gander.internal.data;

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

/**
 * Class description
 *
 * @author ashok
 * @version 1.0
 * @since 03/06/18
 */
@Dao
public abstract class TransactionDao {
    public static final int SEARCH_DEFAULT = Log.VERBOSE;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertTransaction(HttpTransaction httpTransaction);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract int updateTransaction(HttpTransaction httpTransactions);

    @Delete
    public abstract int deleteTransactions(HttpTransaction... httpTransactions);

    @Query("DELETE FROM HttpTransaction WHERE date < :beforeDate")
    public abstract int deleteTransactionsBefore(Date beforeDate);

    @Query("DELETE FROM HttpTransaction")
    public abstract int clearAll();

    @Query("SELECT * FROM HttpTransaction ORDER BY id DESC")
    public abstract DataSource.Factory<Integer, HttpTransaction> getAllTransactions();

    @Query("SELECT * FROM HttpTransaction WHERE id = :id")
    public abstract LiveData<HttpTransaction> getTransactionsWithId(long id);

    public DataSource.Factory<Integer, HttpTransaction> getAllTransactionsWith(String key, @IntRange(from = 2, to = 7) int priority) {
        String endWildCard = key + "%";
        String doubleSideWildCard = "%" + key + "%";
        return getAllTransactionsIncludeRequestResponse(endWildCard, doubleSideWildCard, priority);
    }

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT id,date,length,priority,body FROM HttpTransaction WHERE (tag LIKE :endWildCard OR body LIKE :doubleWildCard) AND priority >= :priority ORDER BY id DESC")
    abstract DataSource.Factory<Integer, HttpTransaction> getAllTransactionsIncludeRequestResponse(String endWildCard, String doubleWildCard, int priority);

}
