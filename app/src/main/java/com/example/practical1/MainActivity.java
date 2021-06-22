package com.example.practical1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.practical1.adapter.BaseAdapter;
import com.example.practical1.databinding.ActivityMainBinding;
import com.example.practical1.model.GridModel;
import com.example.practical1.util.AppConstant;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext = this;

    private ActivityMainBinding binding;

    private String enteredNumber;

    private ArrayList<GridModel> gridModelArrayList;
    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView((Activity) mContext, R.layout.activity_main);

        binding.btnSubmit.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnSubmit:
                if (binding.edtNumber.getText() != null && binding.edtNumber.getText().toString().length() > 0) {
                    enteredNumber = binding.edtNumber.getText().toString();

                    double number = Math.sqrt(Double.parseDouble(enteredNumber));
                    if (number == 0) {
                        Toast.makeText(mContext, "Enter valid number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    boolean isNumberIsSquareRoot = ((number - Math.floor(number)) == 0);

                    if (isNumberIsSquareRoot)
                        setRecyclerView((int) number);
                    else
                        Toast.makeText(mContext, "Not a Square Root Number", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(mContext, "Enter Square Root Number", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void setRecyclerView(int number) {

        gridModelArrayList = new ArrayList<>();
        for (int i = 0; i < number * number; i++) {
            GridModel gridModel = new GridModel();
            gridModel.setClickable(true);
            gridModel.setColor(AppConstant.Colors.White);
            gridModelArrayList.add(gridModel);
        }

        adapter = new BaseAdapter(mContext, gridModelArrayList, R.layout.item_button);
        binding.rvButtons.setAdapter(adapter);
        binding.rvButtons.setLayoutManager(new GridLayoutManager(mContext, number));
    }
}