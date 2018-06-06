package y.com.myhttp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private FragmentTransaction mFragmentTransaction;
    private Fragment mBaseFragment = null;

    private final static String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager lFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = lFragmentManager.beginTransaction();
        mBaseFragment = new FragmentGet();
        mFragmentTransaction.replace(R.id.v_container, mBaseFragment);
        mFragmentTransaction.commit();


        BottomNavigationView lBottomNavigationView = findViewById(R.id.v_bottom_nav);
        lBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                loadFragment(item.getItemId());
                return true;
            }
        });
    }

    private void loadFragment(int pItemId) {


        switch (pItemId) {
            case R.id.menu_get:
                mBaseFragment = new FragmentGet();
                break;
            case R.id.menu_post:
                mBaseFragment = new FragmentPost();
                break;
        }

        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.v_container, mBaseFragment);
        try {
            mFragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showLog(String message) {
        Log.d(TAG, message);
    }


    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

}
