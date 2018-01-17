package com.mycompany.asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView txvMessage;
    private Button btnCancel;
    private Button btnSort;
    private ProgressBar pgbProgreso;
    private static final int MAX_LENGHT = 2000;
    private int[] numbers = new int[MAX_LENGHT];
    private int progreso = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvMessage = findViewById(R.id.txvMessage);
        btnCancel = findViewById(R.id.btnCancel);
        btnSort = findViewById(R.id.btnSort);
        pgbProgreso = findViewById(R.id.pgbProgreso);
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

    private void bubbleSort() {
        int aux;
        for(int i = 0; i < numbers.length - 1; i++) {
            for (int j = i + 1; j < numbers.length - 1; j++) {
                if (numbers[i] > numbers[j])
                {
                    aux = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = aux;
                    progreso++;
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
            bubbleSort();
            // Si no se cancela la operación se actualizará la barra de progreso
            if(!isCancelled())
                publishProgress(Math.round((progreso * 100) / numbers.length));

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            txvMessage.setText(values[0] + "%");
        }

        /**
         * Método que se ejecuta cuando se cancela la tarea
         * @param aVoid
         */
        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
            btnCancel.setVisibility(View.INVISIBLE);
            btnSort.setEnabled(true);
            txvMessage.setText("Operación cancelada");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            btnCancel.setVisibility(View.INVISIBLE);
            btnSort.setEnabled(true);
            txvMessage.setText("Operación terminada");
        }
    }
}
