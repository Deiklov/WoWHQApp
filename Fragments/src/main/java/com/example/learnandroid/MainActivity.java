package com.example.learnandroid;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggler();
            }
        });
    }

    private void toggler() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.FL1);
        if (fragment != null && fragment.isAdded()){
            transaction.remove(fragment);
            Log.d("INFO", "remove");
        }
        else {
            transaction.add(R.id.FL1, new FirstFragment());
            Log.d("INFO", "add");
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
