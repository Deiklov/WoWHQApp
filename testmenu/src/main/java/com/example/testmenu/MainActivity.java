package com.example.testmenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String LAUNCH_AUCANDDEALS = "LAUNCH_AUCANDDEALS";
    public static final String KEY_TYPE = "TYPE";
    TextView textViewAuc;
    TextView textViewDeals;
    TextView textViewToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewAuc= (TextView) findViewById(R.id.menu_auc);
        textViewDeals = (TextView) findViewById(R.id.menu_deals);
        textViewDeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LAUNCH_AUCANDDEALS);
                intent.putExtra(KEY_TYPE, false);
                startActivity(intent);
            }
        });
        textViewAuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LAUNCH_AUCANDDEALS);
                intent.putExtra(KEY_TYPE, true);
                startActivity(intent);
            }
        });
    }
}
