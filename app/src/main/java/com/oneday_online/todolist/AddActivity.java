package com.oneday_online.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText taskname;
    private Button add;
    private Spinner imp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        taskname = (EditText)findViewById(R.id.taskname);
        imp = (Spinner)findViewById(R.id.imp);
        add = (Button)findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (imp.getSelectedItemPosition()){
                    case 0:
                        TodoItem i = new TodoItem(TodoItem.Tags.Faible,taskname.getText().toString());
                        TodoDbHelper.addItem(i,AddActivity.this);
                        break;
                    case 1:
                        TodoItem i2 = new TodoItem(TodoItem.Tags.Faible,taskname.getText().toString());
                        TodoDbHelper.addItem(i2,AddActivity.this);
                        break;
                    case 2:
                        TodoItem i3 = new TodoItem(TodoItem.Tags.Faible,taskname.getText().toString());
                        TodoDbHelper.addItem(i3,AddActivity.this);
                        break;
                }
                finish();
            }
        });
    }
}
