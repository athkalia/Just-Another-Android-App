package com.example.util.mvp.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.hannesdorfmann.mosby3.mvp.delegate.ActivityMvpDelegate;
import com.hannesdorfmann.mosby3.mvp.delegate.ActivityMvpViewStateDelegateImpl;
import com.hannesdorfmann.mosby3.mvp.delegate.MvpViewStateDelegateCallback;
import com.hannesdorfmann.mosby3.mvp.viewstate.MvpViewStateActivity;
import com.hannesdorfmann.mosby3.mvp.viewstate.ViewState;
import dagger.android.AndroidInjection;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;

public abstract class BaseActivity<
        VIEW extends MvpView,
        PRESENTER extends MvpPresenter<VIEW>,
        VIEW_STATE extends ViewState<VIEW>>
        extends AppCompatActivity implements MvpViewStateDelegateCallback<VIEW, PRESENTER, VIEW_STATE> {

    /**
     * Can't inject directly, as the presenter instantiation needs to happen by mosby in {@link this#createPresenter()}.
     */
    @Inject Provider<PRESENTER> presenterProvider;
    private PRESENTER presenter;

    /**
     * Can't inject directly, as the presenter instantiation needs to happen by mosby in {@link this#createViewState()}.
     */
    @Inject Provider<VIEW_STATE> viewStateProvider;
    private VIEW_STATE viewState;

    /**
     * Instead of extending {@link MvpActivity} or {@link MvpViewStateActivity} we are using a mosby's delegate. To do that we need to
     * propagate certain activity lifecycle methods to the delegate.
     */
    @Nullable protected ActivityMvpDelegate mvpDelegate;

    private boolean viewStateRestoreInProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        getMvpDelegate().onCreate(savedInstanceState);
    }

    protected abstract int getLayoutId();

    // Delegate propagation ****************************************************************************************************************

    private ActivityMvpDelegate<VIEW, PRESENTER> getMvpDelegate() {
        if (mvpDelegate == null) {
            mvpDelegate = new ActivityMvpViewStateDelegateImpl<>(this, this, true);
        }
        return mvpDelegate;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getMvpDelegate().onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getMvpDelegate().onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getMvpDelegate().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMvpDelegate().onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getMvpDelegate().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getMvpDelegate().onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getMvpDelegate().onRestart();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        getMvpDelegate().onContentChanged();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getMvpDelegate().onPostCreate(savedInstanceState);
    }

    // MVP related *************************************************************************************************************************

    @Override
    @SuppressWarnings("unchecked")
    public VIEW getMvpView() {
        return (VIEW) this;
    }

    @Override
    public PRESENTER createPresenter() {
        return presenterProvider.get();
    }

    @Override
    public PRESENTER getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(PRESENTER presenter) {
        this.presenter = presenter;
    }

    // View state related ******************************************************************************************************************

    @Override
    public void setViewState(VIEW_STATE viewState) {
        this.viewState = viewState;
    }

    @Override
    public VIEW_STATE getViewState() {
        return viewState;
    }

    @Override
    public VIEW_STATE createViewState() {
        return viewStateProvider.get();
    }

    @Override
    public void setRestoringViewState(boolean restoringViewState) {
        this.viewStateRestoreInProgress = restoringViewState;
    }

    @Override
    public boolean isRestoringViewState() {
        return viewStateRestoreInProgress;
    }

    /**
     * Called right after the state of the view has been restored from the {@link VIEW_STATE}.
     */
    @Override
    public void onViewStateInstanceRestored(boolean instanceStateRetained) {
        // Default not doing anything. Override when required.
    }

    @Override
    public void onNewViewStateInstance() {
        onFirstCreate();
    }

    /**
     * Default implementation not doing anything. Override when required to perform long running tasks only once, then save their state in
     * the {@link VIEW_STATE}
     */
    protected void onFirstCreate() {
        // Default implementation not doing anything. Override when required.
    }

}
