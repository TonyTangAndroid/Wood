package wood;

import static junit.framework.Assert.assertEquals;

import android.app.Application;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import card.pokemon.PokemonItemMapper;
import com.google.common.truth.Truth;
import gson.AppGson;
import io.paging.demo.R;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import paging.model.Data;
import paging.model.Dto;
import paging.model.Item;
import paging.wrapper.app.config.AppConfig;
import paging.wrapper.app.config.AppContext;
import paging.wrapper.model.data.RoomItemMapper;

/** Created by husaynhakeem on 9/26/17. */
@RunWith(AndroidJUnit4.class)
public class PagingEntityDataBaseShould {

  public static final String ANY_POKEMON_NAME = "Moj";
  @Inject PagingItemDao dao;
  @Inject AppGson appGson;

  @Before
  public void setUp() {
    DaggerTestAppComponent.factory()
        .create(AppContext.create(AppConfig.DEFAULT_CONFIG, testApplication()))
        .inject(this);
    dao.deleteAll();
  }

  public static Application testApplication() {
    return (Application)
        InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
  }

  @Test
  public void returnZero_whenDatabaseIsEmpty() {
    int dbSize = dao.count();
    assertEquals(0, dbSize);
  }

  @Test
  public void returnCorrectSize_whenDatabaseIsFull() {
    dao.insertAll(Arrays.asList(anyPokemons()));
    int dbSize = dao.count();
    assertEquals(10, dbSize);
  }

  @Test
  public void transferToJson() {
    dao.insertAll(resources());
    List<Item> all = dao.listAll();
    assertEquals(151, all.size());

    Dto dto = Dto.create(Data.builder().itemList(all).total(all.size()).build());

    Truth.assertThat(appGson.get().toJson(dto)).isNotEmpty();
  }

  @After
  public void tearDown() {
    if (dao != null) {
      dao.deleteAll();
    }
  }

  private PagingEntity[] anyPokemons() {
    PagingEntity[] pagingEntities = new PagingEntity[10];
    for (int i = 0; i < 10; i++) {
      pagingEntities[i] = PokemonItemMapper.createPokemon(i, ANY_POKEMON_NAME);
    }
    return pagingEntities;
  }

  private List<PagingEntity> resources() {
    List<Item> list = new ArrayList<>();
    String[] name = testApplication().getResources().getStringArray(R.array.pokemon_names);
    int index = 0;
    for (String pokemonName : name) {
      Item item1 = PokemonItemMapper.asItem(String.valueOf(++index), pokemonName);
      list.add(item1);
    }
    return Observable.fromIterable(list).map(RoomItemMapper::map).toList().blockingGet();
  }
}
