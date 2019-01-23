package com.tonytangandroid.gander.internal.support.event;

public interface Callback<T> {
    void onEmit(T event);
}