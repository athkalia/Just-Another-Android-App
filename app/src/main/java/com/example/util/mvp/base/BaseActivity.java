package com.example.util.mvp.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.example.tools.dagger.components.BaseActivityComponent;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.delegate.ActivityMvpDelegate;
import com.hannesdorfmann.mosby.mvp.delegate.ActivityMvpViewStateDelegateCallback;
import com.hannesdorfmann.mosby.mvp.delegate.ActivityMvpViewStateDelegateImpl;
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity;
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;

public abstract class BaseActivity<COMPONENT extends BaseActivityComponent, VIEW extends MvpView, PRESENTER extends MvpPresenter<VIEW>,
        VIEW_STATE extends ViewState<VIEW>> extends AppCompatActivity implements ActivityMvpViewStateDelegateCallback<VIEW, PRESENTER> {

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
     * Whether we want to retain state during configuration changes. Means that the same {@link PRESENTER} and {@link VIEW_STATE} is kept.
     */
    private boolean retainInstance;

    /**
     * Instead of extending {@link MvpActivity} or {@link MvpViewStateActivity} we are using a mosby's delegate. To do that we need to
     * propagate certain activity lifecycle methods to the delegate.
     */
    @Nullable protected ActivityMvpDelegate mvpDelegate;

    private boolean viewStateRestoreInProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        this.retainInstance = true;
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        COMPONENT component = constructComponent();
        component.inject(this);
        getMvpDelegate().onCreate(savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract COMPONENT constructComponent();

    // Delegate propagation ****************************************************************************************************************

    private ActivityMvpDelegate<VIEW, PRESENTER> getMvpDelegate() {

        if (mvpDelegate == null) {
            mvpDelegate = new ActivityMvpViewStateDelegateImpl<>(this);
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
    protected void onPostCreate(Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);
        getMvpDelegate().onPostCreate(savedInstanceState);
    }

    /**
     * Mosby stores 3 things in the custom non configuration instance ({@link AppCompatActivity#onRetainCustomNonConfigurationInstance()})
     * during a configuration change:
     * - The {@link PRESENTER}
     * - The {@link VIEW_STATE}
     * - A custom object defined by us via the {@link #onRetainNonMosbyCustomNonConfigurationInstance()}.
     * This method is simple delegation so that the mosby knows how to access the third object, the non mosby managed one.
     */
    @Override
    @Nullable
    public Object getNonMosbyLastCustomNonConfigurationInstance() {

        return getMvpDelegate().getNonMosbyLastCustomNonConfigurationInstance();
    }

    /**
     * Mosby stores 3 things in the custom non configuration instance ({@link AppCompatActivity#onRetainCustomNonConfigurationInstance()})
     * during a configuration change:
     * - The {@link PRESENTER}
     * - The {@link VIEW_STATE}
     * - A custom object defined by us via the {@link #onRetainNonMosbyCustomNonConfigurationInstance()}.
     * This method passes control to the delegate, which simply bundles all 3 objects into a wrapper object.
     **/
    @Override
    @Nullable
    public Object onRetainCustomNonConfigurationInstance() {

        return getMvpDelegate().onRetainCustomNonConfigurationInstance();
    }

    // MVP related *************************************************************************************************************************

    @Override
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

    @Override
    public boolean isRetainInstance() {

        return retainInstance;
    }

    @Override
    public void setRetainInstance(boolean retainInstance) {

        this.retainInstance = retainInstance;
    }

    @Override
    public boolean shouldInstanceBeRetained() {

        return retainInstance && isChangingConfigurations();
    }

    /**
     * Mosby stores 3 things in the custom non configuration instance ({@link AppCompatActivity#onRetainCustomNonConfigurationInstance()})
     * during a configuration change:
     * - The {@link PRESENTER}
     * - The {@link VIEW_STATE}
     * - A custom object defined by us with this method.
     **/
    @Override
    @Nullable
    public Object onRetainNonMosbyCustomNonConfigurationInstance() {

        // Default implementation doesn't save anything inside mosby. Override when required.
        return null;
    }

    // View state related ******************************************************************************************************************

    @Override
    public void setViewState(ViewState<VIEW> viewState) {

        this.viewState = (VIEW_STATE) viewState;
    }

    @Override
    public VIEW_STATE getViewState() {

        return viewState;
    }

    @Override
    public ViewState createViewState() {

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
