package com.tonytangandroid.wood;

import android.database.Cursor;
import android.os.AsyncTask;
import android.text.SpannableStringBuilder;

public class ReadAllAssyncTask extends AsyncTask<Void, Void, String> {
    private final LeafDao leafDao;
    private Callback<String> callback;

    public ReadAllAssyncTask(LeafDao leafDao) {
        this.leafDao = leafDao;
    }

    public void execute(Callback<String> callback){
        this.callback = callback;
        execute();
    }

    @Override
    protected String doInBackground(Void... v) {
        final SpannableStringBuilder sb = new SpannableStringBuilder();

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

                if (sb.length() > 2000000){
                    break;
                }
            }
        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(String content) {
        super.onPostExecute(content);
        if (callback != null) {
            callback.onEmit(content);
        }
    }
}
