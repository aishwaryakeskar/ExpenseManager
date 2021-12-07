package com.example.android.expense;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class RecordAdapter extends ArrayAdapter<Record> {


    public RecordAdapter(Activity context, List<Record> records) {

        super(context, 0, records);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.recordlist_item, parent, false);
        }
        try {
            Record currentRecord = getItem(position);

            TextView itemIdTextView = (TextView) listItemView.findViewById(R.id.itemIdtv);
            itemIdTextView.setText(currentRecord.getItemId() + "");

            TextView nameTextView = (TextView) listItemView.findViewById(R.id.nametv);
            nameTextView.setText(currentRecord.getItemName());

            TextView categoryTextView = (TextView) listItemView.findViewById(R.id.categorytv);
            categoryTextView.setText(currentRecord.getCategory());

            TextView modeTextView = (TextView) listItemView.findViewById(R.id.modetv);
            modeTextView.setText(currentRecord.getMode());

            TextView amountTextView = (TextView) listItemView.findViewById(R.id.amounttv);
            amountTextView.setText(currentRecord.getAmount() + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listItemView;
    }
}
