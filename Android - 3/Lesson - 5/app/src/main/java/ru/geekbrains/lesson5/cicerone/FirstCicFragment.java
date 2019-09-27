package ru.geekbrains.lesson5.cicerone;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.lesson5.App;
import ru.geekbrains.lesson5.R;

public class FirstCicFragment extends Fragment {

    @BindView(R.id.btnRunSecond)
    Button btnRunSecond;

    public FirstCicFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        btnRunSecond.setOnClickListener(v -> {
            App.INSTANCE.getRouter().navigateTo(new Screens.SecondCicScreen());
        });
    }
}
