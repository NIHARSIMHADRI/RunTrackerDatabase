package com.example.runtrackerdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListRunDataActivity extends AppCompatActivity {

    private static final String TAG = "ListRunDataActivity";
    DatabaseHelper mDatabaseHelper;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_run_data);

        mListView = (ListView) findViewById(R.id.runListView);
        mDatabaseHelper = new DatabaseHelper(this);

        populateListView();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the Listview");

        // get the data and append to a list
        Cursor data = mDatabaseHelper.getData();

        // Create an arraylist to store all the database elements in
        // Return back to this and make it an ArrayList of Objects and then pull out all the elements,
        // call the object constructor, and add the object to the arraylist
        ArrayList<String> runData = new ArrayList<>();
        while (data.moveToNext()) {
            // get the value from the database in column 1
            // then add it to the ArrayList
            runData.add(data.getString(1));
            // runData.add(data.getString(1) + ", " + data.getString(2));     // Name of runner
        }

        // Create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, runData);
        mListView.setAdapter(adapter);

        // set an onItemClickListener to the listview to respond to user interaction
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // get the index of the name that was clicked on
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You clicked on " + name);

                Cursor data = mDatabaseHelper.getItemID(name);  // get the unique id associated with that name
                int itemID = -1;

                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                }

                // if data is returned, log it.  If not, send an error message via a toast

                if (itemID > -1) {
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent intent = new Intent(ListRunDataActivity.this, EditRunDataActivity.class);
                    intent.putExtra(EditRunDataActivity.ID, itemID);
                    intent.putExtra(EditRunDataActivity.NAME, name);
                    startActivity(intent);
                }
                else {
                    toastMessage("No ID associated with that name");
                }


            }
        });


    }

    /**
     * Customizable toast message
     * @param message
     */

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
