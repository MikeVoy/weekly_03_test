package com.example.weekly_03_test.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weekly_03_test.R;

public class DetailsActivity extends AppCompatActivity {

    private TextView details_add_to_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent1 = getIntent();
        int pid = intent1.getIntExtra("Pid", 0);
        Toast.makeText(this,"接收到的ID"+pid,Toast.LENGTH_LONG).show();
        initView();

        details_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, CartActivity.class);

                startActivity(intent);
            }
        });


    }

    private void initView() {
        details_add_to_cart = findViewById(R.id.details_add_to_cart);
    }
}
