package com.carles.teamworktechtest.common.ui;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<V> {

    protected V view;
    protected CompositeDisposable disposables = new CompositeDisposable();

    public void onViewCreated(V view) {
        this.view = view;
    }

    public void onViewDestroyed() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
        view = null;
    }

    protected void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

}
