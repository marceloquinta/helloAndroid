package br.ufg.inf.meuapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.ufg.inf.meuapp.R;

/**
 * Created by marceloquinta on 27/01/2018.
 */

public class TaskViewHolder extends RecyclerView.ViewHolder {

    TextView labelName;
    TextView labelDescription;
    ImageView imageTask;

    public TaskViewHolder(View itemView) {
        super(itemView);
        labelName = itemView.findViewById(R.id.label_name);
        labelDescription = itemView.findViewById(R.id.label_description);
        imageTask = itemView.findViewById(R.id.image_task);
    }
}
