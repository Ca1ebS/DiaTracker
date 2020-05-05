package com.diatracker.ui.dietary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.diatracker.DiaTrackerMain;
import com.diatracker.R;

public class DietaryFragment extends Fragment {

    private DietaryViewModel dietaryViewModel;
    private TextView date;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dietaryViewModel =
                ViewModelProviders.of(this).get(DietaryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dietary, container, false);
        final TextView textView = root.findViewById(R.id.text_dietary);
        date = (TextView) root.findViewById(R.id.textDate);
        date.setText(DiaTrackerMain.getDateStr());
        dietaryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
