package com.example.practical1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.practical1.adapter.BaseAdapter;
import com.example.practical1.databinding.ActivityMainBinding;
import com.example.practical1.listener.ItemClickListener;
import com.example.practical1.model.GridModel;
import com.example.practical1.util.AppConstant;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, ItemClickListener {

    private Context mContext = this;

    private ActivityMainBinding binding;

    private String enteredNumber;

    private ArrayList<GridModel> gridModelArrayList;
    private BaseAdapter adapter;

    private Handler handler = new Handler();
    private Runnable runnable;

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
            gridModel.setClickable(false);
            gridModel.setColor(AppConstant.Colors.White);
            gridModelArrayList.add(gridModel);
        }

        adapter = new BaseAdapter(mContext, gridModelArrayList, R.layout.item_button, this);
        binding.rvButtons.setAdapter(adapter);
        binding.rvButtons.setLayoutManager(new GridLayoutManager(mContext, number));

        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(runnable, 1000);
                setRandomButtonClickable(new Random().nextInt(number * number));
            }
        }, 1000);
    }

    private void setRandomButtonClickable(int nextInt) {

        try {
            GridModel gridModel = gridModelArrayList.get(nextInt);
            if (gridModel.getColor() != AppConstant.Colors.Blue) {
                gridModel.setColor(AppConstant.Colors.Red);
                gridModel.setClickable(true);
                gridModelArrayList.set(nextInt, gridModel);
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
    }

    @Override
    public void onItemClick(int itemPosition, Object object) {
        GridModel gridModel = (GridModel) object;
        gridModel.setColor(AppConstant.Colors.Blue);
        gridModelArrayList.set(itemPosition, gridModel);
        adapter.notifyDataSetChanged();
        checkAllClicked();
    }

    private void checkAllClicked() {

        boolean allClicked = true;

        for (int i = 0; i < gridModelArrayList.size(); i++) {

            GridModel gridModel = gridModelArrayList.get(i);
            if (gridModel.getColor() != AppConstant.Colors.Blue) {
                allClicked = false;
                break;
            }
        }

        if (allClicked) {
            new AlertDialog.Builder(mContext).
                    setTitle("Congratulations")
                    .setMessage("You won the Game!!!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
            handler.removeCallbacks(runnable);
        }
    }
}