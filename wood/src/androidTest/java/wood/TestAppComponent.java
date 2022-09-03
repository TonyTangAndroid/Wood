package wood;

import dagger.BindsInstance;
import dagger.Component;
import paging.wrapper.app.config.AppContext;
import paging.wrapper.di.app.AppScope;
import paging.wrapper.di.app.FeatureModule;

@AppScope
@Component(modules = {FeatureModule.class})
public interface TestAppComponent {

  void inject(PagingEntityDataBaseShould pokemonDataBaseShould);

  @Component.Factory
  interface Factory {

    TestAppComponent create(@BindsInstance AppContext appContext);
  }
}
