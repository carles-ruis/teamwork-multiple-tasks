package com.carles.teamworktechtest.tasks.ui;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.carles.teamworktechtest.R;
import com.carles.teamworktechtest.common.ui.BaseActivity;
import com.carles.teamworktechtest.common.ui.BasePresenter;
import com.carles.teamworktechtest.tasks.datasource.TaskCloudDatasource;
import com.carles.teamworktechtest.tasks.model.Task;
import com.carles.teamworktechtest.tasks.repository.TaskRepository;

import java.util.List;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ShowTasksActivity extends BaseActivity implements ShowTasksView {

    private static final String PROJECT_ID = "457090";
    private static final int REQUEST_CODE_ADD_TASKS = 100;
    public static final String EXTRA_TASKS_ADDED = "extra_tasks_added";

    private ShowTasksPresenter presenter;
    private TasksAdapter adapter;

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton addTasksButton;
    private TextView noTasksTextView;
    private View progressBar;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_show_tasks);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.showtasks_title);
        addTasksButton = findViewById(R.id.showtasks_add_tasks_button);
        addTasksButton.setOnClickListener(view ->
                startActivityForResult(AddTasksActivity.makeIntent(this, PROJECT_ID), REQUEST_CODE_ADD_TASKS));
        noTasksTextView = findViewById(R.id.showtasks_no_tasks_textview);
        progressBar = findViewById(R.id.progress_bar);
        coordinatorLayout = findViewById(R.id.showtasks_coordinator);

        adapter = new TasksAdapter();
        recyclerView = findViewById(R.id.showtasks_recyclerview);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initComponents() {
        TaskCloudDatasource cloudDatasource = new TaskCloudDatasource();
        TaskRepository repository = new TaskRepository(cloudDatasource);
        presenter = new ShowTasksPresenter(repository, PROJECT_ID);
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_ADD_TASKS && resultCode == RESULT_OK && data != null) {
            presenter.onAddTasksResult(data.getIntExtra(EXTRA_TASKS_ADDED, 0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_tasks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_show_tasks_refresh) {
            presenter.onRefreshClicked();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(VISIBLE);
        recyclerView.setVisibility(GONE);
        noTasksTextView.setVisibility(GONE);
    }

    @Override
    public void showErrorAndRetry() {
        progressBar.setVisibility(GONE);
        Snackbar.make(coordinatorLayout, R.string.showtasks_get_tasks_error, Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public void showNoTasksMessage() {
        progressBar.setVisibility(GONE);
        noTasksTextView.setVisibility(VISIBLE);
    }

    @Override
    public void showTasks(List<Task> tasks) {
        progressBar.setVisibility(GONE);
        recyclerView.setVisibility(VISIBLE);
        adapter.addItems(tasks);
    }

    @Override
    public void showTasksAddedFeedback(int tasksAdded) {
        Snackbar.make(coordinatorLayout, getResources().getQuantityString(R.plurals.showtasks_tasks_added, tasksAdded, tasksAdded), Snackbar.LENGTH_LONG).show();
    }
}
