package xyz.mmhasanovee.taskerfeema;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;
import xyz.mmhasanovee.taskerfeema.Model.AdminHome;
import xyz.mmhasanovee.taskerfeema.Model.Tasks;
import xyz.mmhasanovee.taskerfeema.Model.User;
import xyz.mmhasanovee.taskerfeema.Prevalent.Prevalent;

public class UserHomeActivity extends AppCompatActivity {

    private Button assign_new_task;
    Button user_logout_btn;

    private RecyclerView ordersList;
    private String current_user;
    public int count=1;

    private DatabaseReference search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);


        Toast.makeText(this, Prevalent.CurrentonlineUser.getPhone(), Toast.LENGTH_SHORT).show();

        current_user= Prevalent.CurrentonlineUser.getPhone();

        search= FirebaseDatabase.getInstance().getReference().child("Tasks").child(current_user);



        ordersList = findViewById(R.id.user_tasks);
        ordersList.setLayoutManager(new LinearLayoutManager(this));
        user_logout_btn=findViewById(R.id.user_logout_btn);
        user_logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
                Intent gomain=new Intent(UserHomeActivity.this,MainActivity.class);
                gomain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(gomain);
                finish();
                Toast.makeText(UserHomeActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Tasks> options =
                new FirebaseRecyclerOptions.Builder<Tasks>()
                        .setQuery(search,Tasks.class)
                        .build();

        final FirebaseRecyclerAdapter<Tasks, UserHomeActivity.AdminOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<Tasks, UserHomeActivity.AdminOrdersViewHolder>(options) {  //firebase recycler "options" object
                    @Override
                    protected void onBindViewHolder(@NonNull final UserHomeActivity.AdminOrdersViewHolder adminOrdersViewHolder, final int ix, @NonNull final Tasks adminOrders) {


                        int xy=ix;
                        xy++;
                        adminOrdersViewHolder.task_id_u.setText("Task number: "+xy );
                        adminOrdersViewHolder.task_name_u.setText("To Do: "+adminOrders.getTask_name());
                        adminOrdersViewHolder.task_status_u.setText("Status: "+adminOrders.getTask_status());
                        adminOrdersViewHolder.reason_u.setText("Reason: "+adminOrders.getReason());

                        adminOrdersViewHolder.edit_task_status.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String task_id = getRef(ix).getKey();

                                Intent intent = new Intent(UserHomeActivity.this,UserEditTaskActivity.class);
                                intent.putExtra("tid",task_id);
                                startActivity(intent);
                            }
                        });


                    }

                    @NonNull
                    @Override
                    public UserHomeActivity.AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_task_layout,parent,false);
                        return new UserHomeActivity.AdminOrdersViewHolder(view);
                    }
                };

        ordersList.setAdapter(adapter);

        adapter.startListening();
    }



    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder{

        public Button ShowOrdersBtn;
        public ImageView edit_task_status;
        public TextView task_id_u,task_name_u,task_status_u,reason_u;
        public AdminOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            task_id_u = itemView.findViewById(R.id.task_id_u);
            task_name_u = itemView.findViewById(R.id.task_name_u);
            task_status_u = itemView.findViewById(R.id.task_status_u);
            reason_u=itemView.findViewById(R.id.reason_u);
            edit_task_status=itemView.findViewById(R.id.edit_task_status);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
