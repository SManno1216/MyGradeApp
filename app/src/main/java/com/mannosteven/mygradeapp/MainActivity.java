package com.mannosteven.mygradeapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int NEW_CLASS_REQUEST_CODE = 1;
    private static final int ITEMS_REQUEST_CODE = 2;

    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ExampleItem> mExampleList;

    private Button buttonAdd;
    private FloatingActionButton fab;

    private StudentClass studentClass;
    private ArrayList<StudentClass> studentClassArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createExampleList();
        buildRecyclerView();
        setButtons();

    }

    @Override
    public void onClick(View v) {
        int position;

        switch (v.getId()){
            //case R.id.add_class:
            case R.id.fab:
                openAddClassActivity();
                break;
        }
    }

    public void createExampleList(){
        mExampleList = new ArrayList<>();
    }

    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(mExampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //changeItem(position, "Clicked");
                Intent intent = new Intent(MainActivity.this, GradeCalculator.class);
                intent.putExtra("Student Class", mExampleList.get(position).getStudentClass());
                intent.putExtra("Position", position);
                startActivityForResult(intent, ITEMS_REQUEST_CODE);
                //startActivity(intent);
            }

            @Override
            public void onDeleteClick(int position) {
                showDeleteDialog(position);
            }
        });
    }

    public void insertItem(int position){
        if(position <= mExampleList.size() && position >= 0){
            mExampleList.add(position, new ExampleItem(R.drawable.ic_android, "new item at position" + position, "This is Line 2", new StudentClass()));
            mAdapter.notifyItemInserted(position);
        }else{
            Toast.makeText(this, "Position is Out of Bounds", Toast.LENGTH_LONG).show();
        }
    }

    public void removeItem(int position){
        if(position <= mExampleList.size() && position >= 0){
            mExampleList.remove(position);
            mAdapter.notifyItemRemoved(position);
        }else{
            Toast.makeText(this, "Position is Out of Bounds", Toast.LENGTH_LONG).show();
        }
    }

    public void addItem(int position){
        mExampleList.add(position, new ExampleItem(R.drawable.ic_android, "New Item" , "New Line", new StudentClass()));
        mAdapter.notifyItemInserted(position);
    }

    public void changeItem(int position, String text){
        mExampleList.get(position).setText1(text);
        mAdapter.notifyItemChanged(position);
    }

    public void setButtons(){
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
        //buttonAdd = findViewById(R.id.add_class);
        //buttonAdd.setOnClickListener(this);
    }

    public void openAddClassActivity(){
        Intent intent = new Intent(MainActivity.this, NewClassActivity.class);
        startActivityForResult(intent, NEW_CLASS_REQUEST_CODE);
    }

    public void showDeleteDialog(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.Delete_text);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeItem(position);
                mAdapter.notifyItemRemoved(position);
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_CLASS_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                ExampleItem item = data.getParcelableExtra("Example Item");
                studentClass = new StudentClass();
                studentClass = item.getStudentClass();
                studentClass.setClassName(item.getText1());
                item.setStudentClass(studentClass);
                mExampleList.add(item);
                mAdapter.notifyItemInserted(mExampleList.size());
            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Couldn't Add New Class", Toast.LENGTH_LONG).show();
            }
        }

        if(requestCode == ITEMS_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                int pos = data.getIntExtra("Position", 0);
                studentClass = data.getParcelableExtra("Student Class");
                mExampleList.get(pos).setText2("Current Grade: " + studentClass.getCurrentGrade() + "%");
                mExampleList.get(pos).setStudentClass(studentClass);
                mAdapter.notifyItemChanged(pos);
                Log.i("Class Added", "Class Added Successfully");
            }
        }

    }
}