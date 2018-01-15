package com.mycompany.asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView txvMessage;
    private static final int MAX_LENGHT = 900;
    private int[] numbers = new int[MAX_LENGHT];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvMessage = (TextView)findViewById(R.id.txvMessage);
        generateNumbers();
    }

    private void generateNumbers() {
        Random random = new Random();
        for(int i = 0; i < MAX_LENGHT; i++)
            numbers[i] = random.nextInt();
    }

    /**
     * Método que se ejecuta cuando se da al botón ordenar
     * @param v
     */
    public void onClickSort(View v) {
        /** OPCIÓN 1: Se obtiene el mensaje de error ANR
        bubbleSort(numbers);
        txvMessage.setText("Operación terminada"); */
        /** OPCIÓN 2: Crear un hilo para la ejecución del método
         * bubbleSort y posterior actualización del mensaje
         */
        execWithThread();
    }

    private void execWithThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                bubbleSort(numbers);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txvMessage.setText("Operación terminada");
                    }
                });
            }
        }).start();
    }

    /**
     * Método que ordena un array mediante el algoritmo de la burbuja simple
     * @param numbers
      */
    private void bubbleSort(int[] numbers) {
        int aux;
        for(int i = 0; i < numbers.length - 1; i++) {
            for (int j = i + 1; j < numbers.length - 1; j++) {
                if (numbers[i] > numbers[j])
                {
                    aux = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = aux;
                }
            }
        }
    }

    private class SimpleAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //publishProgress();
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
        }
    }
}
