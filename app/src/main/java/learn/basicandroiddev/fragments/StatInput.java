package learn.basicandroiddev.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import learn.basicandroiddev.MainActivity;
import learn.basicandroiddev.PokeObject;
import learn.basicandroiddev.R;
import learn.basicandroiddev.StatData;

/**
 * Created by Jerry on 9/21/2016.
 */
public class StatInput extends Fragment
{
    //int[] addedStats = new int[6];
    //int[] addedEvs = new int[6];

    //TextView evInputButton;

    Button hpPlusButton;
    Button hpMinusButton;
    TextView hpStatInput;
    //TextView hpEvInput;
    TextView hpDisplayEvs;
    TextView hpDisplayIvs;
    TextView hpTotalStat;

    Button atkPlusButton;
    Button atkMinusButton;
    TextView atkStatInput;
    //TextView atkEvInput;
    TextView atkDisplayEvs;
    TextView atkDisplayIvs;
    TextView atkTotalStat;

    Button defPlusButton;
    Button defMinusButton;
    TextView defStatInput;
    //TextView defEvInput;
    TextView defDisplayEvs;
    TextView defDisplayIvs;
    TextView defTotalStat;

    Button spAtkPlusButton;
    Button spAtkMinusButton;
    TextView spAtkStatInput;
    //TextView spAtkEvInput;
    TextView spAtkDisplayEvs;
    TextView spAtkDisplayIvs;
    TextView spAtkTotalStat;

    Button spDefPlusButton;
    Button spDefMinusButton;
    TextView spDefStatInput;
    //TextView spDefEvInput;
    TextView spDefDisplayEvs;
    TextView spDefDisplayIvs;
    TextView spDefTotalStat;

    Button speedPlusButton;
    Button speedMinusButton;
    TextView speedStatInput;
    //TextView speedEvInput;
    TextView speedDisplayEvs;
    TextView speedDisplayIvs;
    TextView speedTotalStat;

    @Nullable
    @Override
    // create references to views that need to be accessed from outside
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View rootView;
        System.out.println("Display StatInput");

        MainActivity mainActivity = (MainActivity) getActivity();

        if(mainActivity.leftHandedMode)
        // if(savedInstanceState.getBoolean("leftHandedMode", false))
        {
            System.out.println("fragment_stat_input");
            rootView = inflater.inflate(R.layout.fragment_stat_input, container, false);
        }
        else
        {
            System.out.println("fragment_stat_input_right");
            rootView = inflater.inflate(R.layout.fragment_stat_input_right, container, false);
        }

        //evInputButton = (TextView) rootView.findViewById(R.id.main_go_to_effort_input);

        hpPlusButton =    (Button) rootView.findViewById(R.id.main_hp_add_button);
        hpMinusButton =   (Button) rootView.findViewById(R.id.main_hp_subtract_button);
        hpStatInput =   (TextView) rootView.findViewById(R.id.main_hp_stat_input_text);
        //hpEvInput =     (TextView) rootView.findViewById(R.id.main_hp_ev_input_text);
        hpDisplayEvs =  (TextView) rootView.findViewById(R.id.main_hp_ev_text);
        hpDisplayIvs =  (TextView) rootView.findViewById(R.id.main_hp_iv_range_text);
        hpTotalStat =   (TextView) rootView.findViewById(R.id.main_hp_total_text);

        atkPlusButton =    (Button) rootView.findViewById(R.id.main_atk_add_button);
        atkMinusButton =   (Button) rootView.findViewById(R.id.main_atk_subtract_button);
        atkStatInput =   (TextView) rootView.findViewById(R.id.main_atk_stat_input_text);
        //atkEvInput =     (TextView) rootView.findViewById(R.id.main_atk_ev_input_text);
        atkDisplayEvs =  (TextView) rootView.findViewById(R.id.main_atk_ev_text);
        atkDisplayIvs =  (TextView) rootView.findViewById(R.id.main_atk_iv_range_text);
        atkTotalStat =   (TextView) rootView.findViewById(R.id.main_atk_total_text);

        defPlusButton =    (Button) rootView.findViewById(R.id.main_def_add_button);
        defMinusButton =   (Button) rootView.findViewById(R.id.main_def_subtract_button);
        defStatInput =   (TextView) rootView.findViewById(R.id.main_def_stat_input_text);
        //defEvInput =     (TextView) rootView.findViewById(R.id.main_def_ev_input_text);
        defDisplayEvs =  (TextView) rootView.findViewById(R.id.main_def_ev_text);
        defDisplayIvs =  (TextView) rootView.findViewById(R.id.main_def_iv_range_text);
        defTotalStat =   (TextView) rootView.findViewById(R.id.main_def_total_text);

