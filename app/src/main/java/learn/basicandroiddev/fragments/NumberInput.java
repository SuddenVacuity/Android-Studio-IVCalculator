package learn.basicandroiddev.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import learn.basicandroiddev.MainActivity;
import learn.basicandroiddev.R;
import learn.basicandroiddev.StatData;

/**
 * Created by Jerry on 9/21/2016.
 */
public class NumberInput extends Fragment
{
    public int maxValue = 0;
    public boolean allowNegative = true;
    public boolean allowShowNextPrev = true;
    String numberOutput = "0";

    TextView minus;
    TextView prev;
    TextView next;

    // set output value to previous value
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_number, container, false);
        System.out.println("Display NumberInput");

        minus = (TextView) rootView.findViewById(R.id.number_pad_minus);
        if (allowNegative)
        {
            minus.setText("-");
            minus.setClickable(true);
            minus.setEnabled(true);
        } else
        {
            minus.setText("");
            minus.setClickable(false);
            minus.setEnabled(false);
        }

        prev = (TextView) rootView.findViewById(R.id.num_prev);
        next = (TextView) rootView.findViewById(R.id.num_next);

        return rootView;

    }

    @Override
    public void onStart()
    {
        super.onStart();
        prev.setEnabled(allowShowNextPrev);
        next.setEnabled(allowShowNextPrev);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        prev.setEnabled(allowShowNextPrev);
        next.setEnabled(allowShowNextPrev);
    }

    public void setOutput(int data)
    {
        numberOutput = String.valueOf(data);
    }

    public int getOutput()
    {
        return Integer.parseInt(numberOutput);
    }
    public String getOutputString()
    {
        return numberOutput;
    }

    public void setupNumberPad(int currentValue, int valueLimit, boolean canBeNegative, boolean allowPrevNext)
    {
        maxValue = valueLimit;
        allowNegative = canBeNegative;
        numberOutput = String.valueOf(currentValue);
        allowShowNextPrev = allowPrevNext;
    }

    public void resetOutput()
    {
        numberOutput = "0";
    }

    //// return the value input
    //public void numberInputNext()
    //{
    //    MainActivity activity = (MainActivity) getActivity();
    //    int currentStat = activity.selectedStat;

    //    if(currentStat == StatData.STAT_SPATK)
    //        next.setEnabled(false);
    //    prev.setEnabled(true);
    //}

    //// return the value input
    //public void numberInputPrev()
    //{
    //    MainActivity activity = (MainActivity) getActivity();
    //    int currentStat = activity.selectedStat;

    //    if(currentStat == StatData.STAT_ATK)
    //        prev.setEnabled(false);
    //    next.setEnabled(true);
    //}

    public void numberInputMinus()
    {
        String value = numberOutput;

        if(value.startsWith("-"))
            value = value.substring(1, value.length());
        else if(!value.equals("0"))
            value = "-" + value;

        numberOutput = value;
    }

    public void numberInputBackspace()
    {
        String numberOutputString = numberOutput;

        final int strLen = numberOutputString.length();

        if(strLen > 1)
        {
            if(strLen == 2 && numberOutputString.startsWith("-"))
                numberOutputString = "0";
            else
                numberOutputString = numberOutputString.substring(0, strLen - 1);
        }
        else
        {
            numberOutputString = "0";
        }

        numberOutput = numberOutputString;
    }

    // function for numberInput functions
    public void addNumber(String value)
    {
        int inputValue = Integer.parseInt(numberOutput);

        if(inputValue == 0)
        {
            numberOutput = value;
            //System.out.println("fragNumber.addNumber(" + value + ") numberOutput(" + numberOutput + ") == 0");
        }
        else
        {
            numberOutput = numberOutput + value;

            inputValue = Integer.parseInt(numberOutput);
            //System.out.println("fragNumber.addNumber(" + value + ") numberOutput(" + numberOutput + ") == 0");

            if(inputValue > maxValue)
            {
                //System.out.println("fragNumber.addNumber(" + value + ") numberOutput(" + numberOutput + ") > maxValue(" + maxValue + ")");
                numberOutput = String.valueOf(maxValue);
            }

        }
    }

}
