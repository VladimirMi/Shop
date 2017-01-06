package ru.mikhalev.vladimir.mvpshop.features.details;

import android.support.annotation.NonNull;

import dagger.Provides;
import flow.TreeKey;
import mortar.MortarScope;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.BasePresenter;
import ru.mikhalev.vladimir.mvpshop.core.BaseScreen;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.di.scopes.DaggerScope;
import ru.mikhalev.vladimir.mvpshop.features.catalog.CatalogModel;
import ru.mikhalev.vladimir.mvpshop.features.catalog.CatalogScreen;
import ru.mikhalev.vladimir.mvpshop.features.catalog.product.IProductPresenter;
import ru.mikhalev.vladimir.mvpshop.features.catalog.product.ProductScreen;
import ru.mikhalev.vladimir.mvpshop.features.root.MenuItemHolder;
import ru.mikhalev.vladimir.mvpshop.flow.Screen;

/**
 * Developer Vladimir Mikhalev 23.12.2016
 */

@Screen(R.layout.screen_details)
public class DetailsScreen extends BaseScreen<CatalogScreen.Component> implements TreeKey {
    @Override
    public Object createScreenComponent(CatalogScreen.Component parentComponent) {
        return DaggerDetailsScreen_Component.builder()
                .component(parentComponent)
                .module(new Module())
                .build();
    }

    @NonNull
    @Override
    public Object getParentKey() {
        return new ProductScreen(null);
    }

    //region =============== DI ==============

    @dagger.Module
    public class Module {
        @Provides
        DetailsPresenter provideDetailsPresenter() {
            return new DetailsPresenter();
        }
    }

    @dagger.Component(dependencies = CatalogScreen.Component.class, modules = Module.class)
    @DaggerScope(DetailsScreen.class)
    public interface Component {

        void inject(DetailsView detailsView);

        void inject(DetailsPresenter detailsPresenter);
    }

    public class DetailsPresenter extends BasePresenter<DetailsView, CatalogModel> implements IProductPresenter {
        @Override
        protected void initDagger(MortarScope scope) {
            scope.<DetailsScreen.Component>getService(DaggerService.SERVICE_NAME).inject(this);
        }

        @Override
        protected void initActionBar() {
            mRootPresenter.newActionBarBuilder()
                    .setTitle("Product name")
                    .setBackArrow(true)
                    .addActoin(new MenuItemHolder("В корзину", R.drawable.ic_shopping_cart_color_primary_dark_24dp,
                            item -> {
                                getRootView().showMessage("Перейти в корзину");
                                return true;
                            }))
                    .setTab(getView().getViewPager());
        }

        @Override
        public void clickOnPlus() {

        }

        @Override
        public void clickOnMinus() {

        }

        @Override
        public void clickOnShowMore() {

        }

        @Override
        public void clickOnFavorite(boolean checked) {

        }
    }

    //endregion
}
