package com.mycompany.asynctask;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by usuario on 17/01/18.
 */

public class HiddenFragment extends Fragment {

    private TaskCallbacks mCallback;

    /**
     * Interfaz de comunicación con la Activity
     */
    static interface TaskCallbacks {
        void onPreExecute();
        void onProgressUpdate(int i);
        void onCancelled();
        void onPostExecute();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    public class ProgressBarTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }
    }
}
