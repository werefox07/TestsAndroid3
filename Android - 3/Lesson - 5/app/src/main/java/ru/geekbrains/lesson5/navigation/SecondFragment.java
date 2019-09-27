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

import java.util.Random;

public class SecondFragment extends Fragment {

    public SecondFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController nav = Navigation.findNavController(view);

        view.findViewById(R.id.btnRunStart).setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putString("Count", String.valueOf(new Random().nextInt()));
            nav.navigate(R.id.action_secondFragment_to_startFragment, b);
        });
    }

}
