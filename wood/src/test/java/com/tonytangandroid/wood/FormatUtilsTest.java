package com.tonytangandroid.wood;

import static com.google.common.truth.Truth.assertThat;

import java.util.Collections;
import org.junit.Test;

public class FormatUtilsTest {

  @Test
  public void onHandleIntent() {
    assertThat(FormatUtils.indexOf("abcd", "c")).isEqualTo(Collections.singletonList(2));
  }
}
