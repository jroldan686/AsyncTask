package com.mycompany.asynctask;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HiddenActivity extends AppCompatActivity implements HiddenFragment.TaskCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden);
    }

    public void onClickSort(View view) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(fragment, "HiddenFragment");
        ft.commit();
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onProgressUpdate(int i) {

    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onPostExecute() {

    }
}
