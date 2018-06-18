package com.example.basit009.vaccs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.basit009.vaccs.ClientsJson.DoseAddData;
import com.example.basit009.vaccs.ClientsJson.NetworkUtils;
import com.example.basit009.vaccs.ClientsJson.ServiceGenerator;
import com.example.basit009.vaccs.ClientsJson.VaccsClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DosesSendActivity extends AppCompatActivity {

    private EditText etDoseName, etDoseOrder, etDoseMinage, etDoseMaxage, etDoseMingap;
    private Button btnDoseAdd;
    private int vaccsId = 0;
    private VaccsClient vaccsClient;
    private String doseName;
    private int doseMinage = 0, doseMaxage = 0, doseMingap = 0, doseOrder = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doses_send);
        vaccsClient = ServiceGenerator.createService(VaccsClient.class);
        vaccsId = getIntent().getExtras().getInt("VaccId");
        etDoseName = findViewById(R.id.et_send_dose_name);
        etDoseOrder = findViewById(R.id.et_send_dose_order);
        etDoseMinage = findViewById(R.id.et_send_dose_minage);
        etDoseMaxage = findViewById(R.id.et_send_dose_maxage);
        etDoseMingap = findViewById(R.id.et_send_dose_mingap);
        btnDoseAdd = findViewById(R.id.btn_send_dose_add);


        btnDoseAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doseName = etDoseName.getText().toString();
                // if(!(etDoseOrder.getText().toString().equals("")))
                // if(!(etDoseMinage.getText().toString().equals("")))

                // if(!(etDoseMaxage.getText().toString().equals("")))

                // if(!(etDoseMingap.getText().toString().equals("")))

                if (!isStringInteger(etDoseMinage.getText().toString())) {
                    etDoseMinage.requestFocus();
                    etDoseMinage.setError("Invalid number of days");
                    return;
                }
                if (!isStringInteger(etDoseMaxage.getText().toString())&&!etDoseMaxage.getText().toString().equals("")) {
                    etDoseMaxage.requestFocus();
                    etDoseMaxage.setError("Invalid number of days");
                    return;
                }
                if (!isStringInteger(etDoseOrder.getText().toString())&&!etDoseOrder.getText().toString().equals("")) {
                    etDoseOrder.requestFocus();
                    etDoseOrder.setError("Invalid number of days");
                    return;
                }
                if (!isStringInteger(etDoseMingap.getText().toString())&&!etDoseMingap.getText().toString().equals("")) {
                    etDoseMingap.requestFocus();
                    etDoseMingap.setError("Invalid number of days");
                    return;
                }


                if (etDoseName.getText().toString().equals("")) {
                    etDoseName.requestFocus();
                    etDoseName.setError("Field Required");
                    return;
                }
                if (etDoseMinage.getText().toString().equals("")) {
                    etDoseMinage.requestFocus();
                    etDoseMinage.setError("Field Required");
                    return;
                } else {
                    doseMinage = Integer.parseInt(etDoseMinage.getText().toString());
                }

                if (etDoseMaxage.getText().toString().equals("")) {
                    doseMaxage = 0;
                } else {
                    doseMaxage = Integer.parseInt(etDoseMaxage.getText().toString());
                }

                if (etDoseOrder.getText().toString().equals("")) {
                    doseOrder = 0;
                } else {
                    doseOrder = Integer.parseInt(etDoseOrder.getText().toString());
                }

                if (etDoseMingap.getText().toString().equals("")) {
                    doseMingap = 0;
                } else {
                    doseMingap = Integer.parseInt(etDoseMingap.getText().toString());
                }


                if (!NetworkUtils.isNetworkAvailable(DosesSendActivity.this)) {
                    Toast.makeText(DosesSendActivity.this, "NO internet Available", Toast.LENGTH_LONG).show();
                    return;
                }


                DoseAddData data = new DoseAddData(doseName, vaccsId, doseMinage, doseMaxage, doseOrder, doseMingap);

                Call<DoseAddData> call = vaccsClient.addDose(data);
                call.enqueue(new Callback<DoseAddData>() {
                    @Override
                    public void onResponse(Call<DoseAddData> call, Response<DoseAddData> response) {
                        Toast.makeText(DosesSendActivity.this, "dose added", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<DoseAddData> call, Throwable t) {

                        Toast.makeText(DosesSendActivity.this, "call failed", Toast.LENGTH_LONG).show();
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


