package com.diatracker.ui.glucose;

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
import com.diatracker.R;

public class GlucoseFragment extends Fragment
    implements OnClickListener {
    private GlucoseViewModel glucoseViewModel;
    private Button submitButton;
    private EditText sugarLevel;
    private int enteredLevel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        glucoseViewModel =
                ViewModelProviders.of(this).get(GlucoseViewModel.class);
        View root = inflater.inflate(R.layout.fragment_glucose, container, false);
        final TextView textView = root.findViewById(R.id.text_glucose);
        submitButton = (Button) root.findViewById(R.id.glucoseSubmit);
        sugarLevel = (EditText) root.findViewById(R.id.editSugar);
        submitButton.setOnClickListener(this);
        glucoseViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
            case R.id.glucoseSubmit:
                AddSugar();
                break;
            default:
                break;
        }
    }

    public void AddSugar() {
        enteredLevel = Integer.parseInt(sugarLevel.getText().toString());
        DiaTrackerDB db = new DiaTrackerDB(getActivity());
        Boolean success = db.createGlucose(enteredLevel);
        if (success) {
            sugarLevel.setText("");
            Toast toast = Toast.makeText(getActivity(), "Level successfully added",Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            Toast toast = Toast.makeText(getActivity(), "There was an error",Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
