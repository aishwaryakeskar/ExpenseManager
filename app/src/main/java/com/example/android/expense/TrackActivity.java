package com.example.android.expense;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class TrackActivity extends AppCompatActivity {

    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        dbHandler = DBHandler.getInstance(this, null, null, 1);
        Intent intent = getIntent();
        boolean value = false;
        intent.getBooleanExtra("load", value);
        loadRecords();
    }

    public void loadRecords() {
        int totalExpenses = dbHandler.getTotalExpenses();
        int limit = dbHandler.getLimit();
        int balance = limit - totalExpenses;
        TextView totalExpView = (TextView) findViewById(R.id.totalExpenses);
        totalExpView.setText("₹ " + totalExpenses);

        TextView limitView = (TextView) findViewById(R.id.limit);
        limitView.setText("₹ " + limit);

        TextView balanceView = (TextView) findViewById(R.id.balance);
        balanceView.setText("₹ " + balance);

        List<Record> records = dbHandler.loadHandler();
        RecordAdapter adapter = new RecordAdapter(this, records);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }

    public void removeRecord(View view) {
        View parent = (View) view.getParent();
        TextView itemIdTextView = (TextView) parent.findViewById(R.id.itemIdtv);

        boolean result = dbHandler.deleteHandler(Integer.parseInt(itemIdTextView.getText().toString()));
        if (result) {
            loadRecords();
        }
    }
}




