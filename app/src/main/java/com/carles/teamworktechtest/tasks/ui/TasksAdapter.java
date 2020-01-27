package com.carles.teamworktechtest.tasks.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.carles.teamworktechtest.R;
import com.carles.teamworktechtest.tasks.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private List<Task> items = new ArrayList<>();

    @NonNull
    @Override
    public TasksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TasksAdapter.ViewHolder viewHolder, int position) {
        viewHolder.onBind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void addItems(List<Task> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView avatarImageView;
        private TextView contentTextView;
        private TextView statusTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.task_avatar_imageview);
            contentTextView = itemView.findViewById(R.id.task_name_textview);
            statusTextView = itemView.findViewById(R.id.task_description_textview);
        }

        void onBind(Task item) {
            Context context = itemView.getContext();
            Glide.with(context).load(item.getCreatorAvatarUrl()).into(avatarImageView);
            contentTextView.setText(TextUtils.isEmpty(item.getContent()) ? "" : item.getContent());
            statusTextView.setText(context.getString(R.string.task_status, item.getStatus()));
        }
    }
}
