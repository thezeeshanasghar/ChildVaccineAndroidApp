package com.example.basit009.vaccs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basit009.vaccs.ClientsJson.ChildrenUser;
import com.example.basit009.vaccs.ClientsJson.DoctorUserOne;
import com.example.basit009.vaccs.ClientsJson.NetworkUtils;
import com.example.basit009.vaccs.ClientsJson.ServiceGenerator;
import com.example.basit009.vaccs.ClientsJson.VaccsClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorsEdit extends AppCompatActivity {

    private TextView tvEditDoctorFirstName,tvEditDoctorLastName,tvEditDoctorEmail,tvEditDoctorMobile,
            tvEditDoctorPmdc,tvEditDoctorValidupto;
    private CheckBox cbAllowInvoice,cbAllowFollowup,cbAllowChart,cbAllowInventory;
    private EditText etSmsLimit;
    private int doctorId=0;

    private VaccsClient vaccsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_edit);

        doctorId=getIntent().getExtras().getInt("doctorid");
        vaccsClient = ServiceGenerator.createService(VaccsClient.class);
        tvEditDoctorFirstName=findViewById(R.id.tv_edit_doctor_firstname);
        tvEditDoctorLastName=findViewById(R.id.tv_edit_doctor_lastname);
        tvEditDoctorEmail=findViewById(R.id.tv_edit_doctor_email);
        tvEditDoctorMobile=findViewById(R.id.tv_edit_doctor_mobile);
        tvEditDoctorPmdc=findViewById(R.id.tv_edit_doctor_pmdc);
        tvEditDoctorValidupto=findViewById(R.id.tv_edit_doctor_validpupto);
        cbAllowChart=findViewById(R.id.cb_edit_doctor_allowchart);
        cbAllowFollowup=findViewById(R.id.cb_edit_doctor_allowfollowup);
        cbAllowInvoice=findViewById(R.id.cb_edit_doctor_allowinvoice);
        cbAllowInventory=findViewById(R.id.cb_edit_doctor_allowinventory);
        etSmsLimit=findViewById(R.id.et_edit_doctor_smslimit);

        if (!NetworkUtils.isNetworkAvailable(DoctorsEdit.this)) {
            Toast.makeText(DoctorsEdit.this, "NO internet Available", Toast.LENGTH_LONG).show();
            return;
        }

        final Call<DoctorUserOne> call = vaccsClient.doctor(doctorId);
        call.enqueue(new Callback<DoctorUserOne>() {
            @Override
            public void onResponse(Call<DoctorUserOne> call, Response<DoctorUserOne> response) {
                Toast.makeText(DoctorsEdit.this, "response available", Toast.LENGTH_SHORT).show();
                DoctorUserOne doctorUserOne=response.body();
                if (doctorUserOne != null) {
                    if (doctorUserOne.doctorsListOne != null) {
                        tvEditDoctorFirstName.setText(doctorUserOne.doctorsListOne.firstname);
                        tvEditDoctorLastName.setText(doctorUserOne.doctorsListOne.lastname);
                        tvEditDoctorEmail.setText(doctorUserOne.doctorsListOne.Email);
                        tvEditDoctorMobile.setText(doctorUserOne.doctorsListOne.MobileNumber);
                        tvEditDoctorPmdc.setText(doctorUserOne.doctorsListOne.PMDC);
                        tvEditDoctorValidupto.setText(doctorUserOne.doctorsListOne.validupto);
                        cbAllowChart.setChecked(doctorUserOne.doctorsListOne.allowchart);
                        cbAllowFollowup.setChecked(doctorUserOne.doctorsListOne.allowfollowup);
                        cbAllowInvoice.setChecked(doctorUserOne.doctorsListOne.allowinvoice);
                        cbAllowInventory.setChecked(doctorUserOne.doctorsListOne.allowinventory);
                        etSmsLimit.setText(String.valueOf(doctorUserOne.doctorsListOne.smslimit));

                    }

                } else {
                    Toast.makeText(DoctorsEdit.this, "No response available", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DoctorUserOne> call, Throwable t) {
                Toast.makeText(DoctorsEdit.this, "Call Failed", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
