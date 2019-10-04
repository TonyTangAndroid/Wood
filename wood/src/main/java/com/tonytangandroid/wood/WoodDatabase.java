package com.tonytangandroid.wood;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Leaf.class}, version = 1, exportSchema = false)
public abstract class WoodDatabase extends RoomDatabase {
    private static WoodDatabase WOOD_DATABASE_INSTANCE;

    public static WoodDatabase getInstance(Context context) {
        if (WOOD_DATABASE_INSTANCE == null) {
            WOOD_DATABASE_INSTANCE = Room.databaseBuilder(context, WoodDatabase.class, "WoodDatabase").build();
        }
        return WOOD_DATABASE_INSTANCE;
    }

    public abstract LeafDao leafDao();
}