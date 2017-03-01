package ru.mikhalev.vladimir.mvpshop.features.catalog.product;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.ChangeImageTransform;
import com.transitionseverywhere.Explode;
import com.transitionseverywhere.SidePropagation;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import flow.Flow;
import ru.mikhalev.vladimir.mvpshop.R;
import ru.mikhalev.vladimir.mvpshop.core.BaseView;
import ru.mikhalev.vladimir.mvpshop.data.storage.ProductRealm;
import ru.mikhalev.vladimir.mvpshop.databinding.ScreenProductBinding;
import ru.mikhalev.vladimir.mvpshop.di.DaggerService;
import ru.mikhalev.vladimir.mvpshop.features.details.DetailsScreen;
import ru.mikhalev.vladimir.mvpshop.utils.UIHelper;
import timber.log.Timber;


/**
 * Developer Vladimir Mikhalev 29.11.2016
 */
public class ProductView extends BaseView<ProductScreen.ProductPresenter> implements IProductView, IProductActions {
    private ScreenProductBinding mBinding;
    private ProductRealm mProductRealm;
    private ProductViewModel mViewModel;
    private AnimatorSet mResultSet;
    private ArrayList<View> mChildrenList;
    private boolean isZoomed;
    private int mInitImageHeight;

    public ProductView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<ProductScreen.Component>getDaggerComponent(context).inject(this);
    }

    @Override
    protected void initView() {
        mBinding = ScreenProductBinding.bind(this);
        mBinding.setActionsHandler(this);
    }

    //region ==================== Events ========================

    @Override
    public void clickOnPlus() {
        mProductRealm.inc();
        mPresenter.saveProduct(mProductRealm);
    }

    @Override
    public void clickOnMinus() {
        mProductRealm.dec();
        mPresenter.saveProduct(mProductRealm);
    }

    @Override
    public void clickOnShowMore() {
        Flow.get(this).set(new DetailsScreen(mProductRealm.getId()));
    }

    @Override
    public void clickOnFavorite() {
        mProductRealm.switchFavorite();
        mPresenter.saveProduct(mProductRealm);
    }

    @Override
    public void clickOnImage() {
        startZoomTransition();
    }


    //endregion

    //region ==================== IProductView ========================

    @Override
    public void setProduct(ProductRealm productRealm) {
        Timber.e("setProduct: ");
        mProductRealm = productRealm;
        if (mViewModel == null) {
            mViewModel = new ProductViewModel();
            mViewModel.update(productRealm);
            mBinding.setViewModel(mViewModel);
        } else {
            mViewModel.update(productRealm);
        }
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    //endregion

    //region =============== Animation ==============

    public void startAddToCartAnim() {
        final int cx = (mBinding.productWrapper.getRight() + mBinding.productWrapper.getLeft()) / 2;
        final int cy = (mBinding.productWrapper.getBottom() + mBinding.productWrapper.getTop()) / 2;
        final int radius = Math.max(mBinding.productWrapper.getWidth(), mBinding.productWrapper.getHeight());
        Animator hideCardAnim;
        Animator showCardAnim;
        Animator hideColorAnim = null;
        Animator showColorAnim = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            hideCardAnim = ViewAnimationUtils.createCircularReveal(mBinding.productWrapper, cx, cy, radius, 0);
            hideCardAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mBinding.productWrapper.setVisibility(INVISIBLE);
                }
            });
            showCardAnim = ViewAnimationUtils.createCircularReveal(mBinding.productWrapper, cx, cy, 0, radius);
            showCardAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    mBinding.productWrapper.setVisibility(VISIBLE);
                }
            });
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                ColorDrawable foreground = (ColorDrawable) mBinding.productWrapper.getForeground();
                hideColorAnim = ObjectAnimator.ofArgb(foreground, "color",
                        ContextCompat.getColor(getContext(), R.color.transparent),
                        ContextCompat.getColor(getContext(), R.color.color_accent));
                showColorAnim = ObjectAnimator.ofArgb(foreground, "color",
                        ContextCompat.getColor(getContext(), R.color.color_accent),
                        ContextCompat.getColor(getContext(), R.color.transparent));
            }
        } else {
            hideCardAnim = ObjectAnimator.ofFloat(mBinding.productWrapper, "alpha", 0);
            showCardAnim = ObjectAnimator.ofFloat(mBinding.productWrapper, "alpha", 1);
        }

        AnimatorSet hideSet = new AnimatorSet();
        AnimatorSet showSet = new AnimatorSet();
        hideSet.setDuration(600);
        hideSet.setInterpolator(new FastOutSlowInInterpolator());
        addAnimTogetherInSet(hideSet, hideCardAnim, hideColorAnim);

        showSet.setStartDelay(1000);
        showSet.setInterpolator(new FastOutSlowInInterpolator());
        addAnimTogetherInSet(showSet, showCardAnim, showColorAnim);

        if ((mResultSet != null && !mResultSet.isStarted()) || mResultSet == null) {
            mResultSet = new AnimatorSet();
            mResultSet.playSequentially(hideSet, showSet);
            mResultSet.start();
        }
    }

    private void addAnimTogetherInSet(AnimatorSet set, Animator... anims) {
        List<Animator> animatorList = new ArrayList<>();
        Collections.addAll(animatorList, anims);
        set.playTogether(animatorList);
    }

    private void startZoomTransition() {
        TransitionSet set = new TransitionSet();
        Transition explode = new Explode();
        final Rect epicenter = new Rect(mBinding.imageWrapper.getLeft(), mBinding.imageWrapper.getTop(),
                mBinding.imageWrapper.getRight(), mBinding.imageWrapper.getBottom());
        explode.setEpicenterCallback(new Transition.EpicenterCallback() {
            @Override
            public Rect onGetEpicenter(Transition transition) {
                return epicenter;
            }
        });
        SidePropagation propagation = new SidePropagation();
        propagation.setPropagationSpeed(3f);
        explode.setPropagation(propagation);

        ChangeBounds changeBounds = new ChangeBounds();
        ChangeImageTransform imageTransform = new ChangeImageTransform();
        if (!isZoomed) {
            changeBounds.setStartDelay(100);
            imageTransform.setStartDelay(100);
        }

        set.addTransition(explode)
                .addTransition(changeBounds)
                .addTransition(imageTransform)
                .setDuration(600)
                .setInterpolator(new FastOutSlowInInterpolator());

        TransitionManager.beginDelayedTransition(mBinding.productCard, set);

        changeLayoutForZoom();
    }

    private void changeLayoutForZoom() {
        ViewGroup.LayoutParams cardParam = mBinding.productCard.getLayoutParams();
        cardParam.height = !isZoomed ? ViewGroup.LayoutParams.MATCH_PARENT : ViewGroup.LayoutParams.WRAP_CONTENT;
        mBinding.productCard.setLayoutParams(cardParam);

        ViewGroup.LayoutParams imgWrapParam = mBinding.imageWrapper.getLayoutParams();
        imgWrapParam.height = !isZoomed ? ViewGroup.LayoutParams.MATCH_PARENT : ViewGroup.LayoutParams.WRAP_CONTENT;
        mBinding.imageWrapper.setLayoutParams(imgWrapParam);

        LayoutParams prodWrapParam = (LayoutParams) mBinding.productWrapper.getLayoutParams();
        int margin = !isZoomed ? 0 : (int) getResources().getDimension(R.dimen.font_normal_16);
        prodWrapParam.setMargins(margin, margin, margin, margin);
        mBinding.productWrapper.setLayoutParams(prodWrapParam);

        LayoutParams newImageParam;
        if (!isZoomed) {
            mInitImageHeight = mBinding.productImage.getHeight();
            newImageParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            mBinding.productImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            newImageParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    mInitImageHeight);
            mBinding.productImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        mBinding.productImage.setLayoutParams(newImageParam);

        if (mChildrenList == null) {
            mChildrenList = UIHelper.getChildrenExcludeView(mBinding.productWrapper, R.id.image_wrapper);
        }
        if (!isZoomed) {
            for (View view : mChildrenList) {
                view.setVisibility(GONE);
            }
        } else {
            for (View view : mChildrenList) {
                view.setVisibility(VISIBLE);
            }
        }
        isZoomed = !isZoomed;
    }

    //endregion
}
