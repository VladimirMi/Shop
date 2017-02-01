package ru.mikhalev.vladimir.mvpshop.features.details.comments;

import android.os.Bundle;

import dagger.Provides;
import mortar.MortarScope;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.BasePresenter;
import ru.mikhalev.vladimir.mvpshop.core.BaseScreen;
import ru.mikhalev.vladimir.mvpshop.data.storage.AccountRealm;
import ru.mikhalev.vladimir.mvpshop.data.storage.CommentRealm;
import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.di.scopes.DaggerScope;
import ru.mikhalev.vladimir.mvpshop.features.catalog.CatalogModel;
import ru.mikhalev.vladimir.mvpshop.features.details.DetailsScreen;
import ru.mikhalev.vladimir.mvpshop.flow.Screen;
import rx.Subscription;

/**
 * Developer Vladimir Mikhalev 08.01.2017
 */

@Screen(R.layout.screen_comments)
public class CommentsScreen extends BaseScreen<DetailsScreen.Component> {
    private final String mProductId;

    public CommentsScreen(String id) {
        mProductId = id;
    }

    @Override
    public Object createScreenComponent(DetailsScreen.Component parentComponent) {
        return DaggerCommentsScreen_Component.builder()
                .component(parentComponent)
                .module(new Module())
                .build();
    }

    //region =============== DI ==============

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(CommentsScreen.class)
        CommentsPresenter provideCommentsPresenter() {
            return new CommentsPresenter();
        }
    }

    @dagger.Component(dependencies = DetailsScreen.Component.class, modules = Module.class)
    @DaggerScope(CommentsScreen.class)
    public interface Component {

        void inject(CommentsPresenter commentsPresenter);

        void inject(CommentsView commentsView);
    }

    //endregion

    public class CommentsPresenter extends BasePresenter<CommentsView, CatalogModel> {

        private AccountRealm mAccountRealm;
        private ProductRealm mProductRealm;

        @Override
        protected void initDagger(MortarScope scope) {
            scope.<CommentsScreen.Component>getService(DaggerService.SERVICE_NAME).inject(this);
        }

        @Override
        protected void initActionBar() {
            //do nothing
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            mCompSubs.add(subscribeOnAccountObs());
            mCompSubs.add(subscribeOnCommentsObs(mProductId));
        }

        private Subscription subscribeOnAccountObs() {
            return mModel.getAccountObs()
                    .subscribe(accountRealm -> mAccountRealm = accountRealm);
        }

        private Subscription subscribeOnCommentsObs(String productId) {
            return mModel.getProductObs(productId)
                    .map(productRealm -> {
                        mProductRealm = productRealm;
                        return productRealm.getCommentRealms();
                    })
                    .subscribe(getView()::updateComments);
        }

        public void saveComment(float rating, String comment) {
            CommentRealm commentRealm = new CommentRealm(rating, comment, mAccountRealm);
            mProductRealm.getCommentRealms().add(commentRealm);
            mModel.saveProduct(mProductRealm);
        }
    }
}
