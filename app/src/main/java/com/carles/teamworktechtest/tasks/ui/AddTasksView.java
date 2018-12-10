package com.carles.teamworktechtest.tasks.ui;

public interface AddTasksView {

    void showLoading();

    void showError();

    void navigateToShowTasks(Integer tasksAdded);

}
