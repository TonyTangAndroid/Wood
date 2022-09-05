package com.tonytangandroid.wood.sample

import android.app.Application
import android.util.Log
import com.tonytangandroid.wood.WoodTree
import timber.log.Timber

object WoodIntegrationUtil {

  @JvmStatic
  fun initWood(application: Application) {
    Timber.plant(
      WoodTree(application,"tony")
        .retainDataFor(WoodTree.Period.FOREVER)
        .logLevel(Log.VERBOSE)
        .autoScroll(false)
        .maxLength(100000)
        .showNotification(true)
    )
  }
}