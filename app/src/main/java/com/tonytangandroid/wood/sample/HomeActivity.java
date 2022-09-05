package com.tonytangandroid.wood.sample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import com.tonytangandroid.wood.Wood;
import timber.log.Timber;

public class HomeActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    findViewById(R.id.btn_generate_log).setOnClickListener(view -> generateTimberLog());
    findViewById(R.id.btn_test_extreme_log).setOnClickListener(view -> keepGenerateLog());
    findViewById(R.id.launch_wood_directly).setOnClickListener(view -> launchWoodDirectly());
    Wood.addAppShortcut(this);
  }

  private void keepGenerateLog() {
    startActivity(new Intent(this, GeneratingLogActivity.class));
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
        String url = "https://github.com/TonyTangAndroid/Wood";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void launchWoodDirectly() {
    startActivity(Wood.getLaunchIntent(this));
  }

  private void generateTimberLog() {

    Timber.v("This is a VERBOSE message.");
    Timber.d("This is an DEBUG message.");
    Timber.i("This is an INFO message.");
    Timber.w("This is an WARNING message.");
    Timber.e("This is an ERROR message.");
    logError();
  }

  public static void logInBackground() {
    new Thread(() -> Timber.i("This is an INFO message triggered in background thread.")).start();
  }

  private void logError() {
    try {
      String shortSrc = "";
      String substring = shortSrc.substring(10);
      Timber.v(substring + substring + substring);
    } catch (Exception e) {
      Timber.e(e);
    }
  }
}
