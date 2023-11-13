package com.example.myapplication.model;
import static com.example.myapplication.ui.addTasks.AddTasksFragment.DATA;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.myapplication.R;
import com.example.myapplication.ui.home.HomeFragment;
import com.google.gson.Gson;
import java.util.ArrayList;


/*
*
*  Name : Anan Elayna
*  ID : 1211529
*
*  In this class create custom adaptor to set this in list view of type array list
*  and change the status of objects to done when clicked on checkBox and remove this from list.
*
*
* */


public class TasksAdaptor extends ArrayAdapter<Tasks>  {

    private Context mContext;
    private int mResource;

    public TasksAdaptor(@NonNull Context context, int resource, @NonNull ArrayList<Tasks> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);
        final Tasks item = getItem(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(mResource, parent, false);
        }
        TextView txtTitle1 = convertView.findViewById(R.id.txtTitle1);
        txtTitle1.setText(item.getTaskTitle());

        TextView  txtDate1 = convertView.findViewById(R.id.txtDate1);
        txtDate1.setText(item.getTime());

        CheckBox checkBox1 = convertView.findViewById(R.id.checkBox1);

        checkBox1.setTag(item);
        Tasks selectedTask = (Tasks) checkBox1.getTag();
        checkBox1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Gson gson = new Gson();
            String str = HomeFragment.sharedPreferences.getString(DATA,"");
            Tasks[] tasksArray = gson.fromJson(str, Tasks[].class);
            for (int i = 0; i < tasksArray.length; i++) {
                if(tasksArray[i].getTasksId()==selectedTask.getTasksId()){
                    tasksArray[i].setDone(true);
                    checkBox1.setChecked(true);
                    break;
                }
            }
            remove(selectedTask);
            String updatedJson = gson.toJson(tasksArray);
            HomeFragment.editor.putString(DATA, updatedJson);
            HomeFragment.editor.commit();
            notifyDataSetChanged();
        });
        return convertView;
    }
}
