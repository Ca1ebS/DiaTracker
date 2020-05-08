package com.diatracker.ui.dietary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.diatracker.DiaTrackerDB;
import com.diatracker.DiaTrackerMain;
import com.diatracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DietaryFragment extends Fragment implements OnClickListener {

    private DietaryViewModel dietaryViewModel;
    private TextView date;
    private EditText calorie;
    private EditText carbs;
    private EditText sugar;
    private Button submit;
    private Button clear;
    private int enteredCalorie;
    private int enteredSugar;
    private int enteredCarbs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dietaryViewModel =
                ViewModelProviders.of(this).get(DietaryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dietary, container, false);

        final TextView textView = root.findViewById(R.id.text_dietary);
        date = (TextView) root.findViewById(R.id.textDate);
        date.setText(DiaTrackerMain.getDateStr());
        submit = (Button) root.findViewById(R.id.buttonSubmit);
        clear = (Button) root.findViewById(R.id.buttonClear);
        calorie = (EditText) root.findViewById(R.id.editCalorie);
        carbs = (EditText) root.findViewById(R.id.editCarbs);
        sugar = (EditText) root.findViewById(R.id.editSugar);
        submit.setOnClickListener(this);
        clear.setOnClickListener(this);

        dietaryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSubmit:
                addIntake();
                break;
            case R.id.buttonClear:
                calorie.setText("");
                carbs.setText("");
                sugar.setText("");
                break;
            default:
                break;
        }
    }

    public void addIntake() {
        EditText[] edits = {calorie, carbs, sugar};
        boolean error = false;
        for(int i=0;i<edits.length;i++) {
            if(edits[i].getText().toString().isEmpty()) {
                edits[i].setError("Enter number");
                edits[i].setBackgroundResource(R.drawable.edit_error);
                error = true;}
            else {
                edits[i].setBackgroundResource(R.drawable.edit_normal);
                error = false; }
        }
        if(!error) {
            enteredCalorie = Integer.parseInt(calorie.getText().toString());
            enteredCarbs = Integer.parseInt(carbs.getText().toString());
            enteredSugar = Integer.parseInt(sugar.getText().toString());

            /*if() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                if() builder.setMessage(R.string.).setTitle("");
                else builder.setMessage(R.string.).setTitle("");

                builder.setPositiveButton ("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }*/

            DiaTrackerDB db = new DiaTrackerDB(getActivity());
            Boolean success = db.createDiet(enteredCalorie, enteredCarbs, enteredSugar);
            if (success) {
                try {
                    SharedPreferences sharedpreferences = getActivity().getSharedPreferences("my_pref", Context.MODE_PRIVATE);
                    String stopDateString = "05/07/2020 10:51:00 PM";
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
                    Date stopDate = dateFormat.parse(stopDateString);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    Date currentTime = Calendar.getInstance().getTime();

                    if (sharedpreferences.getString("Date", "").isEmpty()) {
                        editor.putString("Date", currentTime.toString());
                        for(int i=0;i<edits.length;i++) {
                            edits[i].setText("");
                            edits[i].setEnabled(false);
                            edits[i].setBackgroundResource(R.color.colorGray);
                        }
                        submit.setClickable(false);
                    }
                    if (currentTime.after(stopDate)) {
                        for(int i=0;i<edits.length;i++) {
                            edits[i].setText("");
                            edits[i].setEnabled(true);
                            edits[i].setBackgroundResource(R.drawable.edit_normal);
                        }
                        submit.setClickable(true);
                    }
                    editor.commit();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Toast toast = Toast.makeText(getActivity(), "Intake successfully added", Toast.LENGTH_LONG);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getActivity(), "There was an error", Toast.LENGTH_LONG);
                toast.show();
            }
        }
        else {
            return;
        }
    }
}
