package fh_ku.taskmaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import fh_ku.taskmaster.R;
import fh_ku.taskmaster.adapters.DividerItemDecorator;
import fh_ku.taskmaster.adapters.TagAdapter;
import fh_ku.taskmaster.adapters.TaskAdapter;
import fh_ku.taskmaster.repositories.DatabaseHelper;
import fh_ku.taskmaster.repositories.TagRepository;
import fh_ku.taskmaster.repositories.TaskRepository;

public class TaskListActivity extends AppCompatActivity {

    public static TaskAdapter taskAdapter;
    public static TagAdapter tagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView taskRV = (RecyclerView) findViewById(R.id.task_list);
        TaskRepository taskRepository = new TaskRepository(new DatabaseHelper(this));
        this.taskAdapter = new TaskAdapter(taskRepository);

        taskRV.addItemDecoration(new DividerItemDecorator(this, DividerItemDecorator.VERTICAL_LIST));
        taskRV.setAdapter(taskAdapter);
        taskRV.setLayoutManager(new LinearLayoutManager(this));

        taskAdapter.setOnUpdateTouchListener(new TaskAdapter.OnUpdateTouchListener() {
            @Override
            public void onUpdateTouch(int id) {
                Intent intent = new Intent(TaskListActivity.this, TaskDetailActivity.class);
                intent.putExtra("taskId", id);
                TaskListActivity.this.startActivity(intent);
            }
        });

        RecyclerView tagRV = (RecyclerView) findViewById(R.id.tag_list);
        TagRepository tagRepository = new TagRepository(new DatabaseHelper(this));
        this.tagAdapter = new TagAdapter(tagRepository);

        tagRV.addItemDecoration(new DividerItemDecorator(this,DividerItemDecorator.VERTICAL_LIST));
        tagRV.setAdapter(tagAdapter);
        tagRV.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskListActivity.this.startActivity(new Intent(TaskListActivity.this,TaskDetailActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskAdapter.init();
        tagAdapter.init();
    }
}
