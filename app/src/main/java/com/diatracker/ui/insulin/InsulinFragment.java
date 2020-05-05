package com.diatracker.ui.insulin;

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

public class InsulinFragment extends Fragment {

    private InsulinViewModel insulinViewModel;
    private TextView date;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        insulinViewModel =
                ViewModelProviders.of(this).get(InsulinViewModel.class);
        View root = inflater.inflate(R.layout.fragment_insulin, container, false);
        final TextView textView = root.findViewById(R.id.text_insulin);
        date = (TextView) root.findViewById(R.id.textDate);
        date.setText(DiaTrackerMain.getDateStr());
        insulinViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
