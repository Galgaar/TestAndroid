package com.example.rdoison.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProgramActivity extends AppCompatActivity {

    Map<String, RunSession> program;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
    }

    private void initProgram() {
        RunSession runSession = new RunSession();
        RunElement runElement = new RunElement();
        runElement.setType(RunElement.ElementType.WARMUP);
        List<RunElement> runElementList = new ArrayList<RunElement>();
        runElement.setLength(300);
        runElementList.add(runElement);
        runSession.setName("Week 1");
        runSession.setSession(runElementList);
    }
}