        spAtkPlusButton =    (Button) rootView.findViewById(R.id.main_spa_add_button);
        spAtkMinusButton =   (Button) rootView.findViewById(R.id.main_spa_subtract_button);
        spAtkStatInput =   (TextView) rootView.findViewById(R.id.main_spa_stat_input_text);
        //spAtkEvInput =     (TextView) rootView.findViewById(R.id.main_spa_ev_input_text);
        spAtkDisplayEvs =  (TextView) rootView.findViewById(R.id.main_spa_ev_text);
        spAtkDisplayIvs =  (TextView) rootView.findViewById(R.id.main_spa_iv_range_text);
        spAtkTotalStat =   (TextView) rootView.findViewById(R.id.main_spa_total_text);

        spDefPlusButton =    (Button) rootView.findViewById(R.id.main_spd_add_button);
        spDefMinusButton =   (Button) rootView.findViewById(R.id.main_spd_subtract_button);
        spDefStatInput =   (TextView) rootView.findViewById(R.id.main_spd_stat_input_text);
        //spDefEvInput =     (TextView) rootView.findViewById(R.id.main_spd_ev_input_text);
        spDefDisplayEvs =  (TextView) rootView.findViewById(R.id.main_spd_ev_text);
        spDefDisplayIvs =  (TextView) rootView.findViewById(R.id.main_spd_iv_range_text);
        spDefTotalStat =   (TextView) rootView.findViewById(R.id.main_spd_total_text);

        speedPlusButton =    (Button) rootView.findViewById(R.id.main_spe_add_button);
        speedMinusButton =   (Button) rootView.findViewById(R.id.main_spe_subtract_button);
        speedStatInput =   (TextView) rootView.findViewById(R.id.main_spe_stat_input_text);
        //speedEvInput =     (TextView) rootView.findViewById(R.id.main_spe_ev_input_text);
        speedDisplayEvs =  (TextView) rootView.findViewById(R.id.main_spe_ev_text);
        speedDisplayIvs =  (TextView) rootView.findViewById(R.id.main_spe_iv_range_text);
        speedTotalStat =   (TextView) rootView.findViewById(R.id.main_spe_total_text);

