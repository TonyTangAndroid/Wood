package com.ashokvarma.gander.internal.data;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Class description
 *
 * @author ashok
 * @version 1.0
 * @since 03/06/18
 */
public class RoomTypeConverters {
    private static final String NAME_VALUE_SEPARATOR = "__:_:__";
    private static final String LIST_SEPARATOR = "__,_,__";

    @TypeConverter
    public static Date fromLongToDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long fromDateToLong(Date value) {
        return value == null ? null : value.getTime();
    }
}
