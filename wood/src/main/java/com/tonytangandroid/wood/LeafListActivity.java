package com.tonytangandroid.wood;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class LeafListActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.wood_activity_leaf_list);
    Toolbar toolbar = findViewById(R.id.wood_toolbar);
    setSupportActionBar(toolbar);
    toolbar.setSubtitle(getApplicationName());
  }

  private String getApplicationName() {
    ApplicationInfo applicationInfo = getApplicationInfo();
    int stringId = applicationInfo.labelRes;
    return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : getString(stringId);
  }
}
