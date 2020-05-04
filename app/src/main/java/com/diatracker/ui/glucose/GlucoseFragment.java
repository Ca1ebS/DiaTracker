package com.diatracker.ui.glucose;

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

import com.diatracker.R;

public class GlucoseFragment extends Fragment {

    private GlucoseViewModel glucoseViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        glucoseViewModel =
                ViewModelProviders.of(this).get(GlucoseViewModel.class);
        View root = inflater.inflate(R.layout.fragment_glucose, container, false);
        final TextView textView = root.findViewById(R.id.text_glucose);
        glucoseViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
