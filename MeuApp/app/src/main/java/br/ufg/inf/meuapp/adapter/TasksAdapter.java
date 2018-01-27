package br.ufg.inf.meuapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.meuapp.R;
import br.ufg.inf.meuapp.model.Task;

public class TasksAdapter
        extends RecyclerView.Adapter<TaskViewHolder> {

    private final Context context;

    List<Task> taskList;

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public TasksAdapter(Context context){
        this.context = context;
        taskList = new ArrayList<>();
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());

        View contactView = inflater.inflate(R.layout.row_task,
                parent, false);

        // Return a new holder instance
        TaskViewHolder viewHolder = new TaskViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.labelName.setText(task.getName());
        holder.labelDescription.setText(task.getDescription());

        Picasso.with(context).load(task.getImage()).into(holder.imageTask);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
