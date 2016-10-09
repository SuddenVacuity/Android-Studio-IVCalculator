package learn.basicandroiddev;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Jerry on 9/4/2016.
 */
public class NumberActivity extends Activity
{
    public int maxValue = 0;
    public boolean allowNegative = true;
    public String numberPurpose= "dummy text";
/*
    // set output value to previous value
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        Intent calledNumberInput = getIntent();

        maxValue = calledNumberInput.getExtras().getInt("callNumber");
        allowNegative = calledNumberInput.getExtras().getBoolean("allowNegative");
        numberPurpose = calledNumberInput.getExtras().getString("numberPurpose");

        TextView numberDescription = (TextView) findViewById(R.id.num_description);
        numberDescription.setText(numberPurpose);

        TextView minus = (TextView) findViewById(R.id.number_pad_minus);
        if(allowNegative)
        {
            minus.setText("-");
            minus.setClickable(true);
        }
        else
        {
            minus.setText("");
            minus.setClickable(false);
        }

        TextView next = (TextView) findViewById(R.id.num_next);

        if(calledNumberInput.getExtras().getBoolean("chainInput", false))
            next.setVisibility(View.VISIBLE);
        else
            next.setVisibility(View.GONE);

    }

    // return the value input
    public void onNumberInputConfirm(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        TextView numberOutputTV =
                (TextView) findViewById(R.id.number_output);

        String numberOutput = numberOutputTV.getText().toString();

        Intent goingBack = new Intent();

        goingBack.putExtra("returnNumber", numberOutput);
        goingBack.putExtra("chainInput", false);

        setResult(RESULT_OK, goingBack);

        finish();
    }

    // return the value input
    public void onNumberInputNext(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        TextView numberOutputTV =
                (TextView) findViewById(R.id.number_output);

        String numberOutput = numberOutputTV.getText().toString();

        Intent goingBack = new Intent();

        goingBack.putExtra("returnNumber", numberOutput);
        goingBack.putExtra("chainInput", true);

        setResult(RESULT_OK, goingBack);

        finish();
    }

    // cancel input
    public void onNumberInputCancel(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent goingBack = new Intent();

        setResult(RESULT_CANCELED, goingBack);

        finish();
    }

    public void onNumberMinus(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        TextView numberOutputTV =
                (TextView) findViewById(R.id.number_output);

        String value = numberOutputTV.getText().toString();

        if(value.startsWith("-"))
            value = value.substring(1, value.length());
        else if(!value.equals("0"))
            value = "-" + value;

        numberOutputTV.setText(value);
    }

    public void onNumberBackSpace(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        TextView numberOutputTV =
                (TextView) findViewById(R.id.number_output);

        String numberOutput = numberOutputTV.getText().toString();

        final int strLen = numberOutput.length();

        if(strLen > 1)
        {
            if(strLen == 2 && numberOutput.startsWith("-"))
                numberOutput = "0";
            else
                numberOutput = numberOutput.substring(0, strLen - 1);
        }
        else
        {
            numberOutput = "0";
        }

        numberOutputTV.setText(numberOutput);
    }

    // function for onNumberInput functions
    public void addNumber(String value)
    {
        TextView numberOutputTV =
                (TextView) findViewById(R.id.number_output);

        String numberOutput = numberOutputTV.getText().toString();

        int inputValue = Integer.parseInt(numberOutput);

        if(inputValue == 0)
        {
            numberOutputTV.setText(value);
        }
        else
        {
            numberOutput = numberOutput + value;

            inputValue = Integer.parseInt(numberOutput);

            if(inputValue > maxValue)
                numberOutput = String.valueOf(maxValue);

            numberOutputTV.setText(numberOutput);
        }

    }

    public void onNumberInput9(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        addNumber("9");
    }

    public void onNumberInput8(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        addNumber("8");
    }

    public void onNumberInput7(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        addNumber("7");
    }

    public void onNumberInput6(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        addNumber("6");
    }

    public void onNumberInput5(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        addNumber("5");
    }

    public void onNumberInput4(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        addNumber("4");
    }

    public void onNumberInput3(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        addNumber("3");
    }

    public void onNumberInput2(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        addNumber("2");
    }

    public void onNumberInput1(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        addNumber("1");
    }

    public void onNumberInput0(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        addNumber("0");
    }

    */
}
