package br.ufg.inf.meuapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import br.ufg.inf.meuapp.R;
import br.ufg.inf.meuapp.adapter.TasksAdapter;
import br.ufg.inf.meuapp.data.SessionHandler;
import br.ufg.inf.meuapp.model.Task;
import br.ufg.inf.meuapp.model.User;
import br.ufg.inf.meuapp.web.WebTaskTodo;

public class UserActivity extends AppCompatActivity {

    private User user;

    RecyclerView myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myList = findViewById(R.id.recycler_tasks);
        myList.setLayoutManager(new LinearLayoutManager(
                this,LinearLayoutManager.VERTICAL,
                false));
        myList.setAdapter(new TasksAdapter(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        SessionHandler sessionHandler = new SessionHandler();
        try {
            user = sessionHandler.getUser(this);
        } catch (RuntimeException x){
            sessionHandler.removeSession(this);
            Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(myIntent);
            return;
        }

        EventBus.getDefault().register(this);

        initializeScreenFields();

        WebTaskTodo myCall = new WebTaskTodo(this, user.getToken());
        myCall.execute();
    }

    private void initializeScreenFields() {
        getSupportActionBar().setTitle(
                getString(R.string.label_tasks,user.getUsername())
        );
    }


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void handleError(Error error){
        Log.d("",error.getMessage());
    }

    @Subscribe
    public void handleList(List<Task> taskList){
        ( (TasksAdapter) myList.getAdapter() ).setTaskList(taskList);
        myList.getAdapter().notifyDataSetChanged();
    }
}
