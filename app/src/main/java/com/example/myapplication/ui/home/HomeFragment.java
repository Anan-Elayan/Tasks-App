package com.example.myapplication.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.model.Tasks;
import com.example.myapplication.model.TasksAdaptor;
import com.example.myapplication.ui.addTasks.AddTasksFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

/*
*
*
*   Name: Anan Elayan
*   ID: 1211529
*
*   in this class display all the object 'tasks' from sharedPreferences in list view
*   and the user can select any object from list view to change status as a Done and move this object to Done Screen.
*   and initialize the component and connect to the design using R file
*
*
*
* */
public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ListView listView;
    private CheckBox checkBox;

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = root.findViewById(R.id.listView);
        checkBox = root.findViewById(R.id.checkBox1);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        editor = sharedPreferences.edit();

        int number_objects = sharedPreferences.getInt(AddTasksFragment.NUMBER_OBJECT, 0);
        if (number_objects == 0) {
            HomeFragment.editor.putInt(AddTasksFragment.NUMBER_OBJECT, 1);
            HomeFragment.editor.commit();
        }

        Gson gson = new Gson();
        String str = sharedPreferences.getString(AddTasksFragment.DATA, "");
        if (str.toString() != "") {
            Tasks[] tasksArray = gson.fromJson(str, Tasks[].class);
            ArrayList<Tasks> tasksList = new ArrayList<>(Arrays.asList(tasksArray));
            int i = tasksList.size()-1;
            while (!tasksList.isEmpty() && i >=0) {
                System.out.println(tasksList.get(i).toString());
                if (tasksList.get(i).isDone()) {
                    tasksList.remove(i);
                }
                i--;

            }
            // set object to list view and show list
            TasksAdaptor tasksAdaptor = new TasksAdaptor(requireContext(), R.layout.list_row, (ArrayList<Tasks>) tasksList);
            listView.setAdapter(tasksAdaptor);
        }

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}