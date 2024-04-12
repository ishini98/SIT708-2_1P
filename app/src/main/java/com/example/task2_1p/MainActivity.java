package com.example.unitconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private Spinner categorySpinner, sourceUnitSpinner, destinationUnitSpinner;
    private EditText inputValueEditText;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categorySpinner = findViewById(R.id.categorySpinner);
        sourceUnitSpinner = findViewById(R.id.sourceUnitSpinner);
        destinationUnitSpinner = findViewById(R.id.destinationUnitSpinner);
        inputValueEditText = findViewById(R.id.inputValueEditText);
        android.widget.Button convertButton = findViewById(R.id.convertButton);
        resultTextView = findViewById(R.id.resultTextView);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.conversion_categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                updateUnitSpinners(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        convertButton.setOnClickListener(v -> {
            String inputText = inputValueEditText.getText().toString().trim();

            if (inputText.isEmpty() || !isValidDouble(inputText)) {
                Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();
            } else {
                convert();
            }
        });
    }

    private void updateUnitSpinners(String category) {
        int unitArrayResourceId = R.array.length_units;
        if (category.equals("Weight")) {
            unitArrayResourceId = R.array.weight_units;
        } else if (category.equals("Temperature")) {
            unitArrayResourceId = R.array.temperature_units;
        }

        ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(this,
                unitArrayResourceId, android.R.layout.simple_spinner_item);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceUnitSpinner.setAdapter(unitAdapter);
        destinationUnitSpinner.setAdapter(unitAdapter);
    }

    private boolean isValidDouble(String string) {
        try {
            double value = Double.parseDouble(string);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void convert() {
        String category = categorySpinner.getSelectedItem().toString();
        String sourceUnit = sourceUnitSpinner.getSelectedItem().toString();
        String destinationUnit = destinationUnitSpinner.getSelectedItem().toString();

        double inputValue = Double.parseDouble(inputValueEditText.getText().toString());
        double resultValue = convertValue(inputValue, sourceUnit, destinationUnit, category);
        resultTextView.setText(formatResult(inputValue, sourceUnit, resultValue, destinationUnit));
    }

    private double convertValue(double inputValue, String sourceUnit, String destinationUnit, String category) {
        double resultValue = 0;

        switch (category) {
            case "Length":
                resultValue = convertLength(inputValue, sourceUnit, destinationUnit);
                break;
            case "Weight":
                resultValue = convertWeight(inputValue, sourceUnit, destinationUnit);
                break;
            case "Temperature":
                resultValue = convertTemperature(inputValue, sourceUnit, destinationUnit);
                break;
        }

        return resultValue;
    }

    private double convertLength(double value, String sourceUnit, String destinationUnit) {
        double resultValue = 0;
    
        switch (sourceUnit) {
            case "inch":
                switch (destinationUnit) {
                    case "inch":
                        resultValue = value;
                        break;
                    case "foot":
                        resultValue = value / 12;
                        break;
                    case "yard":
                        resultValue = value / 36;
                        break;
                    case "mile":
                        resultValue = value / 63360;
                        break;
                    case "cm":
                        resultValue = value * 2.54;
                        break;
                    case "km":
                        resultValue = value * 0.0000254;
                        break;
                }
                break;
            case "foot":
                switch (destinationUnit) {
                    case "inch":
                        resultValue = value * 12;
                        break;
                    case "foot":
                        resultValue = value;
                        break;
                    case "yard":
                        resultValue = value / 3;
                        break;
                    case "mile":
                        resultValue = value / 5280;
                        break;
                    case "cm":
                        resultValue = value * 30.48;
                        break;
                    case "km":
                        resultValue = value * 0.0003048;
                        break;
                }
                break;
            case "yard":
                switch (destinationUnit) {
                    case "inch":
                        resultValue = value * 36;
                        break;
                    case "foot":
                        resultValue = value * 3;
                        break;
                    case "yard":
                        resultValue = value;
                        break;
                    case "mile":
                        resultValue = value / 1760;
                        break;
                    case "cm":
                        resultValue = value * 91.44;
                        break;
                    case "km":
                        resultValue = value * 0.0009144;
                        break;
                }
                break;
            case "mile":
                switch (destinationUnit) {
                    case "inch":
                        resultValue = value * 63360;
                        break;
                    case "foot":
                        resultValue = value * 5280;
                        break;
                    case "yard":
                        resultValue = value * 1760;
                        break;
                    case "mile":
                        resultValue = value;
                        break;
                    case "cm":
                        resultValue = value * 160934.4;
                        break;
                    case "km":
                        resultValue = value * 1.60934;
                        break;
                }
                break;
            case "cm":
                switch (destinationUnit) {
                    case "inch":
                        resultValue = value * 0.393701;
                        break;
                    case "foot":
                        resultValue = value * 0.0328084;
                        break;
                    case "yard":
                        resultValue = value * 0.0109361;
                        break;
                    case "mile":
                        resultValue = value * 0.0000062137;
                        break;
                    case "cm":
                        resultValue = value;
                        break;
                    case "km":
                        resultValue = value * 0.00001;
                        break;
                }
                break;
            case "km":
                switch (destinationUnit) {
                    case "inch":
                        resultValue = value * 39370.1;
                        break;
                    case "foot":
                        resultValue = value * 3280.84;
                        break;
                    case "yard":
                        resultValue = value * 1093.61;
                        break;
                    case "mile":
                        resultValue = value * 0.621371;
                        break;
                    case "cm":
                        resultValue = value * 100000;
                        break;
                    case "km":
                        resultValue = value;
                        break;
                }
                break;
        }
        return resultValue;
    }
    

    private double convertWeight(double value, String sourceUnit, String destinationUnit) {
        double resultValue = 0;
    
        switch (sourceUnit) {
            case "pound":
                switch (destinationUnit) {
                    case "pound":
                        resultValue = value;
                        break;
                    case "ounce":
                        resultValue = value * 16;
                        break;
                    case "ton":
                        resultValue = value / 2000;
                        break;
                    case "kg":
                        resultValue = value * 0.453592;
                        break;
                    case "g":
                        resultValue = value * 453.592;
                        break;
                }
                break;
            case "ounce":
                switch (destinationUnit) {
                    case "pound":
                        resultValue = value / 16;
                        break;
                    case "ounce":
                        resultValue = value;
                        break;
                    case "ton":
                        resultValue = value / 32000;
                        break;
                    case "kg":
                        resultValue = value * 0.0283495;
                        break;
                    case "g":
                        resultValue = value * 28.3495;
                        break;
                }
                break;
            case "ton":
                switch (destinationUnit) {
                    case "pound":
                        resultValue = value * 2000;
                        break;
                    case "ounce":
                        resultValue = value * 32000;
                        break;
                    case "ton":
                        resultValue = value;
                        break;
                    case "kg":
                        resultValue = value * 907.185;
                        break;
                    case "g":
                        resultValue = value * 907185;
                        break;
                }
                break;
            case "kg":
                switch (destinationUnit) {
                    case "pound":
                        resultValue = value / 0.453592;
                        break;
                    case "ounce":
                        resultValue = value / 0.0283495;
                        break;
                    case "ton":
                        resultValue = value / 907.185;
                        break;
                    case "kg":
                        resultValue = value;
                        break;
                    case "g":
                        resultValue = value * 1000;
                        break;
                }
                break;
            case "g":
                switch (destinationUnit) {
                    case "pound":
                        resultValue = value / 453.592;
                        break;
                    case "ounce":
                        resultValue = value / 28.3495;
                        break;
                    case "ton":
                        resultValue = value / 907185;
                        break;
                    case "kg":
                        resultValue = value / 1000;
                        break;
                    case "g":
                        resultValue = value;
                        break;
                }
                break;
        }
        return resultValue;
    }
    

    private double convertTemperature(double value, String sourceUnit, String destinationUnit) {
        double resultValue = 0;
    
        switch (sourceUnit) {
            case "Celsius":
                switch (destinationUnit) {
                    case "Celsius":
                        resultValue = value;
                        break;
                    case "Fahrenheit":
                        resultValue = (value * 1.8) + 32;
                        break;
                    case "Kelvin":
                        resultValue = value + 273.15;
                        break;
                }
                break;
            case "Fahrenheit":
                switch (destinationUnit) {
                    case "Celsius":
                        resultValue = (value - 32) / 1.8;
                        break;
                    case "Fahrenheit":
                        resultValue = value;
                        break;
                    case "Kelvin":
                        resultValue = (value - 32) / 1.8 + 273.15;
                        break;
                }
                break;
            case "Kelvin":
                switch (destinationUnit) {
                    case "Celsius":
                        resultValue = value - 273.15;
                        break;
                    case "Fahrenheit":
                        resultValue = (value - 273.15) * 1.8 + 32;
                        break;
                    case "Kelvin":
                        resultValue = value;
                        break;
                }
                break;
        }
    
        return resultValue;
    }
    

    private String formatResult(double inputValue, String sourceUnit, double resultValue, String destinationUnit) {
        DecimalFormat df = new DecimalFormat("#.####");
        return "Result: " + inputValue + " " + sourceUnit + " = " + df.format(resultValue) + " " + destinationUnit;
    }
}
