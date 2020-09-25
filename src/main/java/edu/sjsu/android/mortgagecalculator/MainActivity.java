package edu.sjsu.android.mortgagecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private Double seekBarVal = 10.0;
    private String totalAmountBorrowedCal = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar seekbar = findViewById(R.id.seekBarInterestRate);

        //source: https://stackoverflow.com/questions/35571201/how-to-fetch-seekbar-float-value-in-android
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Toast.makeText(getApplicationContext(), "SeekBar progress: " + (double) progress / 10, Toast.LENGTH_SHORT).show();
                seekBarVal = (double) progress / 10;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "SeekBar progress started", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "SeekBar progress stopped", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onClick(View view) {

        //temp variables used
        int totalNumOfMonths = 0;
        Double amountBorrowedtemp = 0.0;
        Double totalMonthlyPay = 0.0;
        Double taxesAndInsuranceVar = 0.0;
        Double interestRateVar = 0.0;
        boolean amountBorrowedCondition = true;
        boolean loanTermCondition = true;

        TextView totalAmountTxt = findViewById(R.id.totalAmountTxt);
        CheckBox checkBoxTaxnInsurance = findViewById(R.id.checkBoxTaxesInsurance);

        //check for amount borrowed text field, REQUIRED
        EditText amountBorrowedTxt = findViewById(R.id.amountBorrowed);
        totalAmountBorrowedCal = amountBorrowedTxt.getText().toString();
        if (amountBorrowedTxt.getText().toString().trim().equalsIgnoreCase("")) {
            amountBorrowedTxt.setError("This field can not be blank");
            amountBorrowedCondition = false;
            totalAmountTxt.setText("");
        }

        //checking if one of the radio button is checked for loan term, REQUIRED
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton radioButtons = findViewById(R.id.radioButton30yrs);
        int radioButtonIdTracker = radioGroup.getCheckedRadioButtonId();

        if (radioButtonIdTracker == -1) {
            radioButtons.setError("Need to select loan term");
            loanTermCondition = false;
            totalAmountTxt.setText("");
        }

        //Checking if amount borrowed and loan term is entered, if so calculate monthly pay
        if (amountBorrowedCondition && loanTermCondition == true) {
//            totalAmountTxt.setText("test");
//            totalAmountTxt.setText("" + totalAmountBorrowedCal);

            amountBorrowedtemp = Double.parseDouble(totalAmountBorrowedCal);

            //calculating the month from the radio button and changing it to an integer value
            totalNumOfMonths = (Integer.valueOf(((RadioButton) findViewById(radioButtonIdTracker)).getText().toString())) * 12;

            //For 0% interest rate when seekbar value is set to 0.0
            if ((seekBarVal.doubleValue() == 0.0)) {

//                totalAmountTxt.setText("interest rate 0%");

                //Checking if checkbox is checked for calculation for Tax and Insurance
                if (checkBoxTaxnInsurance.isChecked()) {

                    //Calculation for Insurance checkBox is CHECKED
//                    totalAmountTxt.setText("Insurance checkbox is checked");

                    taxesAndInsuranceVar = (amountBorrowedtemp * (0.1 / 100));

                    if (radioButtonIdTracker == R.id.radioButton15yrs) {
                        totalMonthlyPay = ((amountBorrowedtemp / totalNumOfMonths) + taxesAndInsuranceVar);
                        totalAmountTxt.setText(String.valueOf(totalMonthlyPay));
                        totalAmountTxt.setText("" + totalMonthlyPay);
                    }
                    if (radioButtonIdTracker == R.id.radioButton20yrs) {
                        totalMonthlyPay = ((amountBorrowedtemp / totalNumOfMonths) + taxesAndInsuranceVar);
                        totalAmountTxt.setText(String.valueOf(totalMonthlyPay));
                        totalAmountTxt.setText("" + totalMonthlyPay);
                    }
                    if (radioButtonIdTracker == R.id.radioButton30yrs) {
                        totalMonthlyPay = ((amountBorrowedtemp / totalNumOfMonths) + taxesAndInsuranceVar);
                        totalAmountTxt.setText(String.valueOf(totalMonthlyPay));
                        totalAmountTxt.setText("" + totalMonthlyPay);
                    }

                } else {

                    //Calculation for Insurance checkBox is NOT checked
//                    totalAmountTxt.setText("Insurance checkbox is not checked");

                    if (radioButtonIdTracker == R.id.radioButton15yrs) {
                        totalMonthlyPay = amountBorrowedtemp / totalNumOfMonths;
                        totalAmountTxt.setText("" + totalMonthlyPay);

                    }
                    if (radioButtonIdTracker == R.id.radioButton20yrs) {
                        totalMonthlyPay = amountBorrowedtemp / totalNumOfMonths;
                        totalAmountTxt.setText("" + totalMonthlyPay);
                    }
                    if (radioButtonIdTracker == R.id.radioButton30yrs) {
                        totalMonthlyPay = amountBorrowedtemp / totalNumOfMonths;
                        totalAmountTxt.setText("" + totalMonthlyPay);
                    }
                }

                //For over 0% interest rate when seekbar value is set between 0 and 20
            } else {

//                totalAmountTxt.setText("Interest rate over 0%");
                //getting value from the seekBar as double
                interestRateVar = seekBarVal / 1200;

                //Condition for checking if the checkbox is checked for tax and insurance calculations
                if (checkBoxTaxnInsurance.isChecked()) {
//                    totalAmountTxt.setText("Insurance checkbox is checked");

                    //getting interest rate of 0.1% of amount borrowed
                    taxesAndInsuranceVar = (amountBorrowedtemp * (0.1 / 100));

                    if (radioButtonIdTracker == R.id.radioButton15yrs) {
                        totalMonthlyPay = (((amountBorrowedtemp) * ((interestRateVar) / ((1) - (Math.pow((1 + interestRateVar), -(totalNumOfMonths)))))) + (taxesAndInsuranceVar));
                        totalAmountTxt.setText("" + totalMonthlyPay);
                    }

                    if (radioButtonIdTracker == R.id.radioButton20yrs) {
                        totalMonthlyPay = (((amountBorrowedtemp) * ((interestRateVar) / ((1) - (Math.pow((1 + interestRateVar), -(totalNumOfMonths)))))) + (taxesAndInsuranceVar));
                        totalAmountTxt.setText("" + totalMonthlyPay);
                    }

                    if (radioButtonIdTracker == R.id.radioButton30yrs) {
                        totalMonthlyPay = (((amountBorrowedtemp) * ((interestRateVar) / ((1) - (Math.pow((1 + interestRateVar), -(totalNumOfMonths)))))) + (taxesAndInsuranceVar));
                        totalAmountTxt.setText("" + totalMonthlyPay);
                    }

                } else {

//                    totalAmountTxt.setText("Insurance checkbox is not checked");

                    if (radioButtonIdTracker == R.id.radioButton15yrs) {
                        totalMonthlyPay = ((amountBorrowedtemp) * ((interestRateVar) / ((1) - (Math.pow((1 + interestRateVar), -(totalNumOfMonths))))));
                        totalAmountTxt.setText("" + totalMonthlyPay);
                    }

                    if (radioButtonIdTracker == R.id.radioButton20yrs) {
                        totalMonthlyPay = ((amountBorrowedtemp) * ((interestRateVar) / ((1) - (Math.pow((1 + interestRateVar), -(totalNumOfMonths))))));
                        totalAmountTxt.setText("" + totalMonthlyPay);
                    }

                    if (radioButtonIdTracker == R.id.radioButton30yrs) {
                        totalMonthlyPay = ((amountBorrowedtemp) * ((interestRateVar) / ((1) - (Math.pow((1 + interestRateVar), -(totalNumOfMonths))))));
                        totalAmountTxt.setText("" + totalMonthlyPay);
                    }
                }
            }
        } else {
            totalAmountTxt.setText("Condition not met for calculations, Enter Loan amount and duration of loan for result");
        }
    }
}