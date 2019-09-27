package ru.geekbrains.lesson5.cicerone;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.lesson5.App;
import ru.geekbrains.lesson5.R;

public class StartCicFragment extends Fragment {

    @BindView(R.id.btnRunFirst)
    Button btnRunFirst;

    public StartCicFragment() {
    }

    private static final String EXTRA_NUMBER = "extra_number";

    public static StartCicFragment getNewInstance(int number) {
        StartCicFragment fragment = new StartCicFragment();

        Bundle args = new Bundle();
        args.putInt(EXTRA_NUMBER, number);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        int count = getArguments() == null ? 0 : getArguments().getInt(EXTRA_NUMBER);
        ((TextView) view.findViewById(R.id.text)).setText("Start fragment: " + count);

        btnRunFirst.setOnClickListener(v -> {
            App.INSTANCE.getRouter().navigateTo(new Screens.FirstCicScreen());
        });
    }
}
