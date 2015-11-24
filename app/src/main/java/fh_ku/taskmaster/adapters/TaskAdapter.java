package fh_ku.taskmaster.adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fh_ku.taskmaster.R;
import fh_ku.taskmaster.models.Task;
import fh_ku.taskmaster.repositories.TaskRepository;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

<<<<<<< HEAD
    public List<Task> tasks;

    private OnUpdateTouchListener onUpdateTouchListener;

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
=======
    private TaskRepository taskRepository;
    private Cursor cursor;
    private OnUpdateTouchListener onUpdateTouchListener;

    public TaskAdapter(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void init() {
        this.cursor = this.taskRepository.queryAllTasks();
>>>>>>> stefanhuber/master
    }

    public static interface OnUpdateTouchListener {
        public void onUpdateTouch(int id);
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView dueDate;
        public CheckBox checkbox;

        public ImageButton updateTask;
        public CheckBox closed;

        public TaskViewHolder(View itemView) {
            super(itemView);

            name    = (TextView) itemView.findViewById(R.id.task_name);
            dueDate = (TextView) itemView.findViewById(R.id.task_due_date);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
            updateTask = (ImageButton) itemView.findViewById(R.id.task_update_button);
            closed = (CheckBox) itemView.findViewById(R.id.task_closed);
        }
    }

<<<<<<< HEAD
    public void setTasks(List<Task> tasks){
        this.tasks = tasks;
    }
    /*
    public void addTask(Task task) {
        tasks.add(task);
        notifyItemInserted(tasks.size() - 1);
    }

    public Task getTask(int id) {
        Task task = tasks.get(id);
        task.setId(id);
        return task;
=======
    public void setOnUpdateTouchListener (OnUpdateTouchListener onEditTouchListener) {
        this.onUpdateTouchListener = onEditTouchListener;
>>>>>>> stefanhuber/master
    }

    public Task getTaskAtPosition(int position) {
        return this.taskRepository.taskAtCursorPosition(this.cursor,position);
    }
    public void taskUpdate(View view){
        int i = view.getId();
    }
    */
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item,parent,false);
        return new TaskAdapter.TaskViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(TaskAdapter.TaskViewHolder viewHolder, final int position) {
<<<<<<< HEAD
        viewHolder.name.setText(tasks.get(position).getName());
        viewHolder.dueDate.setText(Task.formatDateTime(viewHolder.dueDate.getContext(), tasks.get(position).getDueDate()));
=======
        final Task task = getTaskAtPosition(position);

        viewHolder.name.setText(task.getName());
        viewHolder.dueDate.setText(Task.formatDateTime(viewHolder.dueDate.getContext(), task.getDueDate()));
        viewHolder.closed.setChecked(task.isClosed());

>>>>>>> stefanhuber/master
        viewHolder.updateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TaskAdapter.this.onUpdateTouchListener != null) {
                    TaskAdapter.this.onUpdateTouchListener.onUpdateTouch(task.getId());
                }
            }
        });

        viewHolder.closed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TASK ADAPTER", "Task closed is clicked");
                CheckBox cbx = (CheckBox) v;
                task.setClosed(cbx.isChecked());
                TaskAdapter.this.taskRepository.updateTask(task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.cursor != null ? this.cursor.getCount() : 0;
    }
}
