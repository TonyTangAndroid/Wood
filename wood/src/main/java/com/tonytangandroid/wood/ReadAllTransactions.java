package com.tonytangandroid.wood;

import android.database.Cursor;

public class ReadAllTransactions {
    private final LeafDao leafDao;
    public int maxSize = 100000;

    public ReadAllTransactions(LeafDao leafDao) {
        this.leafDao = leafDao;
    }

    public String load(){
        final StringBuilder sb = new StringBuilder();

        try (Cursor cursor = leafDao.getAllTransactions()) {
            Leaf leaf = new Leaf();
            if (cursor.isAfterLast()) return "No data";
            int cCreateAt = cursor.getColumnIndex("createAt");
            int cTag = cursor.getColumnIndex("tag");
            int cPriority = cursor.getColumnIndex("priority");
            int cBody = cursor.getColumnIndex("body");
            while (cursor.moveToNext()) {
                leaf.setCreateAt(cursor.getLong(cCreateAt));
                leaf.setTag(cursor.getString(cTag));
                leaf.setPriority(cursor.getInt(cPriority));
                leaf.setBody(cursor.getString(cBody));

                sb.append(FormatUtils.getShareTextFull(leaf));
                sb.append("\n");

                if (sb.length() > maxSize){
                    break;
                }
            }
        }

        return sb.toString();
    }
}
