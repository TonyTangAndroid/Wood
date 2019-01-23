package com.tonytangandroid.wood;

interface Callback<T> {
    void onEmit(T event);
}