package com.carles.teamworktechtest.tasks.ui;

import android.util.Log;

import com.carles.teamworktechtest.common.ui.BasePresenter;
import com.carles.teamworktechtest.tasks.model.Task;
import com.carles.teamworktechtest.tasks.datasource.TaskRepository;

import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ShowTasksPresenter extends BasePresenter<ShowTasksView> {

    private static final String TAG = ShowTasksPresenter.class.getSimpleName();

    private final String projectId;
    private final TaskRepository repository;

    public ShowTasksPresenter(TaskRepository repository, String projectId) {
        this.repository = repository;
        this.projectId = projectId;
    }

    @Override
    public void onViewCreated(ShowTasksView view) {
        super.onViewCreated(view);
        getTasks();
    }

    private void getTasks() {
        view.showLoading();
        addDisposable(repository.getTasksByProject(projectId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                this::onGetTasksSuccess, this::onGetTasksError));
    }

    private void onGetTasksSuccess(List<Task> tasks) {
        if (tasks.isEmpty()) {
            view.showNoTasksMessage();
        } else {
            sortTasksByPosition(tasks);
            view.showTasks(tasks);
        }
    }

    private void sortTasksByPosition(List<Task> tasks) {
        Collections.sort(tasks, (task, other) -> {
            if (task.getOrder() == null) {
                return 1;
            } else if (other.getOrder() == null) {
                return -1;
            }
            return other.getOrder().compareTo(task.getOrder());
        });
    }

    private void onGetTasksError(Throwable error) {
        Log.e(TAG, "onGetTasksError:" + error.getMessage());
        view.showErrorAndRetry();
    }

    void onRefreshClicked() {
        getTasks();
    }

    void onAddTasksResult(int tasksAdded) {
        if (tasksAdded > 0) {
            view.showTasksAddedFeedback(tasksAdded);
        }
        getTasks();
    }

}