        return rootView;
    }

    //@Override
    //public void onStart()
    //{
    //    super.onStart();

    //    MainActivity rootView = (MainActivity) getActivity();

    //    resetEvInput();
    //    resetIvDisplay();
    //    updateStatTotal(rootView.POKEMON);
    //    updateEvDisplay(rootView.POKEMON);
    //    updateIvDisplay(rootView.POKEMON);

    //}

    @Override
    public void onResume()
    {
        super.onResume();

        MainActivity rootView = (MainActivity) getActivity();

        resetEvInput();
        resetIvDisplay();
        updateStatTotal(rootView.POKEMON);
        updateEvDisplay(rootView.POKEMON, rootView.ADDED_EVS);
        updateIvDisplay(rootView.POKEMON);
        System.out.println("fragStatInput.onResume()");
    }

    //public void resetAddedStats()
    //{
    //    addedStats[StatData.STAT_HP] = 0;
    //    addedStats[StatData.STAT_ATK] = 0;
    //    addedStats[StatData.STAT_DEF] = 0;
    //    addedStats[StatData.STAT_SPATK] = 0;
    //    addedStats[StatData.STAT_SPDEF] = 0;
    //    addedStats[StatData.STAT_SPEED] = 0;
    //}
    //public void resetAddedEvs()
    //{
    //    addedEvs[StatData.STAT_HP] = 0;
    //    addedEvs[StatData.STAT_ATK] = 0;
    //    addedEvs[StatData.STAT_DEF] = 0;
    //    addedEvs[StatData.STAT_SPATK] = 0;
    //    addedEvs[StatData.STAT_SPDEF] = 0;
    //    addedEvs[StatData.STAT_SPEED] = 0;
    //}

    //public int getAddedStat(int statEnum)
    //{
    //    return addedStats[statEnum];
    //}

    //public int getAddedEv(int statEnum)
    //{
    //    return addedEvs[statEnum];
    //}

    //public void setAddedStat(int value, int statEnum)
    //{
    //    addedStats[statEnum] = value;
    //}
    //public void setAddedEv(int value, int statEnum)
    //{
    //    addedEvs[statEnum] = value;
    //}

    // set wether or not EvInput views are clickable
    public void setClickableEvInput(boolean isClickable)
    {
        hpDisplayEvs.setClickable(isClickable);
        atkDisplayEvs.setClickable(isClickable);
        defDisplayEvs.setClickable(isClickable);
        spAtkDisplayEvs.setClickable(isClickable);
        spDefDisplayEvs.setClickable(isClickable);
        speedDisplayEvs.setClickable(isClickable);

        hpDisplayEvs.setEnabled(isClickable);
        atkDisplayEvs.setEnabled(isClickable);
        defDisplayEvs.setEnabled(isClickable);
        spAtkDisplayEvs.setEnabled(isClickable);
        spDefDisplayEvs.setEnabled(isClickable);
        speedDisplayEvs.setEnabled(isClickable);
    }

    // sets evInput to default text
    public void resetEvInput()
    {
        String ev = "+EV ";

        hpDisplayEvs.setText(ev);
        atkDisplayEvs.setText(ev);
        defDisplayEvs.setText(ev);
        spAtkDisplayEvs.setText(ev);
        spDefDisplayEvs.setText(ev);
        speedDisplayEvs.setText(ev);
    }

    public void resetStatInput()
    {
        TextView[] view = new TextView[6];

        view[StatData.STAT_HP] = hpStatInput;
        view[StatData.STAT_ATK] = atkStatInput;
        view[StatData.STAT_DEF] = defStatInput;
        view[StatData.STAT_SPATK] = spAtkStatInput;
        view[StatData.STAT_SPDEF] = spDefStatInput;
        view[StatData.STAT_SPEED] = speedStatInput;

        String stat = "+stat ";

        for(int i = 0; i < 6; i++)
        {
            view[i].setText(stat);
            view[i].setTextColor(Color.parseColor("#303030"));
            view[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        }
    }

    // updates ev input text to match the pokeObject evs
    public void updateEvDisplay(PokeObject pokeObject, int[] ADDED_EVS)
    {
        TextView[] view = new TextView[6];
        view[StatData.STAT_HP] = hpDisplayEvs;
        view[StatData.STAT_ATK] = atkDisplayEvs;
        view[StatData.STAT_DEF] = defDisplayEvs;
        view[StatData.STAT_SPATK] = spAtkDisplayEvs;
        view[StatData.STAT_SPDEF] = spDefDisplayEvs;
        view[StatData.STAT_SPEED] = speedDisplayEvs;

        for(int i = StatData.STAT_HP; i < StatData.NUMBER_STATS; i++)
        {
            if(pokeObject.EVs[i] > 0 || ADDED_EVS[i] > 0)
            {
                String text;

                if(pokeObject.EVs[i] < 0)
                    text = "0";
                else
                    text = String.valueOf(pokeObject.EVs[i]);

                // add extra evs if they are higher than 0
                if(ADDED_EVS[i] > 0)
                    text = text + "+" + String.valueOf(ADDED_EVS[i]);

                view[i].setText(text);
                System.out.println("fragStatInput.updateEvDisplay() if(pokeObject.EVs[i] > 0)" +
                        " EVs[" + i + "] = " + pokeObject.EVs[i] +  "ADDED_EVS[" + i + "]= " + ADDED_EVS[i]);
            }
            else
            {
                view[i].setText("---");
                System.out.println("fragStatInput.updateEvDisplay() ELSE(pokeObject.EVs[i] > 0)" +
                        " EVs[" + i + "] = " + pokeObject.EVs[i] +  "ADDED_EVS[" + i + "]= " + ADDED_EVS[i]);
            }
        }
    }

    public void resetIvDisplay()
    {
        TextView[] view = new TextView[6];

        view[StatData.STAT_HP] = hpDisplayIvs;
        view[StatData.STAT_ATK] = atkDisplayIvs;
        view[StatData.STAT_DEF] = defDisplayIvs;
        view[StatData.STAT_SPATK] = spAtkDisplayIvs;
        view[StatData.STAT_SPDEF] = spDefDisplayIvs;
        view[StatData.STAT_SPEED] = speedDisplayIvs;

        String dash = " - ";

        for(int i = 0; i < 6; i++)
        {
            view[i].setText(dash);
            view[i].setTextColor(Color.parseColor("#000000"));
        }
    }

    public void updateIvDisplay(PokeObject pokeObject)
    {
        TextView[] view = new TextView[6];

        view[StatData.STAT_HP] = hpDisplayIvs;
        view[StatData.STAT_ATK] = atkDisplayIvs;
        view[StatData.STAT_DEF] = defDisplayIvs;
        view[StatData.STAT_SPATK] = spAtkDisplayIvs;
        view[StatData.STAT_SPDEF] = spDefDisplayIvs;
        view[StatData.STAT_SPEED] = speedDisplayIvs;

        String dash = " - ";

        for(int i = 0; i < 6; i++)
        {
            // check if min/max are set
            if (pokeObject.IVsMin[0] != -2)
            {
                // check if precise iv is set
                if (pokeObject.IVs[i] != -3)
                {
                    view[i].setText(String.valueOf(pokeObject.IVs[i]));
                    view[i].setTextColor(Color.parseColor("#000000"));
                    System.out.println("fragStatInput.updateIvDisplay() if(pokeObject.IVs[i] != -3) IVs[i] = " + pokeObject.IVs[i]);
                }
                // if IV[] is not set show min/max range
                else
                {
                    String string = String.valueOf(pokeObject.IVsMin[i]) + dash + String.valueOf(pokeObject.IVsMax[i]);
                    view[i].setText(string);

                    view[i].setTextColor(Color.parseColor("#dd0000"));
                    System.out.println("fragStatInput.updateIvDisplay() ELSE(pokeObject.IVs[i] != -3) IVs[i] = " + pokeObject.IVs[i]);
                }

                System.out.println("fragStatInput.updateIvDisplay() if(pokeObject.IVsMin[i] != -2) IVsMin[i] = " + pokeObject.IVs[i]);
            }
            else
            {
                view[i].setText(dash);

                view[i].setTextColor(Color.parseColor("#dd0000"));
                System.out.println("fragStatInput.updateIvDisplay() ELSE(pokeObject.IVsMin[i] != -2) IVsMin[i] = " + pokeObject.IVs[i]);
            }
        }
    }

    public void setClickableStatTotal(boolean isClickable)
    {
        hpTotalStat.setClickable(isClickable);
        atkTotalStat.setClickable(isClickable);
        defTotalStat.setClickable(isClickable);
        spAtkTotalStat.setClickable(isClickable);
        spDefTotalStat.setClickable(isClickable);
        speedTotalStat.setClickable(isClickable);

        hpTotalStat.setEnabled(isClickable);
        atkTotalStat.setEnabled(isClickable);
        defTotalStat.setEnabled(isClickable);
        spAtkTotalStat.setEnabled(isClickable);
        spDefTotalStat.setEnabled(isClickable);
        speedTotalStat.setEnabled(isClickable);
    }

    public void resetStatTotal()
    {
        String dash = "---";

        hpTotalStat.setText(dash);
        atkTotalStat.setText(dash);
        defTotalStat.setText(dash);
        spAtkTotalStat.setText(dash);
        spDefTotalStat.setText(dash);
        speedTotalStat.setText(dash);
    }

    public void updateStatTotal(PokeObject pokeObject)
    {
        System.out.println("fragStatInput.updateStatTotal() pokeObject.stats[STAT_HP] = " + pokeObject.stats[StatData.STAT_HP]);

        TextView[] view = new TextView[6];
        view[StatData.STAT_HP] = hpTotalStat;
        view[StatData.STAT_ATK] = atkTotalStat;
        view[StatData.STAT_DEF] = defTotalStat;
        view[StatData.STAT_SPATK] = spAtkTotalStat;
        view[StatData.STAT_SPDEF] = spDefTotalStat;
        view[StatData.STAT_SPEED] = speedTotalStat;

        for (int i = StatData.STAT_HP; i < StatData.NUMBER_STATS; i++)
        {
            if(pokeObject.stats[i] != -1)
                view[i].setText(String.valueOf(pokeObject.stats[i]));
            else
                view[i].setText("---");
        }
    }

    public void updateAddedStatDisplay(int value, int statEnum)
    {
        String valueString;

        if(value == -1000)
            valueString = "+stat";
        else
            valueString = String.valueOf(value);

        // TODO: AddedEvText text formatting
        //ivText.setTextColor(Color.parseColor("#000000"));
        //ivText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        //ivText.setText(String.valueOf(ADDED_STATS[StatData.STAT_HP]));

        switch(statEnum)
        {
            default: System.out.println("switch(statEnum) default(" + statEnum + ")"); break;
            case StatData.STAT_HP: hpStatInput.setText(valueString); break;
            case StatData.STAT_ATK: atkStatInput.setText(valueString); break;
            case StatData.STAT_DEF: defStatInput.setText(valueString); break;
            case StatData.STAT_SPATK: spAtkStatInput.setText(valueString); break;
            case StatData.STAT_SPDEF: spDefStatInput.setText(valueString); break;
            case StatData.STAT_SPEED: speedStatInput.setText(valueString); break;
        }
    }
}
