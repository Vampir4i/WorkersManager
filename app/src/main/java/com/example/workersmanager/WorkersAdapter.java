package com.example.workersmanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.example.workersmanager.models.WorkerModel;
import java.util.ArrayList;

public class WorkersAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<WorkerModel> workers;
    MainActivity.AdditionalOperations additionalOperations;

    WorkersAdapter(Context context, ArrayList<WorkerModel> workers,
                   MainActivity.AdditionalOperations additionalOperations) {
        this.context = context;
        this.workers = workers;
        this.additionalOperations = additionalOperations;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return workers.size();
    }

    @Override
    public Object getItem(int position) {
        return workers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.layout_worker_item, parent, false);
        }
        WorkerModel p = getWorker(position);

        ((TextView) view.findViewById(R.id.tv_firstName)).setText(p.getFirstName());
        ((TextView) view.findViewById(R.id.tv_lastName)).setText(p.getLastName());
        ConstraintLayout moreInfo = view.findViewById(R.id.more_info);
        moreInfo.setVisibility(p.visibility);
        if(p.visibility == View.VISIBLE){
            ((TextView) view.findViewById(R.id.tv_position)).setText(p.getPosition());
            ((TextView) view.findViewById(R.id.tv_age)).setText("" + p.getAge());
            ((TextView) view.findViewById(R.id.tv_gender)).setText(p.getGender());
            ((TextView) view.findViewById(R.id.tv_salary)).setText("" + p.getSalary());
            ((TextView) view.findViewById(R.id.tv_data)).setText(p.getData());
            ((TextView) view.findViewById(R.id.tv_info)).setText(p.getInfo());
        }

        Button btnMore = view.findViewById(R.id.btn_more);
        btnMore.setOnClickListener(btnItemClick);
        btnMore.setTag(position);
        Button btnEdit = view.findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(btnItemClick);
        btnEdit.setTag(position);
        Button btnDelete = view.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(btnItemClick);
        btnDelete.setTag(position);

        return view;
    }

    View.OnClickListener btnItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            WorkerModel w = getWorker((Integer)v.getTag());
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

    WorkerModel getWorker(int position) {
        return ((WorkerModel) getItem(position));
    }
}
