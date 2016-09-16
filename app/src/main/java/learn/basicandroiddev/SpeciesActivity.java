package learn.basicandroiddev;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jerry on 9/5/2016.
 *
 *  TODO: make species use portrait and landscape for slide-out keyboards
 */
public class SpeciesActivity extends Activity
{
    public static final int SPECIES_MAX_RESULTS = 8;
    public static final int SPECIES_MAX_LETTERS = 8;

    public static int currentSpecies = 0;
    public static int[] resultValue = {0,0,0,0,0,0,0,0};
    public static int resultSelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_species);

        currentSpecies = 0;
        resultSelected = -1;
        resultValue[0] = 0;
        resultValue[1] = 0;
        resultValue[2] = 0;
        resultValue[3] = 0;
        resultValue[4] = 0;
        resultValue[5] = 0;
        resultValue[6] = 0;
        resultValue[7] = 0;

        EditText searchBar = (EditText) findViewById(R.id.species_input);

        searchBar.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        searchBar.addTextChangedListener(new TextWatcher()
                                         {
                                             @Override
                                             public void beforeTextChanged(CharSequence s, int start, int count, int after)
                                             {
                                                 // update text fields
                                                 TextView result1 = (TextView) findViewById(R.id.species_result1);
                                                 TextView result2 = (TextView) findViewById(R.id.species_result2);
                                                 TextView result3 = (TextView) findViewById(R.id.species_result3);
                                                 TextView result4 = (TextView) findViewById(R.id.species_result4);
                                                 TextView result5 = (TextView) findViewById(R.id.species_result5);
                                                 TextView result6 = (TextView) findViewById(R.id.species_result6);
                                                 TextView result7 = (TextView) findViewById(R.id.species_result7);
                                                 TextView result8 = (TextView) findViewById(R.id.species_result7);

                                                 result1.setText("");
                                                 result2.setText("");
                                                 result3.setText("");
                                                 result4.setText("");
                                                 result5.setText("");
                                                 result6.setText("");
                                                 result7.setText("");
                                                 result8.setText("");
                                             }

                                             @Override
                                             public void onTextChanged(CharSequence s, int start, int before, int count)
                                             {
                                             }

                                             @Override
                                             public void afterTextChanged(Editable s)
                                             {
                                                 if(s.length() == 0)
                                                     return;

                                                 String subString = s.toString();
                                                 subString = Character.toUpperCase(subString.charAt(0)) + subString.substring(1);

                                                 if(subString.length() > SPECIES_MAX_LETTERS)
                                                 {
                                                     subString = subString.substring(0, SPECIES_MAX_LETTERS);
                                                     EditText searchBar = (EditText) findViewById(R.id.species_input);
                                                     searchBar.setText(subString);

                                                     searchBar.setSelection(0, SPECIES_MAX_LETTERS);
                                                     Toast.makeText(SpeciesActivity.this, "Maximum 8 characters", Toast.LENGTH_SHORT).show();
                                                 }

                                                 // reset values
                                                 resultValue[0] = 0;
                                                 resultValue[1] = 0;
                                                 resultValue[2] = 0;
                                                 resultValue[3] = 0;
                                                 resultValue[4] = 0;
                                                 resultValue[5] = 0;
                                                 resultValue[6] = 0;
                                                 resultValue[7] = 0;
                                                 resultSelected = -1;
                                                 boolean isCurrentStillInResult = false;

                                                 // search pokemon by name
                                                 int numResults = 0;
                                                 for (int i = 1; i < PokeData.POKEDATA_NUMBER_POKEMON; i++)
                                                 {
                                                     if(subString.length() <= PokeData.Name[i].length())
                                                     {
                                                         if (PokeData.Name[i].startsWith(subString))
                                                         {
                                                             resultValue[numResults] = i;
                                                             numResults++;
                                                         }
                                                     }

                                                     if (numResults == SPECIES_MAX_RESULTS)
                                                         break;
                                                 }

                                                 TextView[] result = new TextView[SPECIES_MAX_RESULTS];
                                                 result[0] = (TextView) findViewById(R.id.species_result1);
                                                 result[1] = (TextView) findViewById(R.id.species_result2);
                                                 result[2] = (TextView) findViewById(R.id.species_result3);
                                                 result[3] = (TextView) findViewById(R.id.species_result4);
                                                 result[4] = (TextView) findViewById(R.id.species_result5);
                                                 result[5] = (TextView) findViewById(R.id.species_result6);
                                                 result[6] = (TextView) findViewById(R.id.species_result7);
                                                 result[7] = (TextView) findViewById(R.id.species_result8);

                                                 // change all back to unselected
                                                 updateResultColor(-1);

                                                 // populate result text views
                                                 for(int i = 0; i < SPECIES_MAX_RESULTS; i++)
                                                 {
                                                     result[i].setText("");
                                                     result[i].setClickable(false);

                                                     if(resultValue[i] == 0)
                                                         break;

                                                     String string = "#" + String.valueOf(resultValue[i]) + " " + PokeData.Name[resultValue[i]];
                                                     result[i].setText(string);
                                                     result[i].setClickable(true);

                                                     if(resultValue[i] == currentSpecies)
                                                     {
                                                         isCurrentStillInResult = true;
                                                         result[i].setBackgroundColor(Color.parseColor("#D58585"));
                                                     }
                                                 }

                                                 // auto select if only one result
                                                 if(resultValue[1] == 0)
                                                 {
                                                     currentSpecies = resultValue[0];
                                                     isCurrentStillInResult = true;
                                                     updateResultColor(0);
                                                 }

                                                 if(!isCurrentStillInResult)
                                                     currentSpecies = 0;
                                             } // end public void afterTextChanged(Editable s)
                                         }
        );

        searchBar.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchBar, InputMethodManager.SHOW_IMPLICIT);
    } // end onCreate()

    public void onOpenIME(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        EditText searchBar = (EditText) findViewById(R.id.species_input);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        searchBar.setSelection(searchBar.getText().length());
        imm.showSoftInput(searchBar, InputMethodManager.SHOW_IMPLICIT);
    }

    // return the value input
    public void onSpeciesConfirm(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent goingBack = new Intent();

        goingBack.putExtra("returnSpecies", currentSpecies);

        setResult(RESULT_OK, goingBack);

        finish();
    }

    // cancel input
    public void onSpeciesCancel(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent goingBack = new Intent();

        setResult(RESULT_CANCELED, goingBack);

        finish();
    }

    public void updateResultColor(int slot)
    {
        TextView result1 = (TextView) findViewById(R.id.species_result1);
        TextView result2 = (TextView) findViewById(R.id.species_result2);
        TextView result3 = (TextView) findViewById(R.id.species_result3);
        TextView result4 = (TextView) findViewById(R.id.species_result4);
        TextView result5 = (TextView) findViewById(R.id.species_result5);
        TextView result6 = (TextView) findViewById(R.id.species_result6);
        TextView result7 = (TextView) findViewById(R.id.species_result7);
        TextView result8 = (TextView) findViewById(R.id.species_result8);

        result1.setBackgroundColor(Color.parseColor("#858585"));
        result2.setBackgroundColor(Color.parseColor("#858585"));
        result3.setBackgroundColor(Color.parseColor("#858585"));
        result4.setBackgroundColor(Color.parseColor("#858585"));
        result5.setBackgroundColor(Color.parseColor("#858585"));
        result6.setBackgroundColor(Color.parseColor("#858585"));
        result7.setBackgroundColor(Color.parseColor("#858585"));
        result8.setBackgroundColor(Color.parseColor("#858585"));

        switch(slot)
        {
            default: break;
            case 0: result1.setBackgroundColor(Color.parseColor("#D58585")); break;
            case 1: result2.setBackgroundColor(Color.parseColor("#D58585")); break;
            case 2: result3.setBackgroundColor(Color.parseColor("#D58585")); break;
            case 3: result4.setBackgroundColor(Color.parseColor("#D58585")); break;
            case 4: result5.setBackgroundColor(Color.parseColor("#D58585")); break;
            case 5: result6.setBackgroundColor(Color.parseColor("#D58585")); break;
            case 6: result7.setBackgroundColor(Color.parseColor("#D58585")); break;
            case 7: result8.setBackgroundColor(Color.parseColor("#D58585")); break;
        }

    }

    public void onResult1(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        currentSpecies = resultValue[0];
        updateResultColor(0);
    }
    public void onResult2(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        currentSpecies = resultValue[1];
        updateResultColor(1);
    }
    public void onResult3(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        currentSpecies = resultValue[2];
        updateResultColor(2);
    }
    public void onResult4(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        currentSpecies = resultValue[3];
        updateResultColor(3);
    }
    public void onResult5(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        currentSpecies = resultValue[4];
        updateResultColor(4);
    }
    public void onResult6(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        currentSpecies = resultValue[5];
        updateResultColor(5);
    }
    public void onResult7(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        currentSpecies = resultValue[6];
        updateResultColor(6);
    }
    public void onResult8(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        currentSpecies = resultValue[7];
        updateResultColor(7);
    }

}
