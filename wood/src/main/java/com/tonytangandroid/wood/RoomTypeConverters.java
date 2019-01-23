package com.tonytangandroid.wood;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

class RoomTypeConverters {

    @TypeConverter
    public static Date fromLongToDate(Long value) {
        format();
        return value == null ? null : new Date(value);
    }

    private static void format() {
//        String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
//        String date = simpleDateFormat.format(new Date());
    }

    @TypeConverter
    public static Long fromDateToLong(Date value) {
        return value == null ? null : value.getTime();
    }
}
