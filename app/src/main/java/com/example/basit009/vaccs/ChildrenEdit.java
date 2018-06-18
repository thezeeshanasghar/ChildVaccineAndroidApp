package com.example.basit009.vaccs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basit009.vaccs.ClientsJson.ChildrenUser;
import com.example.basit009.vaccs.ClientsJson.NetworkUtils;
import com.example.basit009.vaccs.ClientsJson.ServiceGenerator;
import com.example.basit009.vaccs.ClientsJson.VaccsClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildrenEdit extends AppCompatActivity {

    private TextView tvChildrenName,tvChildrenGender,tvChildrenBirth,tvChildrenFather,tvChildrenEmail;
    private VaccsClient vaccsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children_edit);

        tvChildrenName=findViewById(R.id.tv_children_edit_name);
        tvChildrenGender=findViewById(R.id.tv_children_edit_gender);
        tvChildrenBirth=findViewById(R.id.tv_children_edit_birth);
        tvChildrenFather=findViewById(R.id.tv_children_edit_father);
        tvChildrenEmail=findViewById(R.id.tv_children_edit_email);
        vaccsClient= ServiceGenerator.createService(VaccsClient.class);

        if (!NetworkUtils.isNetworkAvailable(ChildrenEdit.this)) {
            Toast.makeText(ChildrenEdit.this, "NO internet Available", Toast.LENGTH_LONG).show();
            return;
        }

        final Call<ChildrenUser> call=vaccsClient.child(4);
        call.enqueue(new Callback<ChildrenUser>() {
            @Override
            public void onResponse(retrofit2.Call<ChildrenUser> call, Response<ChildrenUser> response) {
                ChildrenUser childrenUser=response.body();
                if(childrenUser!=null){
                    Toast.makeText(ChildrenEdit.this, "SUccessfull", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(retrofit2.Call<ChildrenUser> call, Throwable t) {
                Toast.makeText(ChildrenEdit.this, "Error", Toast.LENGTH_LONG).show();

            }
        });



    }
}
