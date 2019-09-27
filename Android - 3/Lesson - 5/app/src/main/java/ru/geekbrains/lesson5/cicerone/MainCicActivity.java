package ru.geekbrains.lesson5.cicerone;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.lesson5.App;
import ru.geekbrains.lesson5.R;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;

public class MainCicActivity extends AppCompatActivity {

    private Navigator navigator = new SupportAppNavigator(this, R.id.content_cic);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cic_main);
        ButterKnife.bind(this);

        App.INSTANCE.getRouter().navigateTo(new Screens.StartCicScreen(0));

    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        App.INSTANCE.getNavigatorHolder().setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        App.INSTANCE.getNavigatorHolder().removeNavigator();
        super.onPause();
    }
}
