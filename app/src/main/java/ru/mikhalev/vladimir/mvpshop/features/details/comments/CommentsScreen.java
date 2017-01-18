package ru.mikhalev.vladimir.mvpshop.features.details.comments;

import android.os.Bundle;

import dagger.Provides;
import io.realm.RealmList;
import mortar.MortarScope;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.BasePresenter;
import ru.mikhalev.vladimir.mvpshop.core.BaseScreen;
import ru.mikhalev.vladimir.mvpshop.data.storage.CommentRealm;
import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.di.scopes.DaggerScope;
import ru.mikhalev.vladimir.mvpshop.features.catalog.CatalogModel;
import ru.mikhalev.vladimir.mvpshop.features.details.DetailsScreen;
import ru.mikhalev.vladimir.mvpshop.flow.Screen;

/**
 * Developer Vladimir Mikhalev 08.01.2017
 */

@Screen(R.layout.screen_comments)
public class CommentsScreen extends BaseScreen<DetailsScreen.Component> {
    private final RealmList<CommentRealm> mCommentRealms;

    public CommentsScreen(ProductRealm productRealm) {
        mCommentRealms = productRealm.getCommentRealms();
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
            getView().initComments(mCommentRealms);
        }
    }
}
