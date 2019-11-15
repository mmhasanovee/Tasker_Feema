package xyz.mmhasanovee.taskerfeema;


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
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import xyz.mmhasanovee.taskerfeema.Model.AdminHome;

public class AdminHomeActivity extends AppCompatActivity {

    private RecyclerView ordersList;
    private DatabaseReference ordersRef,adminViewRef;
    Button admin_logout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        ordersRef= FirebaseDatabase.getInstance().getReference().child("Users");
        adminViewRef= FirebaseDatabase.getInstance().getReference().child("Cart List").child("Admin View");


        ordersList = findViewById(R.id.users_list);
        ordersList.setLayoutManager(new LinearLayoutManager(this));

        admin_logout_btn=findViewById(R.id.admin_logout_btn);

        admin_logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gomain=new Intent(AdminHomeActivity.this,MainActivity.class);
                gomain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(gomain);
                finish();
                Toast.makeText(AdminHomeActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AdminHome> options =
                new FirebaseRecyclerOptions.Builder<AdminHome>()
                        .setQuery(ordersRef,AdminHome.class)
                        .build();

        FirebaseRecyclerAdapter<AdminHome, AdminOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<AdminHome, AdminOrdersViewHolder>(options) {  //firebase recycler "options" object
                    @Override
                    protected void onBindViewHolder(@NonNull final AdminOrdersViewHolder adminOrdersViewHolder, final int ix, @NonNull final AdminHome adminOrders) {

                        adminOrdersViewHolder.userName.setText("Name: "+adminOrders.getName());
                        adminOrdersViewHolder.userPhoneNumber.setText("Phone: "+adminOrders.getPhone());

                        adminOrdersViewHolder.ShowOrdersBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String p_number = getRef(ix).getKey();

                                Intent intent = new Intent(AdminHomeActivity.this,UserTasksDetails.class);
                                intent.putExtra("uid",p_number);
                                startActivity(intent);
                            }
                        });



                    }

                    @NonNull
                    @Override
                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_layout,parent,false);
                        return new AdminOrdersViewHolder(view);
                    }
                };

        ordersList.setAdapter(adapter);

        adapter.startListening();
    }



    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder{

        public Button ShowOrdersBtn;
        public TextView userName, userPhoneNumber;
        public AdminOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user_name);
            userPhoneNumber = itemView.findViewById(R.id.user_phone_number);
            ShowOrdersBtn = itemView.findViewById(R.id.show_user_task_btn);

        }
    }






}