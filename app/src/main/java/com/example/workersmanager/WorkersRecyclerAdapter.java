package com.example.workersmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workersmanager.models.WorkerModel;

import java.util.ArrayList;

public class WorkersRecyclerAdapter extends RecyclerView.Adapter<WorkersRecyclerAdapter.ViewHolder>{

    private LayoutInflater layoutInflater;
    private ArrayList<WorkerModel> workers;
    MainActivity.AdditionalOperations additionalOperations;

    WorkersRecyclerAdapter(Context context, ArrayList<WorkerModel> workers,
                           MainActivity.AdditionalOperations additionalOperations) {
        this.workers = workers;
        this.additionalOperations = additionalOperations;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.layout_worker_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkerModel worker = workers.get(position);

        holder.moreInfo.setVisibility(worker.visibility);

        holder.btnMore.setOnClickListener(btnItemClick);
        holder.btnMore.setTag(position);
        holder.btnEdit.setOnClickListener(btnItemClick);
        holder.btnEdit.setTag(position);
        holder.btnDelete.setOnClickListener(btnItemClick);
        holder.btnDelete.setTag(position);
    }

    View.OnClickListener btnItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            WorkerModel w = workers.get(((Integer)v.getTag()));
            switch (v.getId()){
                case R.id.btn_more:
                    if(w.visibility == View.VISIBLE) w.visibility = View.GONE;
                    else w.visibility = View.VISIBLE;
                    break;
                case R.id.btn_edit:
                    additionalOperations.updateWorker(w);
                    break;
                case R.id.btn_delete:
                    additionalOperations.deleteWorkerOperation(w.get_id());
                    break;
            }

            notifyDataSetChanged();
        }
    };

    @Override
    public int getItemCount() {
        return workers.size();
    }

    public void setWorkers(ArrayList<WorkerModel> workers) {
        this.workers = workers;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            final TextView firstName;
            final TextView lastName;
            final TextView position;
            final TextView age;
            final TextView gender;
            final TextView salary;
            final TextView data;
            final TextView info;
            final ConstraintLayout moreInfo;
            final Button btnMore;
            final Button btnEdit;
            final Button btnDelete;

        public ViewHolder(@NonNull View view) {
            super(view);
            firstName =  view.findViewById(R.id.tv_firstName);
            lastName = view.findViewById(R.id.tv_lastName);
            position = view.findViewById(R.id.tv_position);
            age = view.findViewById(R.id.tv_age);
            gender = view.findViewById(R.id.tv_gender);
            salary = view.findViewById(R.id.tv_salary);
            data = view.findViewById(R.id.tv_data);
            info = view.findViewById(R.id.tv_info);
            moreInfo = view.findViewById(R.id.more_info);
            btnMore = view.findViewById(R.id.btn_more);
            btnEdit = view.findViewById(R.id.btn_edit);
            btnDelete = view.findViewById(R.id.btn_delete);
        }
    }
}
