package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.os.Bundle;

import org.opencv.core.Mat;
import org.opencv.dnn.Model;

import java.util.HashMap;

public class MainActivity4 extends AppCompatActivity {

    private TextView mostFrequentDiseaseText;
    private int[][] diseaseTable;
    private String[] diseaseNames = {"Disease 1", "Disease 2", "Disease 3", "Healthy"};
    private TextView[][] diseaseTableViews = new TextView[2][4];
    private TextView healthyTextView;
    private View predictbtn;
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        TableLayout tableLayout = findViewById(R.id.tablereport);

        // Create a new row for the table
        TableRow row1 = new TableRow(this);
        row1.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        // Create four text views to display data in the columns
        TextView column1 = new TextView(this);
        column1.setText("Column 1");
        column1.setGravity(Gravity.CENTER);
        row1.addView(column1);

        TextView column2 = new TextView(this);
        column2.setText("Column 2");
        column2.setGravity(Gravity.CENTER);
        row1.addView(column2);

        TextView column3 = new TextView(this);
        column3.setText("Column 3");
        column3.setGravity(Gravity.CENTER);
        row1.addView(column3);

        TextView column4 = new TextView(this);
        column4.setText("Column 4");
        column4.setGravity(Gravity.CENTER);
        row1.addView(column4);

        // Add the first row to the table
        tableLayout.addView(row1);

        // Create a second row for the table
        TableRow row2 = new TableRow(this);
        row2.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        // Create four text views to display data in the columns
        TextView data1 = new TextView(this);
        data1.setText("Data 1");
        data1.setGravity(Gravity.CENTER);
        row2.addView(data1);

        TextView data2 = new TextView(this);
        data2.setText("Data 2");
        data2.setGravity(Gravity.CENTER);
        row2.addView(data2);

        TextView data3 = new TextView(this);
        data3.setText("Data 3");
        data3.setGravity(Gravity.CENTER);
        row2.addView(data3);

        TextView data4 = new TextView(this);
        data4.setText("Data 4");
        data4.setGravity(Gravity.CENTER);
        row2.addView(data4);

        // Add the second row to the table
        tableLayout.addView(row2);

        // Set the table layout as the content view for the activity
        setContentView(tableLayout);

        // Define a HashMap to store the integer values for each disease
        HashMap<String, Integer> diseaseCount = new HashMap<>();

// Initialize the values to 0 for each disease
        diseaseCount.put("Disease 1", 0);
        diseaseCount.put("Disease 2", 0);
        diseaseCount.put("Disease 3", 0);
        diseaseCount.put("Healthy", 0);

// Define a method to update the count for a given disease
        void updateCount; (String String predictedDisease;
        String predictedDisease1 = predictedDisease;{
            // Get the current count for the disease
            int count = diseaseCount.get(predictedDisease);

            // Increment the count by 1
            count++;

            // Update the count in the HashMap
            diseaseCount.put(predictedDisease, count);

            // Update the text view in the corresponding row and column of the table
            TextView countTextView;

            switch (predictedDisease) {
                case "Disease 1":
                    countTextView = findViewById(R.id.disease1_count);
                    break;
                case "Disease 2":
                    countTextView = findViewById(R.id.disease2_count);
                    break;
                case "Disease 3":
                    countTextView = findViewById(R.id.disease3_count);
                    break;
                case "Healthy":
                    countTextView = findViewById(R.id.Healthy_count);
                    break;
                default:
                    // Handle the case where the predicted disease is not recognized
                    return;
            }

            // Set the text of the count text view to the updated count
            countTextView.setText(String.valueOf(count));
        }

        predictbtn.setOnClickListener(View.OnClickListener() {
            public void onClick; Model myMachineLearningModel;
            Mat image;
            (View) {
                    // Get the predicted disease from the machine learning model
                    String predictedDisease = myMachineLearningModel.predict(image);

            // Update the count for the predicted disease in the table
            updateCount(predictedDisease);
            }
        });

        private void updateMostFrequentDisease () {
            int maxCount = 0;
            int maxIndex = -1;
            for (int i = 0; i < 4; i++) {
                if (diseaseTable[0][i] > maxCount) {
                    maxCount = diseaseTable[0][i];
                    maxIndex = i;
                }
            }
            if (maxIndex >= 0) {
                if (maxIndex == 3) {
                    healthyTextView.setText("Seems crops are healthy.");
                    mostFrequentDiseaseText.setText("");
                } else {
                    healthyTextView.setText("");
                    mostFrequentDiseaseText.setText(diseaseNames[maxIndex] + " is the most frequent disease attacking crops this year.");
                }
            }
        }

        void updateHealthyTextView;() {
            int healthyCount = diseaseTable[0][4];
            if (healthyCount > 0) {
                healthyTextView.setText("Number of healthy crops: " + Integer.toString(healthyCount));
            } else {
                healthyTextView.setText("");
            }
        }
    }

    private void updateCount(String predictedDisease) {
    }