package com.tonytangandroid.wood;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {Leaf.class}, version = 1, exportSchema = false)
@TypeConverters({RoomTypeConverters.class})
abstract class WoodDatabase extends RoomDatabase {
    private static WoodDatabase WOOD_DATABASE_INSTANCE;

    public static WoodDatabase getInstance(Context context) {
        if (WOOD_DATABASE_INSTANCE == null) {
            WOOD_DATABASE_INSTANCE = Room.databaseBuilder(context, WoodDatabase.class, "WoodDatabase").build();
        }
        return WOOD_DATABASE_INSTANCE;
    }

    public abstract LeafDao leafDao();
}