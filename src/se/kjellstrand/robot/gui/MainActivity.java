package se.kjellstrand.robot.gui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * The main activity holding the control panel and visualiser fragment.
 * 
 */
public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new ControlPanelFragment()).commit();
    }

}
