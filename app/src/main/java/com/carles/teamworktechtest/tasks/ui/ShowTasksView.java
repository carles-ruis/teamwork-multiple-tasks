package com.carles.teamworktechtest.tasks.ui;

import com.carles.teamworktechtest.tasks.model.Task;

import java.util.List;

public interface ShowTasksView {

    void showLoading();

    void showErrorAndRetry();

    void showNoTasksMessage();

    void showTasks(List<Task> tasks);

    void showTasksAddedFeedback(int tasksAdded);

}
