package com.carles.teamworktechtest.tasks.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.carles.teamworktechtest.R;
import com.carles.teamworktechtest.common.ui.BaseActivity;
import com.carles.teamworktechtest.common.ui.BasePresenter;
import com.carles.teamworktechtest.common.ui.OnTextChangeListener;
import com.carles.teamworktechtest.tasks.datasource.TaskCloudDatasource;
import com.carles.teamworktechtest.tasks.datasource.TaskRepository;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.carles.teamworktechtest.tasks.ui.ShowTasksActivity.EXTRA_TASKS_ADDED;

public class AddTasksActivity extends BaseActivity implements AddTasksView, AddTasksAdapter.Listener {

    private AddTasksPresenter presenter;
    private AddTasksAdapter adapter;
    private boolean isBackEnabled;

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private EditText newTaskEditText;
    private View clearTextButton;
    private View doneTextButton;
    private View progressBar;
    private MenuItem addTasksMenuItem;

    private static final String EXTRA_PROJECT_ID = "extra_project_id";

    public static Intent makeIntent(Context context, String projectId) {
        return new Intent(context, AddTasksActivity.class).putExtra(EXTRA_PROJECT_ID, projectId);
    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_add_tasks);
        isBackEnabled = true;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle(R.string.addtasks_title);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        clearTextButton = findViewById(R.id.addtasks_cleartext_button);
        clearTextButton.setVisibility(INVISIBLE);
        clearTextButton.setOnClickListener(view -> newTaskEditText.setText(""));

        doneTextButton = findViewById(R.id.addtasks_done_button);
        doneTextButton.setVisibility(INVISIBLE);
        doneTextButton.setOnClickListener(view -> {
            adapter.addItem(newTaskEditText.getText().toString());
            newTaskEditText.setText("");
        });

        newTaskEditText = findViewById(R.id.addtasks_newtask_edittext);
        newTaskEditText.addTextChangedListener(new OnTextChangeListener() {
            @Override
            public void onTextChanged(String text) {
                boolean hasText = !TextUtils.isEmpty(text);
                clearTextButton.setVisibility(hasText ? VISIBLE : INVISIBLE);
                doneTextButton.setVisibility(hasText ? VISIBLE : INVISIBLE);
                updateAddTasksIconVisibility();

            }
        });
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(GONE);

        adapter = new AddTasksAdapter(this);
        recyclerView = findViewById(R.id.addtasks_recyclerview);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initComponents() {
        TaskCloudDatasource cloudDatasource = new TaskCloudDatasource();
        TaskRepository repository = new TaskRepository(cloudDatasource);
        presenter = new AddTasksPresenter(repository, getIntent().getStringExtra(EXTRA_PROJECT_ID));
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    private static final String SAVED_STATE_RECYCLER_VIEW = "saved_state_recycler_view";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(SAVED_STATE_RECYCLER_VIEW, new ArrayList<>(adapter.getItems()));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            adapter.setItems(savedInstanceState.getStringArrayList(SAVED_STATE_RECYCLER_VIEW));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_tasks, menu);
        addTasksMenuItem = menu.findItem(R.id.menu_add_tasks_done);
        addTasksMenuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add_tasks_done) {
            presenter.onDonePressed(adapter.getItems());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!isBackEnabled) {
            return;
        }
        if (adapter.getItemCount() == 0) {
            setResult(RESULT_CANCELED);
            finish();
        } else {
            showLeaveConfirmationDialog();
        }
    }

    private void showLeaveConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.addtasks_cancel_dialog_text)
                .setPositiveButton(R.string.addtasks_cancel_dialog_leave, (dialogInterface, i) -> {
                    setResult(RESULT_CANCELED);
                    finish();
                })
                .setNegativeButton(R.string.addtasks_cancel_dialog_stay, null)
                .setCancelable(true)
                .show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(VISIBLE);
        isBackEnabled = false;
        addTasksMenuItem.setEnabled(false);
    }

    @Override
    public void showError() {
        progressBar.setVisibility(INVISIBLE);
        isBackEnabled = true;
        addTasksMenuItem.setEnabled(true);
        Snackbar.make(findViewById(android.R.id.content), R.string.addtasks_error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void navigateToShowTasks(Integer tasksAdded) {
        setResult(RESULT_OK, new Intent().putExtra(EXTRA_TASKS_ADDED, tasksAdded));
        finish();
    }

    @Override
    public void onTasksChanged() {
        updateAddTasksIconVisibility();
    }

    private void updateAddTasksIconVisibility() {
        if (addTasksMenuItem != null) {
            addTasksMenuItem.setVisible(adapter.getItemCount() > 0 && TextUtils.isEmpty(newTaskEditText.getText()));
        }
    }
}
