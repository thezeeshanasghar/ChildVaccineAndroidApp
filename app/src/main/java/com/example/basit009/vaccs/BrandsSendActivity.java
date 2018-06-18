package com.example.basit009.vaccs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.basit009.vaccs.ClientsJson.BrandsAddData;
import com.example.basit009.vaccs.ClientsJson.NetworkUtils;
import com.example.basit009.vaccs.ClientsJson.ServiceGenerator;
import com.example.basit009.vaccs.ClientsJson.VaccsClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandsSendActivity extends AppCompatActivity {

    private int vaccsId=0;
    private EditText etBrandName;
    private Button btnBrandAdd;
    private VaccsClient vaccsClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brands_send);
        vaccsId=getIntent().getExtras().getInt("VaccId");

        etBrandName=findViewById(R.id.et_send_brand_name);
        btnBrandAdd=findViewById(R.id.btn_send_brand_add);
        vaccsClient= ServiceGenerator.createService(VaccsClient.class);


        btnBrandAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkUtils.isNetworkAvailable(BrandsSendActivity.this)) {
                    Toast.makeText(BrandsSendActivity.this, "NO internet Available", Toast.LENGTH_LONG).show();
                    return;
                }

                BrandsAddData data=new BrandsAddData(etBrandName.getText().toString(),vaccsId);

                Call<BrandsAddData> call=vaccsClient.addBrand(data);
                call.enqueue(new Callback<BrandsAddData>() {
                    @Override
                    public void onResponse(Call<BrandsAddData> call, Response<BrandsAddData> response) {
                        Toast.makeText(BrandsSendActivity.this, "Brand Added successfully", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<BrandsAddData> call, Throwable t) {
                        Toast.makeText(BrandsSendActivity.this, "Call failed", Toast.LENGTH_LONG).show();

                    }
                });

            }
        });

    }
}
