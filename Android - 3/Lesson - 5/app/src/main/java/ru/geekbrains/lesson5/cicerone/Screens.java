package ru.geekbrains.lesson5.cicerone;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import ru.geekbrains.lesson5.navigation.MainActivity;
import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {

    public static final class StartCicScreen extends SupportAppScreen {

        private int number;

        public StartCicScreen(int number) {
            this.number = number;
        }

        @Override
        public Fragment getFragment() {
            return StartCicFragment.getNewInstance(number);
        }
    }

    public static final class FirstCicScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return new FirstCicFragment();
        }
    }

    public static final class SecondCicScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return new SecondCicFragment();
        }
    }

    public static final class StartMainScreen extends SupportAppScreen {
        @Override
        public Intent getActivityIntent(Context context) {
            return new Intent(context, MainActivity.class);
        }
    }
}
