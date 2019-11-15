package xyz.mmhasanovee.taskerfeema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class AssignTaskActivity extends AppCompatActivity {

    private TextView task_assigned_to_phone_number, task_assigned_id;
    private EditText task_assigned_name;
    private Button  task_assigned_btn;
    private String p3_number;
    public String task_id_unique;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_task);
        p3_number = getIntent().getStringExtra("uid");
        task_assigned_btn=findViewById(R.id.task_assigned_btn);
        task_assigned_id=findViewById(R.id.task_assigned_id);
        task_assigned_name=findViewById(R.id.task_assigned_name);
        task_assigned_to_phone_number=findViewById(R.id.task_assigned_to_phone_number);


        task_assigned_to_phone_number.setText("Phone number:"+p3_number);

         task_id_unique= UUID.randomUUID().toString();



        task_assigned_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTask();
            }
        });


    }

    private void updateTask() {

        if (TextUtils.isEmpty(task_assigned_name.getText().toString()))
        {
            Toast.makeText(this, "Task name can't be empty", Toast.LENGTH_SHORT).show();
        }

        else {

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Tasks");

            HashMap<String, Object> userMap = new HashMap<>();
            userMap.put("task_id", task_id_unique);
            userMap.put("task_name", task_assigned_name.getText().toString());
            userMap.put("task_status", "NOT DONE");
            userMap.put("reason", "NOT SPECIFIED");

            ref.child(p3_number).child(task_id_unique).updateChildren(userMap);
            Toast.makeText(this, "Task assigned succesfully", Toast.LENGTH_SHORT).show();

            Intent gomain=new Intent(AssignTaskActivity.this,AdminHomeActivity.class);
            startActivity(gomain);
            finish();
            Toast.makeText(AssignTaskActivity.this, "Task assigned successfully", Toast.LENGTH_SHORT).show();



        }


    }
}
