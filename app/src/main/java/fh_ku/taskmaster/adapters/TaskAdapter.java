package fh_ku.taskmaster.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fh_ku.taskmaster.R;
import fh_ku.taskmaster.models.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    public List<Task> tasks;

    private OnUpdateTouchListener onUpdateTouchListener;

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    public static interface OnUpdateTouchListener {
        public void onUpdateTouch(int id);
    }

    public void setOnUpdateTouchListener (OnUpdateTouchListener onEditTouchListener) {
        this.onUpdateTouchListener = onEditTouchListener;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView dueDate;
        public ImageButton updateTask;

        public TaskViewHolder(View itemView) {
            super(itemView);

            name    = (TextView) itemView.findViewById(R.id.task_name);
            dueDate = (TextView) itemView.findViewById(R.id.task_due_date);
            updateTask = (ImageButton) itemView.findViewById(R.id.task_update_button);
        }
    }

    public void setTasks(List<Task> tasks){
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
        notifyItemInserted(tasks.size() - 1);
    }

    public Task getTask(int id) {
        Task task = tasks.get(id);
        task.setId(id);
        return task;
    }

    public void updateTask(Task task) {
        tasks.set(task.getId(),task);
        notifyItemChanged(task.getId());
    }
    public void taskUpdate(View view){
        int i = view.getId();
    }

    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item,parent,false);
        return new TaskAdapter.TaskViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(TaskAdapter.TaskViewHolder viewHolder, final int position) {
        viewHolder.name.setText(tasks.get(position).getName());
        viewHolder.dueDate.setText(Task.formatDateTime(viewHolder.dueDate.getContext(),tasks.get(position).getDueDate()));
        viewHolder.updateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TaskAdapter.this.onUpdateTouchListener != null) {
                    TaskAdapter.this.onUpdateTouchListener.onUpdateTouch(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.tasks != null ? this.tasks.size() : 0;
    }
}
