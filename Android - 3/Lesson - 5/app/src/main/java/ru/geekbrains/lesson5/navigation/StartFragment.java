package ru.geekbrains.lesson5.navigation;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import ru.geekbrains.lesson5.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StartFragment extends Fragment {

    public StartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String count = getArguments() == null ? "0" : getArguments().getString("Count");
        ((TextView)view.findViewById(R.id.text)).setText("Start fragment: " + count);

        NavController nav = Navigation.findNavController(view);

        view.findViewById(R.id.btnRunFirst).setOnClickListener(v -> {
            nav.navigate(R.id.action_startFragment_to_firstFragment);
        });
    }
}
