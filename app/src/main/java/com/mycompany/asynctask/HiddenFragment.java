package com.mycompany.asynctask;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by usuario on 17/01/18.
 */

public class HiddenFragment extends Fragment {

    public static final String TAG = "HiddenFragment";
    private static final int MAX_LENGTH = 1000;
    private int[] numbers = new int[MAX_LENGTH];
    private TaskCallbacks mCallback;
    private ProgressBarTask progressBarTask;

    /**
     * Interfaz de comunicación con la Activity
     */
    interface TaskCallbacks {
        void onPreExecute();
        void onProgressUpdate(Integer... values);
        void onCancelled();
        void onPostExecute(Void avoid);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //Se inicia la tarea
        progressBarTask = new ProgressBarTask();
        progressBarTask.execute();
    }

    public void cancelTask(){
        progressBarTask.cancel(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (TaskCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity + " debe implementar TaskCallbacks");
        }
    }

    public class ProgressBarTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            generateNumbers();
            if(mCallback != null)
                mCallback.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Si no se cancela la operación se actualizará la barra de progreso
            int maxMovimientos = (numbers.length * (numbers.length - 1)) / 2;
            int movimientos = 1;
            for (int i = 0; i < numbers.length; i++)
                for (int j = 1; j < (numbers.length - i); j++) {
                    publishProgress((int) ((100 * movimientos++)/ (float) maxMovimientos));
                    if (numbers[j - 1] > numbers[j]) {
                        int aux = numbers[j - 1];
                        numbers[j - 1] = numbers[j];
                        numbers[j] = aux;
                    }
                    if (isCancelled())
                        return null;
                }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(mCallback != null)
                mCallback.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(mCallback != null)
                mCallback.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if(mCallback != null)
                mCallback.onCancelled();
        }

        private void generateNumbers() {
            for (int i = 0; i < numbers.length; i++)
                numbers[i] = (int) Math.floor(Math.random() * MAX_LENGTH);
        }
    }
}
