package com.example.myapplication.ui.addTasks;

import static android.content.Intent.getIntent;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.DatePickerDialog;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentAddtasksBinding;
import com.example.myapplication.databinding.FragmentDoneBinding;
import com.example.myapplication.model.Tasks;
import com.example.myapplication.ui.home.HomeFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;


/*
*
*   Name: Anan Elayan
*   ID: 1211529
*
*   in this class i want create new object of Tasks when clicked on button save .
*   and write this object to the editor ( sharedPreferences )
*   and defined ad initialize the date picker and time picker.
*   connect components with design XML file using R file
*
* */
public class AddTasksFragment extends Fragment {

    private FragmentAddtasksBinding binding;
    private FragmentDoneBinding bindingEdit;
    private Button btnSave;
    private Button btnTime;
    private Button btnDate;
    private TextInputEditText textInputEditTextTitle;
    private TextInputEditText textInputEditTextDate;
    private TextInputEditText textInputEditTextTime;
    private TextInputEditText textInputEditTextData;
    private TextInputLayout txtDate;
    public static ArrayList<Tasks> arrayList;
    public static final String DATA = "DATA";
    public static final String NUMBER_OBJECT = "NUMBER_OBJECT";
    private int hour, minutes;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        binding = FragmentAddtasksBinding.inflate(inflater, container, false);
       // bindingEdit = FragmentDoneBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
       // View root2 = bindingEdit.getRoot();
        arrayList = new ArrayList<>();
        btnSave = root.findViewById(R.id.btnSave);
        btnDate = root.findViewById(R.id.btndate);
        btnTime = root.findViewById(R.id.btnTime);
        textInputEditTextTitle = root.findViewById(R.id.textInputEditTextTitle);
        textInputEditTextDate = root.findViewById(R.id.textInputEditTextDate);
        textInputEditTextTime = root.findViewById(R.id.textInputEditTextTime);
        textInputEditTextData = root.findViewById(R.id.textInputEditTextData);
        txtDate = root.findViewById(R.id.txtDate1);
        textInputEditTextTime.setEnabled(false);
        textInputEditTextDate.setEnabled(false);

        HomeFragment.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        HomeFragment.editor = HomeFragment.sharedPreferences.edit();

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            String amPm;
                            if (selectedHour >= 12) {
                                amPm = "PM";
                                selectedHour -= 12;
                            } else {
                                amPm = "AM";
                            }
                            // Handle midnight (12:00 AM) and noon (12:00 PM)
                            if (selectedHour == 0) {
                                selectedHour = 12;
                            }
                            hour = selectedHour;
                            minutes = selectedMinute;
                            textInputEditTextTime.setText(String.format(Locale.getDefault(), "%02d:%02d %s", hour, minutes, amPm));
                        }
                    };
                    TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), AlertDialog.THEME_DEVICE_DEFAULT_DARK, onTimeSetListener, hour, minutes, false);
                    timePickerDialog.setTitle("Select Time");
                    timePickerDialog.show();
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requireContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Month is zero-based, so we add 1 to it
                                monthOfYear += 1;
                                // Display selected date in the TextInputEditText
                                textInputEditTextDate.setText(String.format(Locale.getDefault(), "%02d/%02d/%d", monthOfYear, dayOfMonth, year));
                            }
                        },
                        year, // Initial year
                        month, // Initial month
                        day // Initial day
                );
                // Show the DatePickerDialog
                datePickerDialog.show();
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textInputEditTextTitle.getText().toString().isEmpty()){
                    Toast.makeText(requireContext(),"Title is Empty",Toast.LENGTH_SHORT).show();
                } else if (textInputEditTextData.getText().toString().isEmpty()) {
                    Toast.makeText(requireContext(),"Data is Empty",Toast.LENGTH_SHORT).show();
                } else if (textInputEditTextDate.getText().toString().isEmpty()) {
                    Toast.makeText(requireContext(),"Date is Empty",Toast.LENGTH_SHORT).show();
                } else if (textInputEditTextTime.getText().toString().isEmpty()) {
                    Toast.makeText(requireContext(),"Time is Empty",Toast.LENGTH_SHORT).show();
                } else if (textInputEditTextTitle.getText().toString().isEmpty() && textInputEditTextData.getText().toString().isEmpty() && textInputEditTextDate.getText().toString().isEmpty() &&  textInputEditTextTime.getText().toString().isEmpty()) {
                    Toast.makeText(requireContext(),"Data is Empty",Toast.LENGTH_SHORT).show();
                } else {
                    int number_objects = HomeFragment.sharedPreferences.getInt(AddTasksFragment.NUMBER_OBJECT, 0);
                    Tasks newTask = new Tasks(
                            number_objects,
                            textInputEditTextTitle.getText().toString(),
                            textInputEditTextData.getText().toString(),
                            textInputEditTextTime.getText().toString(),
                            textInputEditTextDate.getText().toString());
                    number_objects++;
                    HomeFragment.editor.putInt(AddTasksFragment.NUMBER_OBJECT, number_objects);
                    HomeFragment.editor.commit();

                    String str = HomeFragment.sharedPreferences.getString(AddTasksFragment.DATA, "");
                    Gson gson = new Gson();
                    if (!str.isEmpty()) {
                        // lasts objects
                        Tasks[] existingTasks = gson.fromJson(str, Tasks[].class);
                        if (existingTasks == null) {
                            existingTasks = new Tasks[0];
                        }
                        // Add the new Tasks object to the array
                        ArrayList<Tasks> updatedTaskList = new ArrayList<>(Arrays.asList(existingTasks));
                        updatedTaskList.add(newTask);
                        String updatedJson = gson.toJson(updatedTaskList.toArray(new Tasks[0]));
                        HomeFragment.editor.putString(DATA, updatedJson);
                        HomeFragment.editor.commit();
                        textInputEditTextTitle.getText().clear();
                        textInputEditTextData.getText().clear();
                        textInputEditTextDate.getText().clear();
                        textInputEditTextTime.getText().clear();
                        Toast.makeText(requireContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                        System.out.println(newTask.getTasksId()+"--ID--");

                    }else{
                        ArrayList<Tasks> newTaskList = new ArrayList<>();
                        newTaskList.add(newTask);
                        String updatedJson = gson.toJson(newTaskList.toArray(new Tasks[0]));
                        HomeFragment.editor.putString(DATA, updatedJson);
                        HomeFragment.editor.commit();
                        textInputEditTextTitle.getText().clear();
                        textInputEditTextData.getText().clear();
                        textInputEditTextDate.getText().clear();
                        textInputEditTextTime.getText().clear();
                        Toast.makeText(requireContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return root;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}