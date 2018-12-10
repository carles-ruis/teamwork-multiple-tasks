package com.carles.teamworktechtest.tasks.ui;

import android.util.Log;

import com.carles.teamworktechtest.common.ui.BasePresenter;
import com.carles.teamworktechtest.tasks.repository.TaskRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddTasksPresenter extends BasePresenter<AddTasksView> {

    private static final String TAG = AddTasksPresenter.class.getSimpleName();

    private final String projectId;
    private final TaskRepository repository;

    public AddTasksPresenter(TaskRepository repository, String projectId) {
        this.repository = repository;
        this.projectId = projectId;
    }

    public void onDonePressed(List<String> tasks) {
        view.showLoading();
        addDisposable(repository.addMultipleTasks(projectId, tasks).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                this::onAddTasksSuccess, this::onAddTasksError));
    }

    private void onAddTasksSuccess(Integer tasksAdded) {
        view.navigateToShowTasks(tasksAdded);
    }

    private void onAddTasksError(Throwable throwable) {
        Log.e(TAG, "onAddTasksError:" + throwable.getMessage());
        view.showError();
    }
}
