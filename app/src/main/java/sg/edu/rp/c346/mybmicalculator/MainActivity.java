package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    TextView tvDate;
    TextView tvBMI;
    TextView tvEnhancement;
    Button calculate;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvEnhancement = findViewById(R.id.textViewEnhancement);
        calculate = findViewById(R.id.buttonCalculate);
        reset = findViewById(R.id.buttonReset);

        Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
        final String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                (now.get(Calendar.MONTH)+1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                now.get(Calendar.MINUTE);



        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float FloatWeight = Float.parseFloat(etWeight.getText().toString());
                float FloatHeight = Float.parseFloat(etHeight.getText().toString());
                final float BMI = FloatWeight / (FloatHeight * FloatHeight);

                if (BMI < 18.5) {
                    tvEnhancement.setText("You are underweight");
                }
                else if (BMI >= 18.5 && BMI <= 24.9){
                    tvEnhancement.setText("Your BMI is normal");
                }
                else if (BMI >= 25 && BMI <= 29.9) {
                    tvEnhancement.setText("You are overweight");
                }
                else {
                    tvEnhancement.setText("You are obese");
                }

                tvDate.setText("Last Calculated Date:" + datetime);
                tvBMI.setText("Last Calculated BMI:" + BMI);
                etWeight.setText("");
                etHeight.setText("");

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.putString("date", datetime);
                prefEdit.putFloat("bmi", BMI);
                prefEdit.commit();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etWeight.setText("");
                etHeight.setText("");
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.clear();
                prefEdit.commit();

            }
        });



    }

    @Override
    protected void onPause() {
        super.onPause();

        // Step 1a: Get the user input from the EditText and store it into a variable
        if (!"".equals(etWeight.getText().toString()) && !"".equals(Float.parseFloat(etHeight.getText().toString()))) {
            float FloatWeight = Float.parseFloat(etWeight.getText().toString());
            float FloatHeight = Float.parseFloat(etHeight.getText().toString());

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor prefEdit = prefs.edit();
            prefEdit.putFloat("weight",FloatWeight);
            prefEdit.putFloat("height",FloatHeight);
            prefEdit.commit();

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Step 2a: Obtain an instance of the SharedPreferences
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // step 2b: Retrieve the saved data from the SharedPreferences object
        Float msg = prefs.getFloat("weight",0);
        Float msg1 = prefs.getFloat("height",0);
        String msg2 = prefs.getString("date","");
        Float msg3 = prefs.getFloat("bmi",0);


        // step 2c: Update the UI element with the value
        etWeight.setText(msg.toString());
        etHeight.setText(msg1.toString());

        tvDate.setText("Last Calculated Date:" + msg2);
        tvBMI.setText("Last Calculated BMI:" + msg3);



    }
}
