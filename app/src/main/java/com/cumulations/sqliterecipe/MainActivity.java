package com.cumulations.sqliterecipe;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.editText_name) EditText editName;
    @BindView(R.id.editText_position) EditText editPosition;
    @BindView(R.id.editText_salary) EditText editSalary;
    @BindView(R.id.editText_id) EditText editTextId;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myDb = new DatabaseHelper(this);
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void addData(View view) {
        boolean isInserted = myDb.insertData(editName.getText().toString(), editPosition.getText().toString(), editSalary.getText().toString() );
        if(isInserted)
            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
    }

    public void updateData(View view) {
        boolean isUpdate = myDb.updateData(editTextId.getText().toString(), editName.getText().toString(), editPosition.getText().toString(), editSalary.getText().toString());
        if(isUpdate)
            Toast.makeText(MainActivity.this,"Data Update",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
    }

    public void viewData(View view) {
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            showMessage("Error","Nothing found");
            return;
        }

        StringBuilder buffer = new StringBuilder();
        while (res.moveToNext()) {
            buffer.append("Id :").append(res.getString(0)).append("\n");
            buffer.append("Name :").append(res.getString(1)).append("\n");
            buffer.append("Position :").append(res.getString(2)).append("\n");
            buffer.append("Salary :").append(res.getString(3)).append("\n\n");
        }

        showMessage("Data",buffer.toString());
    }

    public void deleteData(View view) {
        Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
        if(deletedRows > 0)
            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
    }
}