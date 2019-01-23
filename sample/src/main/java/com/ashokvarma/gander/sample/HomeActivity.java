package com.tonytangandroid.gander.sample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.tonytangandroid.gander.Gander;

import timber.log.Timber;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById(R.id.do_http_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doHttpActivity();
            }
        });
        findViewById(R.id.launch_gander_directly).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchGanderDirectly();
            }
        });
        Gander.addAppShortcut(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sample_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_github:
                String url = "https://github.com/Ashok-Varma/Gander";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void launchGanderDirectly() {
        // Optionally launch Gander directly from your own app UI
        startActivity(Gander.getLaunchIntent(this));
    }

    private void doHttpActivity() {

        Timber.v("This is a verbose message.");
        Timber.d("This is an debug message ");
        Timber.i("Hello from Tysons");
        Timber.w("Warning. Your spaceship is on danger");
        Timber.e("Something is wrong.");
        logError();

    }

    private void logError() {
        try {
            String shortSrc = "a";
            String substring = shortSrc.substring(10);
            Timber.v(substring + substring + substring);
        } catch (Exception e) {
            Timber.e(e);
        }
    }
}