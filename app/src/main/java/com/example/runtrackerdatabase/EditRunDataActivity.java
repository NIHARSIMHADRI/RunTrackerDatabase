package com.example.runtrackerdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditRunDataActivity extends AppCompatActivity {

    public static final String ID = "id";
    public static final String NAME = "name";

    private Button deleteBtn, updateBtn;
    private EditText editName;

    private static final String TAG = "EditRunDataActivity";

    DatabaseHelper mDatabaseHelper;
    private String selectedName;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_run_data);

        editName = (EditText) findViewById(R.id.runnerName);
        deleteBtn = (Button) findViewById(R.id.buttonDelete);
        updateBtn = (Button) findViewById(R.id.buttonUpdate);
        mDatabaseHelper = new DatabaseHelper(this);

        // get the intent and get the extra info that was put into the intent
        Log.d(TAG, "onCreate, just established vars");
        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra(ID, -1);    // -1 is default value
        selectedName = receivedIntent.getStringExtra(NAME);
        Log.d(TAG, "onCreate, selectedID " + selectedID + " selectedName " + selectedName);

        // set the EditText to equal the name they clicked on
        editName.setText(selectedName);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editName.getText().toString();
                if(!item.equals("")){
                    mDatabaseHelper.updateName(item, selectedID, selectedName);
                }
                else {
                    toastMessage("You must enter a name");
                }

            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.deleteName(selectedID, selectedName);
                editName.setText("");
                toastMessage("Removed from database");
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
