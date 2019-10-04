package com.tonytangandroid.wood.sample;

import com.google.common.truth.Truth;

import org.junit.Test;

public class AppTest {

    @Test
    public void onCreate() {

        Truth.assertThat(1 + 1).isEqualTo(2);
    }
}