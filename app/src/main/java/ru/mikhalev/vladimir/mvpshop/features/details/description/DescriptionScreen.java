package ru.mikhalev.vladimir.mvpshop.features.details.description;

import android.os.Bundle;

import dagger.Provides;
import mortar.MortarScope;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.BasePresenter;
import ru.mikhalev.vladimir.mvpshop.core.BaseScreen;
import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.di.scopes.DaggerScope;
import ru.mikhalev.vladimir.mvpshop.features.catalog.CatalogModel;
import ru.mikhalev.vladimir.mvpshop.features.catalog.product.IProductPresenter;
import ru.mikhalev.vladimir.mvpshop.features.details.DetailsScreen;
import ru.mikhalev.vladimir.mvpshop.features.details.comments.CommentsScreen;
import ru.mikhalev.vladimir.mvpshop.flow.Screen;
import rx.Subscription;

/**
 * Developer Vladimir Mikhalev, 14.01.2017.
 */

@Screen(R.layout.screen_description)
public class DescriptionScreen extends BaseScreen<DetailsScreen.Component> {

    private final String mProductId;

    public DescriptionScreen(String id) {
        mProductId = id;
    }

    @Override
    public Object createScreenComponent(DetailsScreen.Component parentComponent) {
        return DaggerDescriptionScreen_Component.builder()
                .component(parentComponent)
                .module(new Module())
                .build();
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

    public class DescriptionPresenter extends BasePresenter<DescriptionView, CatalogModel> implements IProductPresenter {

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
            mCompSubs.add(subscribeOnProductObs(mProductId));
        }

        private Subscription subscribeOnProductObs(String productId) {
            return mModel.getProductObs(productId)
                    .subscribe(productRealm -> getView().setProduct(productRealm));
        }

        //region =============== IDescriptionPresenter ==============

        @Override
        public void saveProduct(ProductRealm productRealm) {
            mModel.saveProduct(productRealm);
        }

        //endregion
    }
}
