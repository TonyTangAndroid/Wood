package wood;

import paging.wrapper.app.PagingWrapperApplication;
import paging.wrapper.di.thread.ThreadConfig;

public class EspressoApp extends PagingWrapperApplication {

  @Override
  public void onCreate() {
    super.onCreate();
  }

  @Override
  protected ThreadConfig threadConfig() {
    return ThreadConfig.create(false);
  }
}
