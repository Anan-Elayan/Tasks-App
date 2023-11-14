package com.example.myapplication.ui.done;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDoneBinding;
import com.example.myapplication.model.Tasks;
import com.example.myapplication.ui.addTasks.AddTasksFragment;
import com.example.myapplication.ui.home.HomeFragment;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;


/*
*
*
*   Name: Anan Elayan
*   ID: 1211529
*
*   in this class display all the object 'tasks' from sharedPreferences in list view but status is done only
*   and initialize the component and connect to the design using R file
*
* */

public class DoneFragment extends Fragment {

    private FragmentDoneBinding binding;
    private ListView listViewDone;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDoneBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listViewDone = root.findViewById(R.id.listViewDone);

        Gson gson = new Gson();
        String str = HomeFragment.sharedPreferences.getString(AddTasksFragment.DATA,"");
        if(str.toString()!=""){
            Tasks[] tasksArray = gson.fromJson(str, Tasks[].class);
            ArrayList<Tasks> tasksList = new ArrayList<>(Arrays.asList(tasksArray));
            ArrayList<Tasks> completTasks = new ArrayList<>();
            for (int i = 0; i < tasksList.size(); i++) {
                if(tasksList.get(i).isDone()) {
                    completTasks.add(tasksList.get(i));
                }
            }
            ArrayAdapter<Tasks> adapter = new ArrayAdapter<Tasks>(requireContext(),android.R.layout.test_list_item,(ArrayList<Tasks>) completTasks);
            listViewDone.setAdapter(adapter);
        }
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}