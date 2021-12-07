package com.example.android.expense;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class RecordActivity extends AppCompatActivity {
    EditText itemName;
    EditText amount;
    DBHandler dbHandler;
    Spinner modesp;
    Spinner categorysp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        amount = (EditText) findViewById(R.id.amountET);
        itemName = (EditText) findViewById(R.id.itemnameET);
        modesp = (Spinner) findViewById(R.id.modeSP);
        categorysp = (Spinner) findViewById(R.id.categorySP);
        dbHandler = DBHandler.getInstance(this, null, null, 1);
    }

    public void addRecord(View view) {
        int ramount = Integer.parseInt(amount.getText().toString());
        String mode = String.valueOf(modesp.getSelectedItem());
        String category = String.valueOf(categorysp.getSelectedItem());
        String name = itemName.getText().toString();
        if (!validate(name, ramount)) {
            Toast toast = Toast.makeText(getApplicationContext(), "Amount exceeding max limit", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Record record = new Record(name, mode, category, ramount);
        dbHandler.addHandler(record);
        amount.setText("");
        itemName.setText("");

        Toast toast = Toast.makeText(getApplicationContext(), "Item added", Toast.LENGTH_SHORT);
        toast.show();

        List<Record> records = dbHandler.loadHandler();
        Intent intent = new Intent(this, TrackActivity.class);
        intent.putExtra("load", true);
        startActivity(intent);
    }

    private boolean validate(String name, int amount) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        int totalExpenses = dbHandler.getTotalExpenses();
        if ((totalExpenses + amount) > dbHandler.getLimit()) {
            return false;
        }
        return true;
    }
}
