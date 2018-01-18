package com.mycompany.asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_LENGTH = 500;
    private int[] numbers = new int[MAX_LENGTH];

    private TextView txvMessage;
    private Button btnCancel;
    private Button btnSort;
    private SimpleAsyncTask simpleAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvMessage = findViewById(R.id.txvMessage);
        btnCancel = findViewById(R.id.btnCancel);
        btnSort = findViewById(R.id.btnSort);
    }

    private void generateNumbers() {
        /*
        Random random = new Random();
        for(int i = 0; i < MAX_LENGTH; i++)
            numbers[i] = random.nextInt();
        */
        for (int i = 0; i < numbers.length; i++)
            numbers[i] = (int) Math.floor(Math.random() * MAX_LENGTH);
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
        execWithThread(); */
        /** Opción 3: Con AsyncTask */
        simpleAsyncTask = new SimpleAsyncTask();
        simpleAsyncTask.execute();
    }

    public void onCancelSort(View view) {
        /** Opción 3: Con AsyncTask */
        simpleAsyncTask.cancel(true);
    }

    private void execWithThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                bubbleSort();
                /** Opcion 2.1: Esta es la única forma de tener acceso a la vista
                Esta opción no es limpia */
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txvMessage.setText("Operación terminada");
                        Toast.makeText(MainActivity.this, "Fin", Toast.LENGTH_SHORT).show();
                    }
                });
                /** Opcion 2.2:
                txvMessage.setText("Operación terminada"); */
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
                }
            }
        }
    }

    private class SimpleAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnCancel.setVisibility(View.VISIBLE);
            btnSort.setEnabled(false);
            generateNumbers();
        }

        /**
         * Es el único método que se ejecuta en el hilo principal.
         * La comunicación con la interfaz gráfica se ejecuta con publishProgress.
         * En el método principal se va a ejecutar onProgressUpdate.
         * En la interfaz gráfica se ejecutan todos menos doInBackground
         * (onPreExecute, onProgressUpdate, publishProgress, onPostExecute, onCancelled).
         * @param
         * @return
         */
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
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //Este caso es de un único parámetro
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
