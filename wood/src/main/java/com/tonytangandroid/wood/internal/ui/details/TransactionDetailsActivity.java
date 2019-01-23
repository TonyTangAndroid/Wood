package com.tonytangandroid.wood.internal.ui.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.tonytangandroid.wood.R;
import com.tonytangandroid.wood.internal.support.WoodColorUtil;

public class TransactionDetailsActivity extends AppCompatActivity {

    private static final String ARG_TRANSACTION_ID = "arg_transaction_id";
    private static final String ARG_PRIORITY = "arg_priority";


    public static void start(Context context, long id, int priority) {
        Intent intent = new Intent(context, TransactionDetailsActivity.class);
        intent.putExtra(ARG_TRANSACTION_ID, id);
        intent.putExtra(ARG_PRIORITY, priority);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wood_act_transaction_details);
        long id = getIntent().getLongExtra(ARG_TRANSACTION_ID, 0);
        int priority = getIntent().getIntExtra(ARG_PRIORITY, Log.VERBOSE);
        WoodColorUtil colorUtil = WoodColorUtil.getInstance(this);
        AppBarLayout appBarLayout = findViewById(R.id.wood_details_appbar);
        appBarLayout.setBackgroundColor(colorUtil.getTransactionColor(priority));
        Toolbar toolbar = findViewById(R.id.wood_details_toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        bindFragment(TransactionPayloadFragment.newInstance(id));
    }

    private void bindFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_fragment_holder, fragment).commit();
    }
}
