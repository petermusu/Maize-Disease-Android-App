package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout;

import java.nio.MappedByteBuffer;
import java.util.HashMap;
import java.util.Map;

import android.content.res.AssetFileDescriptor;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;

import org.opencv.core.Mat;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
import org.tensorflow.lite.support.image.TensorImageUtils;
import org.tensorflow.lite.support.tensorbuffer.TensorBufferFloat;
import java.io.IOException;
import java.nio.ByteBuffer;


public class MainActivity3 extends AppCompatActivity {
    private Button predictbtn;
    private Button classifybutton;
    private TextView tv;
    private ImageView imgview;
    private Bitmap img;
    private RelativeLayout pdfViewLayout;
    private TextView predictionTextView;
    private WebView webview;

    // Declare a variable for the image Bitmap
    private Bitmap imageBitmap;

    // Load the TensorFlow Lite model from a file
    Interpreter tflite = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Find the UI elements by their IDs
        predictbtn = findViewById(R.id.button4);
        classifybutton = findViewById(R.id.button5);
        imgview = findViewById(R.id.imageView1);
        tv = findViewById(R.id.predictionTextView);

        // Set the background color
        View view = this.getWindow().getDecorView();
        view.setBackgroundResource(R.color.cream_yellow);

        // Retrieve the image Bitmap from the extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            imageBitmap = extras.getParcelable("imageBitmap");
        }

        //fitting bitmap contents with image view
        imgview = (ImageView) findViewById(R.id.imageView1);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.my_image);
        imgview.setImageBitmap(bitmap);

        // Load the TensorFlow Lite model from the asset file
        try {
            tflite = new Interpreter(loadModelFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = getAssets().openFd("model.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        try {
            return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Set an OnClickListener for the predict button
    public <ImagePreprocessor> void predict(View view) {
        // Preprocess the bitmap (resize, normalize, etc.) as required by the model
        ImagePreprocessor preprocessor = new ImagePreprocessor(new Mat(imageBitmap));
        Mat inputImage = preprocessor.preprocess();

        // Create an input Tensor for the model
        TensorBuffer inputTensorBuffer = new TensorBufferFloat(inputImage.size(), null);
        inputTensorBuffer.loadArray(inputImage);


        // Create an output Tensor for the model
                int[] outputShape = tflite.getOutputTensor(0).shape();
                DataType outputDataType = tflite.getOutputTensor(0).dataType();
                TensorBuffer outputTensorBuffer = TensorBuffer.createFixedSize(outputShape, outputDataType);

                // Run the model with the input Tensor and get the output Tensor
                tflite.run(inputTensorBuffer.getBuffer(), outputTensorBuffer.getBuffer().rewind());

                // Process the output Tensor (e.g. argmax to get the predicted class)
                float[] output = outputTensorBuffer.getFloatArray();
                String predictedClass = predictDisease(output);

                // Display the prediction to the user (e.g. using a Toast)
                Toast.makeText(MainActivity3.this, "Prediction: " + predictedClass, Toast.LENGTH_SHORT).show();
            }

    private String predictDisease(float[] output) {
        return null;
    }
});
    }

public class MainActivity extends AppCompatActivity {

    private TextView predictionTextView;
    private Button classifyButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the views
        predictionTextView = findViewById(R.id.predictionTextView);
        classifyButton = findViewById(R.id.button5);

        // Set on click listener for classify button
        classifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the prediction string from the TextView
                String prediction = predictionTextView.getText().toString();

                // Create a map that maps each prediction string to the corresponding PDF file path
                Map<String, String> predictionToPdfMap = new HashMap<>();
                predictionToPdfMap.put("Healthy", "file:///android_asset/healthy.pdf");
                predictionToPdfMap.put("Disease 1", "file:///android_asset/Commonrust.pdf");
                predictionToPdfMap.put("Disease 2", "file:///android_asset/CornGrayLeafSpot.pdf");
                predictionToPdfMap.put("Disease 3", "file:///android_asset/Cornblight.pdf");

                // Get the corresponding PDF file path from the map
                String pdfFilePath = predictionToPdfMap.get(prediction);

                // Open the PDF document using an Intent
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(pdfFilePath), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        // Load the model file
        MappedByteBuffer modelFile = null;
        try {
            modelFile = loadModelFile(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get the disease names from string resources
        String disease1CommonName = getResources().getString(R.string.disease1_common_name);
        String disease1ScientificName = getResources().getString(R.string.disease1_scientific_name);
        String disease2CommonName = getResources().getString(R.string.disease2_common_name);
        String disease2ScientificName = getResources().getString(R.string.disease2_scientific_name);
        String disease3CommonName = getResources().getString(R.string.disease3_common_name);
        String disease3ScientificName = getResources().getString(R.string.disease3_scientific_name);

        // Get the model output and convert it to a string
        float[] output = {...}; // Get the model output as an array of floats
        String prediction = ""; // Initialize the prediction string
        if (output[3] > output[0] && output[3] > output[1] && output[3] > output[2]) {
            prediction = "Healthy";
        } else if (output[1] > output[0] && output[1] > output[2] && output[1] > output[3]) {
            prediction = "Disease 1";
        } else if (output[2] > output[0] && output[2] > output[1] && output[2] > output[3]) {
            prediction = "Disease 2";
        } else if (output[0] > output[1] && output[0] > output[2] && output[0] > output[3]) {
            prediction = "Disease 3";
        }

        // Display the prediction in a TextView with formatting
        if (prediction.equals("Healthy")) {
            predictionTextView.setText("Prediction: Healthy");
        } else if (prediction.equals("Disease 1") || prediction.equals("Disease 2") || prediction.equals("Disease 3"))

            public class MainActivity extends AppCompatActivity {
        // Declare predictionToPdfMap as a class member variable
        private Map<String, String> predictionToPdfMap = new HashMap<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // Initialize predictionToPdfMap in onCreate method
            predictionToPdfMap.put("Healthy", "file:///android_asset/healthy.pdf");
            predictionToPdfMap.put("Disease 1", "file:///android_asset/Commonrust.pdf");
            predictionToPdfMap.put("Disease 2", "file:///android_asset/CornGrayLeafSpot.pdf");
            predictionToPdfMap.put("Disease 3", "file:///android_asset/Cornblight.pdf");

            // Find WebView in the layout file
            WebView webView = findViewById(R.id.webview);

            // Get the prediction string from the Intent
            Intent intent = getIntent();
            String prediction = intent.getStringExtra("prediction");

            // Get the corresponding PDF file path from the map
            String pdfPath = predictionToPdfMap.get(prediction);

            // Load the PDF file into the WebView
            webView.loadUrl(pdfPath);
        }
    }

}