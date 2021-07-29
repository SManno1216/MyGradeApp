package com.mannosteven.mygradeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;

public class NewClassActivity extends AppCompatActivity {

    private int last_pos = -1;
    private int imageID;
    private ExampleItem mExampleItem;
    private GridLayout gridLayout;
    private EditText mEditText;
    private ImageView imageView;
    private Button buttonAdd;

    private int[] images = {R.drawable.ic_android, R.drawable.ic_calculate,
                            R.drawable.ic_science, R.drawable.ic_wheel, R.drawable.ic_pencil,
                            R.drawable.ic_baseline_sports_football_24};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);

        setTitle(" Add New Class");

        initialize();

    }

    public void initialize(){
        mEditText = findViewById(R.id.editText_name);
        buttonAdd = findViewById(R.id.button_finish);
        gridLayout = findViewById(R.id.gridLayout);


        for(int i = 0; i < gridLayout.getChildCount(); i++){
            imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.findViewById(images[i]);
            int finalI = i;
            gridLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(last_pos != -1){
                        gridLayout.getChildAt(last_pos).setSelected(false);
                        gridLayout.getChildAt(last_pos).setBackgroundColor(getResources().getColor(R.color.holo_dark_blue));
                    }
                    v.setSelected(true);
                    v.setBackgroundColor(getResources().getColor(R.color.holo_light_blue));
                    imageID = images[finalI];
                    last_pos = gridLayout.indexOfChild(v);
                }
            });
        }

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditText.getText().toString();
                mExampleItem = new ExampleItem(imageID, name, getResources().getString(R.string.current_grade), new StudentClass());
                Intent intent = new Intent();
                intent.putExtra("Example Item", mExampleItem);
                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }
}