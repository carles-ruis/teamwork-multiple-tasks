package com.carles.teamworktechtest.tasks.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carles.teamworktechtest.R;

import java.util.ArrayList;
import java.util.List;

public class AddTasksAdapter extends RecyclerView.Adapter<AddTasksAdapter.ViewHolder> {

    private List<String> items = new ArrayList<>();
    private Listener listener;

    public AddTasksAdapter(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_add_task, viewGroup, false));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.onBind(items.get(position));
    }

    public void addItem(String item) {
        items.add(item);
        notifyItemInserted(getItemCount() - 1);
        listener.onTasksChanged();
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView contentTextView;
        private View deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contentTextView = itemView.findViewById(R.id.addtask_content_textview);
            deleteButton = itemView.findViewById(R.id.addtask_delete_button);
            deleteButton.setOnClickListener(view -> {
                int position = getAdapterPosition();
                items.remove(position);
                notifyItemRemoved(position);
                listener.onTasksChanged();
            });
        }

        void onBind(String content) {
            contentTextView.setText(content);
        }
    }

    interface Listener {
        void onTasksChanged();
    }
}
