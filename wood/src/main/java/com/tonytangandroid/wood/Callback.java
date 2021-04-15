package com.tonytangandroid.wood;

public interface Callback<T> {
    void onEmit(T event);
}