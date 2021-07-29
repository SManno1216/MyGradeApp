package com.mannosteven.mygradeapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class GradeCalculator extends AppCompatActivity implements View.OnClickListener {

    private int maxItems = 6;
    private int pos;
    private TextView currentGradeTextView;
    private EditText targetGradeEditText;
    private Button add_btn;
    private Button calculate_btn;
    private Button clear_btn;
    //private Button save_btn;
    private FloatingActionButton fab;

    private RecyclerView mRecyclerView;
    private SyllabusItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private StudentClass studentClass;
    private SyllabusItem syllabusItem;
    private ArrayList<SyllabusItem> syllabusItemArrayList;

    private String[] syllabusItemName = new String[maxItems];
    private double[] gradeNum = new double[maxItems];
    private double[] weightNum = new double[maxItems];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_calculator);

        initialize();
        createSyllabusItemList();


    }

    public void initialize() {
        currentGradeTextView = (TextView) findViewById(R.id.currentGradeTextView);
        targetGradeEditText = (EditText) findViewById(R.id.targetGradeEditText);
        //llContainer = (LinearLayout) findViewById(R.id.llContainer);
        add_btn = (Button) findViewById(R.id.add_btn);
        calculate_btn = (Button) findViewById(R.id.calculate_btn);
        clear_btn = (Button) findViewById(R.id.clear_btn);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        //save_btn = (Button) findViewById(R.id.save_btn);

        add_btn.setOnClickListener(this);
        calculate_btn.setOnClickListener(this);
        clear_btn.setOnClickListener(this);
        fab.setOnClickListener(this);
        //save_btn.setOnClickListener(this);
    }

    public void createSyllabusItemList(){
        syllabusItemArrayList = new ArrayList();
        //studentClass = new StudentClass();

        if(getIntent().getExtras() != null){
            studentClass = getIntent().getExtras().getParcelable("Student Class");
            setTitle(studentClass.getClassName());
            if(studentClass.getsItems() != null){
                syllabusItemArrayList = studentClass.getsItems();
                currentGradeTextView.setText("" + studentClass.getCurrentGrade());
                targetGradeEditText.setText("" + studentClass.getTargetGrade());
            }
            pos = getIntent().getExtras().getInt("Position");
            Log.i("Syllabus Items", " " + syllabusItemArrayList);
            buildRecyclerView();
        }else{
            buildRecyclerView();
        }
    }

    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new SyllabusItemAdapter(syllabusItemArrayList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new SyllabusItemAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_btn:
                if(mRecyclerView.getChildCount() < maxItems)
                    addItem(syllabusItemArrayList.size());
                break;
            case R.id.calculate_btn:
                if (checkIfValid())
                    //getList();
                    calculate();
                break;
            case R.id.clear_btn:
                clear();
                break;
            //case R.id.save_btn:
            case R.id.fab:
                Intent intent = new Intent();
                intent.putExtra("Student Class", studentClass);
                intent.putExtra("Position", pos);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    public void addItem(int position){
        syllabusItemArrayList.add(position, new SyllabusItem());
        mAdapter.notifyItemInserted(position);
    }

    public void removeItem(int position){
        if(position <= syllabusItemArrayList.size() && position >= 0){
            syllabusItemArrayList.remove(position);
            mAdapter.notifyItemRemoved(position);
        }
    }

    @SuppressLint("ShowToast")
    public boolean checkIfValid() {

        syllabusItemArrayList.clear();

        if (mRecyclerView.getChildCount() == 0) {
            Toast.makeText(this, "Add Syllabus Items First!", Toast.LENGTH_LONG).show();
            return false;
        }

        if (targetGradeEditText.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Target Grade!", Toast.LENGTH_LONG).show();
            return false;
        }

        for (int i = 0; i < mRecyclerView.getChildCount(); i++) {
            View view = mRecyclerView.getChildAt(i);
            EditText nameEditText = view.findViewById(R.id.name);
            EditText weightEditText = view.findViewById(R.id.weight);
            EditText gradeEditText = view.findViewById(R.id.grade);

            syllabusItem = new SyllabusItem();

            if (!nameEditText.getText().toString().equals("")) {
                syllabusItem.setItemName(nameEditText.getText().toString());
            } else {
                Toast.makeText(this, "Invalid input for Syllabus Item Name", Toast.LENGTH_LONG).show();
                return false;
            }

            if (!weightEditText.getText().toString().equals("")) {
                syllabusItem.setWeight(weightEditText.getText().toString());
            } else {
                Toast.makeText(this, "Invalid input for Weight", Toast.LENGTH_LONG).show();
                return false;
            }

            if (!gradeEditText.getText().toString().equals("")) {
                syllabusItem.setGrade(gradeEditText.getText().toString());
            }else {
                syllabusItem.setGrade(" ");
            }

            syllabusItemArrayList.add(syllabusItem);
        }
        if (!checkWeight()) {
            Toast.makeText(this, "Weight needs to add to 100!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }// end of checkIfValid

    public boolean checkWeight() {
        int weight = 0;
        for (int i = 0; i < mRecyclerView.getChildCount(); i++) {
            View view = mRecyclerView.getChildAt(i);
            EditText weightEditText = view.findViewById(R.id.weight);
            try{
                weight += Integer.parseInt(weightEditText.getText().toString());
            }catch (NumberFormatException e){
                weight += 0;
            }
        }

        if (weight != 100)
            return false;

        return true;
    }

    public ArrayList<SyllabusItem> getList(){

            syllabusItem = new SyllabusItem();
            for(int i = 0; i < mRecyclerView.getChildCount(); i++){
                View view = mRecyclerView.getChildAt(i);
                EditText nameEditText = view.findViewById(R.id.name);
                EditText weightEditText = view.findViewById(R.id.weight);
                EditText gradeEditText = view.findViewById(R.id.grade);
                syllabusItem.setItemName(nameEditText.getText().toString());
                syllabusItem.setWeight(weightEditText.getText().toString());
                if(gradeEditText.getText().toString().equals(" ")){
                    syllabusItem.setGrade(" ");
                }else{
                    syllabusItem.setGrade(gradeEditText.getText().toString());
                }
                syllabusItemArrayList.add(syllabusItem);
            }

        for(SyllabusItem item : syllabusItemArrayList){
            Log.i("ITEMS: " , item.toString());
        }

        return syllabusItemArrayList;
    }

    @SuppressLint("SetTextI18n")
    public void calculate() {

        double targetGrade = Double.parseDouble(targetGradeEditText.getText().toString());
        double currentGrade;
        double needGrade;
        double result = 0.0;
        double points;
        double totalPoints = 0.0;

        for (int i = 0; i < mRecyclerView.getChildCount(); i++) {
            View view = mRecyclerView.getChildAt(i);
            EditText nameEditText = view.findViewById(R.id.name);
            EditText weightEditText = view.findViewById(R.id.weight);
            EditText gradeEditText = view.findViewById(R.id.grade);

            syllabusItemName[i] = nameEditText.getText().toString();

            if (gradeEditText.getText().toString().equals("")) {
                gradeEditText.setText("need");
            } else if(gradeEditText.getText().toString().contains("need")){
                gradeEditText.setText("need");
            }else {
                gradeNum[i] = Double.parseDouble(gradeEditText.getText().toString());
                weightNum[i] = Double.parseDouble(weightEditText.getText().toString());
            }

            points = (weightNum[i] / 100.0) * gradeNum[i];
            result += points;
            totalPoints += weightNum[i];

        }

        currentGrade = (result / totalPoints) * 100.0;
        currentGradeTextView.setText((int) Math.floor(currentGrade) + "%");

        studentClass.setCurrentGrade((int) currentGrade);
        studentClass.setTargetGrade((int) targetGrade);


        for (int i = 0; i < mRecyclerView.getChildCount(); i++) {

            View view = mRecyclerView.getChildAt(i);
            EditText gradeEditText = view.findViewById(R.id.grade);

            if(gradeEditText.getText().toString().contains("need")){
                if (result == 0) {
                    needGrade = targetGrade;
                    String temp = String.valueOf((int) Math.ceil(needGrade));
                    gradeEditText.setText(temp + "%");
                } else if (targetGrade > result && result != 0) {
                    needGrade = ((targetGrade - result) / (100 - totalPoints)) * 100.0;
                    String temp = String.valueOf((int) Math.ceil(needGrade));
                    gradeEditText.setText("need " + temp + "%");
                } else {
                    needGrade = 0;
                    String temp = String.valueOf((int) Math.ceil(needGrade));
                    gradeEditText.setText("need " + temp + "%");
                }
                syllabusItemArrayList.get(i).setGrade("need " + (int)Math.ceil(needGrade));
                //syllabusItem.setGrade("need " + needGrade);
                //Log.i("NEED GRADE" , needGrade + "");

            }
        }
        studentClass.setsItems(syllabusItemArrayList);
        for(SyllabusItem item : syllabusItemArrayList){
            Log.i("ITEMS: " , item.toString());
        }
        totalPoints = 0;
        result = 0;
        points = 0;

    } //end of calculate

    public void clear() {

        currentGradeTextView.setText("");

        for (int i = 0; i < mRecyclerView.getChildCount(); i++) {
            View view = mRecyclerView.getChildAt(i);
            EditText gradeEditText = view.findViewById(R.id.grade);
            EditText weightEditText = view.findViewById(R.id.weight);
            gradeEditText.setText("");
            weightEditText.setText("");

        }

    }

}