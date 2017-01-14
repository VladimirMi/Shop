package ru.mikhalev.vladimir.mvpshop.features.details.description;

import android.os.Bundle;

import dagger.Provides;
import mortar.MortarScope;
import ru.mikhalev.vladimir.mvpshop.core.BasePresenter;
import ru.mikhalev.vladimir.mvpshop.core.BaseScreen;
import ru.mikhalev.vladimir.mvpshop.data.storage.Product;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.di.scopes.DaggerScope;
import ru.mikhalev.vladimir.mvpshop.features.catalog.product.ProductViewModel;
import ru.mikhalev.vladimir.mvpshop.features.details.DetailsModel;
import ru.mikhalev.vladimir.mvpshop.features.details.DetailsScreen;
import ru.mikhalev.vladimir.mvpshop.features.details.comments.CommentsScreen;

/**
 * Developer Vladimir Mikhalev, 14.01.2017.
 */

public class DescriptionScreen extends BaseScreen<DetailsScreen.Component> {

    private final Product mProduct;

    public DescriptionScreen(Product product) {
        mProduct = product;
    }

    @Override
    public Object createScreenComponent(DetailsScreen.Component parentComponent) {
        return null;
    }

    //region =============== Di ==============

    @dagger.Module
    public class Module {
        @Provides
        DescriptionPresenter provideDescriptionPresenter() {
            return new DescriptionPresenter();
        }
    }

    @dagger.Component(dependencies = DetailsScreen.Component.class, modules = DescriptionScreen.Module.class)
    @DaggerScope(CommentsScreen.class)
    public interface Component {
        void inject(DescriptionPresenter descriptionPresenter);

        void inject(DescriptionView descriptionView);
    }

    //endregion

    public class DescriptionPresenter extends BasePresenter<DescriptionView, DetailsModel> {

        @Override
        protected void initDagger(MortarScope scope) {
            scope.<DescriptionScreen.Component>getService(DaggerService.SERVICE_NAME).inject(this);
        }

        @Override
        protected void initActionBar() {
            // do nothing
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            getView().setViewModel(new ProductViewModel(mProduct));
        }
    }
}
