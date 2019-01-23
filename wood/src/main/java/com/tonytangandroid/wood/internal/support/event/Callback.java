package com.tonytangandroid.wood.internal.support.event;

public interface Callback<T> {
    void onEmit(T event);
}