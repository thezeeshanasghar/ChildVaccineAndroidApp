package com.example.basit009.vaccs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.basit009.vaccs.ClientsJson.NetworkUtils;
import com.example.basit009.vaccs.ClientsJson.ServiceGenerator;
import com.example.basit009.vaccs.ClientsJson.VaccinesAddData;
import com.example.basit009.vaccs.ClientsJson.VaccsClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VaccineSendActivity extends AppCompatActivity {

    private EditText etVaccineName;
    private EditText etChildMinage;
    private EditText etChildMaxage;
    private Button btnAddVaccine;
    private VaccsClient vaccsClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_send);

        etVaccineName = findViewById(R.id.et_send_vaccine_name);
        etChildMinage = findViewById(R.id.et_send_vaccine_minage);
        etChildMaxage = findViewById(R.id.et_send_vaccine_maxage);
        btnAddVaccine = findViewById(R.id.btn_add_vaccine);
        vaccsClient = ServiceGenerator.createService(VaccsClient.class);

        btnAddVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etVaccineName.getText().toString().equals("")) {
                    etVaccineName.requestFocus();
                    etVaccineName.setError("Field Required");
                    return;
                }
                if (etChildMinage.getText().toString().equals("")) {
                    etChildMinage.requestFocus();
                    etChildMinage.setError("Field Required");
                    return;
                }

                if (etChildMaxage.getText().toString().equals("")) {
                    etChildMaxage.requestFocus();
                    etChildMaxage.setError("Field Required");
                    return;
                }

                if (!isStringInteger(etChildMinage.getText().toString())) {
                    etChildMinage.requestFocus();
                    etChildMinage.setError("Invalid number of days");
                    return;
                }
                if (!isStringInteger(etChildMaxage.getText().toString())) {
                    etChildMaxage.requestFocus();
                    etChildMaxage.setError("Invalid number of days");
                    return;
                }

                if (!NetworkUtils.isNetworkAvailable(VaccineSendActivity.this)) {
                    Toast.makeText(VaccineSendActivity.this, "NO internet Available", Toast.LENGTH_LONG).show();
                    return;
                }

                final VaccinesAddData data = new VaccinesAddData(etVaccineName.getText().toString(),
                        Integer.parseInt(etChildMinage.getText().toString()), Integer.parseInt(etChildMaxage.getText().toString()));


                Call<VaccinesAddData> call = vaccsClient.addVaccines(data);
                call.enqueue(new Callback<VaccinesAddData>() {
                    @Override
                    public void onResponse(Call<VaccinesAddData> call, Response<VaccinesAddData> response) {
                        Toast.makeText(VaccineSendActivity.this, "Vaccine Added successfully", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<VaccinesAddData> call, Throwable t) {

                        Toast.makeText(VaccineSendActivity.this, "Call Failed", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

    }


    public boolean isStringInteger(String number) {
        try {
            int num = Integer.parseInt(number);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
