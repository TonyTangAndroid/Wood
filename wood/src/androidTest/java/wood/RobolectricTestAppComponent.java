package wood;

import dagger.BindsInstance;
import dagger.Component;
import paging.wrapper.app.config.AppContext;
import paging.wrapper.di.app.AppModule;
import paging.wrapper.di.app.AppScope;

@AppScope
@Component(modules = {AppModule.class})
public interface RobolectricTestAppComponent {

  @Component.Factory
  interface Factory {

    RobolectricTestAppComponent create(@BindsInstance AppContext appContext);
  }
}
