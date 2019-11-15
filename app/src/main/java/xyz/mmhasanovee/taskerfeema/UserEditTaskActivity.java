package xyz.mmhasanovee.taskerfeema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import xyz.mmhasanovee.taskerfeema.Prevalent.Prevalent;

public class UserEditTaskActivity extends AppCompatActivity {

    private String task_id;
    private String task_status;
    Button update_task_status;
    EditText task_reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_task);

        task_id = getIntent().getStringExtra("tid");
        //Toast.makeText(this, task_id, Toast.LENGTH_SHORT).show();

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner1);
//create a list of items for the spinner.
        String[] items = new String[]{"NOT DONE", "UNDER PROCESSING", "DONE"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    //Toast.makeText(UserEditTaskActivity.this, "NOT DONE", Toast.LENGTH_SHORT).show();
                    task_status="NOT DONE";
                    task_reason.setVisibility(View.VISIBLE);
                } else if (i == 1) {
                    //Toast.makeText(UserEditTaskActivity.this, "PROCESSING", Toast.LENGTH_SHORT).show();
                    task_status="UNDER PROCESSING";
                    task_reason.setVisibility(View.VISIBLE);
                }
                if (i == 2) {
                    //Toast.makeText(UserEditTaskActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    task_status="DONE";
                    task_reason.setVisibility(View.INVISIBLE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                //Toast.makeText(UserEditTaskActivity.this, "you r a nibba", Toast.LENGTH_SHORT).show();
            }
        });

        task_reason=findViewById(R.id.task_reason);
        update_task_status=findViewById(R.id.update_task_status);
        update_task_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_status();
            }
        });



    }

    private void update_status() {

        if (!task_status.equals("DONE") && TextUtils.isEmpty(task_reason.getText().toString()))
        {
            Toast.makeText(this, "Please write the reason of the delay", Toast.LENGTH_LONG).show();
        }

        else {

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Tasks");

            HashMap<String, Object> userMap = new HashMap<>();
            userMap.put("task_status", task_status);
            userMap.put("reason", task_reason.getText().toString());

            ref.child(Prevalent.CurrentonlineUser.getPhone()).child(task_id).updateChildren(userMap);
            Toast.makeText(this, "Task Status Updated Successfully", Toast.LENGTH_SHORT).show();

            Intent gomain=new Intent(UserEditTaskActivity.this,UserHomeActivity.class);
            startActivity(gomain);
            finish();




        }

    }
}
