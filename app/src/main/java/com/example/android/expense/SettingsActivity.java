package com.example.android.expense;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    DBHandler dbHandler;
    EditText limitET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        limitET = (EditText) findViewById(R.id.limitET);
        dbHandler = DBHandler.getInstance(this, null, null, 1);
        int limit = dbHandler.getLimit();
        limitET.setText(limit + "");
    }

    public void setLimitHandler(View view) {

        int limit = Integer.parseInt(limitET.getText().toString());
        dbHandler.setLimit(limit);

        Toast toast = Toast.makeText(getApplicationContext(), "Limit Set", Toast.LENGTH_SHORT);
        toast.show();
    }

}
