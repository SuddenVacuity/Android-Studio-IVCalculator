package learn.basicandroiddev.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import learn.basicandroiddev.MainActivity;
import learn.basicandroiddev.R;
import learn.basicandroiddev.StatData;

/**
 * Created by Jerry on 9/30/2016.
 */
public class EffortInput extends Fragment
{
    public static final int NUMBER_SLOTS = 6;

    //TextView statInputButton;
    TextView holdItemDisplay;
    TextView pokerusDisplay;

    TextView slot1ObjectDisplay;
    TextView slot1ObjectCountDisplay;
    TextView hpEvTotalText;
    TextView hpEvTotalNumber;

    TextView slot2ObjectDisplay;
    TextView slot2ObjectCountDisplay;
    TextView atkEvTotalText;
    TextView atkEvTotalNumber;

    TextView slot3ObjectDisplay;
    TextView slot3ObjectCountDisplay;
    TextView defEvTotalText;
    TextView defEvTotalNumber;

    TextView slot4ObjectDisplay;
    TextView slot4ObjectCountDisplay;
    TextView spAtkEvTotalText;
    TextView spAtkEvTotalNumber;

    TextView slot5ObjectDisplay;
    TextView slot5ObjectCountDisplay;
    TextView spDefEvTotalText;
    TextView spDefEvTotalNumber;

    TextView slot6ObjectDisplay;
    TextView slot6ObjectCountDisplay;
    TextView speedEvTotalText;
    TextView speedEvTotalNumber;

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
        {
            System.out.println("fragment_effort_values");
            rootView = inflater.inflate(R.layout.fragment_effort_values, container, false);
        }
        else
        {
            System.out.println("fragment_effort_values_right - not implemented");
            rootView = inflater.inflate(R.layout.fragment_effort_values, container, false);
        }

        //statInputButton = (TextView) rootView.findViewById(R.id.ev_go_to_stat_input);
        holdItemDisplay = (TextView) rootView.findViewById(R.id.ev_hold_item_display);
        pokerusDisplay = (TextView) rootView.findViewById(R.id.ev_pokerus_text);

        slot1ObjectDisplay = (TextView) rootView.findViewById(R.id.ev_hp_item_display);
        slot1ObjectCountDisplay = (TextView) rootView.findViewById(R.id.ev_hp_object_count);
        hpEvTotalText = (TextView) rootView.findViewById(R.id.ev_hp_total_added_text);
        hpEvTotalNumber = (TextView) rootView.findViewById(R.id.ev_hp_total_added_display);

        slot2ObjectDisplay = (TextView) rootView.findViewById(R.id.ev_atk_item_display);
        slot2ObjectCountDisplay = (TextView) rootView.findViewById(R.id.ev_atk_object_count);
        atkEvTotalText = (TextView) rootView.findViewById(R.id.ev_atk_total_added_text);
        atkEvTotalNumber = (TextView) rootView.findViewById(R.id.ev_atk_total_added_display);

        slot3ObjectDisplay = (TextView) rootView.findViewById(R.id.ev_def_item_display);
        slot3ObjectCountDisplay = (TextView) rootView.findViewById(R.id.ev_def_object_count);
        defEvTotalText = (TextView) rootView.findViewById(R.id.ev_def_total_added_text);
        defEvTotalNumber = (TextView) rootView.findViewById(R.id.ev_def_total_added_display);

        slot4ObjectDisplay = (TextView) rootView.findViewById(R.id.ev_spa_item_display);
        slot4ObjectCountDisplay = (TextView) rootView.findViewById(R.id.ev_spa_object_count);
        spAtkEvTotalText = (TextView) rootView.findViewById(R.id.ev_spa_total_added_text);
        spAtkEvTotalNumber = (TextView) rootView.findViewById(R.id.ev_spa_total_added_display);

        slot5ObjectDisplay = (TextView) rootView.findViewById(R.id.ev_spd_item_display);
        slot5ObjectCountDisplay = (TextView) rootView.findViewById(R.id.ev_spd_object_count);
        spDefEvTotalText = (TextView) rootView.findViewById(R.id.ev_spd_total_added_text);
        spDefEvTotalNumber = (TextView) rootView.findViewById(R.id.ev_spd_total_added_display);

        slot6ObjectDisplay = (TextView) rootView.findViewById(R.id.ev_spe_item_display);
        slot6ObjectCountDisplay = (TextView) rootView.findViewById(R.id.ev_spe_object_count);
        speedEvTotalText = (TextView) rootView.findViewById(R.id.ev_spe_total_added_text);
        speedEvTotalNumber = (TextView) rootView.findViewById(R.id.ev_spe_total_added_display);

        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        //MainActivity rootView = (MainActivity) getActivity();

        //resetEvInput();
        //resetIvDisplay();
        //updateStatTotal(rootView.POKEMON);
        //updateEvDisplay(rootView.POKEMON, rootView.ADDED_EVS);
        //updateIvDisplay(rootView.POKEMON);
        System.out.println("fragEffortValues.onResume()");
    }

    public void updateObjectDisplay(String string, int position)
    {
        switch(position)
        {
            default: break;
            case 0: slot1ObjectDisplay.setText(string); break;
            case 1: slot2ObjectDisplay.setText(string); break;
            case 2: slot3ObjectDisplay.setText(string); break;
            case 3: slot4ObjectDisplay.setText(string); break;
            case 4: slot5ObjectDisplay.setText(string); break;
            case 5: slot6ObjectDisplay.setText(string); break;
        }
    }

    public void updateObjectCountDisplay(int count, int position)
    {
        switch(position)
        {
            default: break;
            case 0: slot1ObjectDisplay.setText(String.valueOf(count)); break;
            case 1: slot2ObjectDisplay.setText(String.valueOf(count)); break;
            case 2: slot3ObjectDisplay.setText(String.valueOf(count)); break;
            case 3: slot4ObjectDisplay.setText(String.valueOf(count)); break;
            case 4: slot5ObjectDisplay.setText(String.valueOf(count)); break;
            case 5: slot6ObjectDisplay.setText(String.valueOf(count)); break;
        }
    }

    public void updateEvTotalDisplay(int value, int statEnum)
    {
        final String string = "+" + String.valueOf(value);

        switch(statEnum)
        {
            default: break;
            case StatData.STAT_HP: hpEvTotalNumber.setText(string); break;
            case StatData.STAT_ATK: atkEvTotalNumber.setText(string); break;
            case StatData.STAT_DEF: defEvTotalNumber.setText(string); break;
            case StatData.STAT_SPATK: spAtkEvTotalNumber.setText(string); break;
            case StatData.STAT_SPDEF: spDefEvTotalNumber.setText(string); break;
            case StatData.STAT_SPEED: speedEvTotalNumber.setText(string); break;
        }
    }


    public void resetObjectDisplay(int position)
    {
        final String string = "- - - - -";

        slot1ObjectDisplay.setText(string);
        slot2ObjectDisplay.setText(string);
        slot3ObjectDisplay.setText(string);
        slot4ObjectDisplay.setText(string);
        slot5ObjectDisplay.setText(string);
        slot6ObjectDisplay.setText(string);

    }

    public void resetObjectCountDisplay(int position)
    {
        final String string = "0";

        slot1ObjectDisplay.setText(String.valueOf(string));
        slot2ObjectDisplay.setText(String.valueOf(string));
        slot3ObjectDisplay.setText(String.valueOf(string));
        slot4ObjectDisplay.setText(String.valueOf(string));
        slot5ObjectDisplay.setText(String.valueOf(string));
        slot6ObjectDisplay.setText(String.valueOf(string));

    }

    public void setPokerus(boolean hasPokerus)
    {
        if(hasPokerus)
            pokerusDisplay.setBackgroundColor(Color.parseColor("#be6ebe"));
        else
            pokerusDisplay.setBackgroundColor(Color.parseColor("#858585"));
    }

    public void setHeldItem(int itemEnum)
    {
        // TODO: EffortInput.setHeldItem() images

        switch (itemEnum)
        {
            default:
                break;
            case StatData.HELD_NONE:
                holdItemDisplay.setText("-");
                holdItemDisplay.setBackgroundColor(Color.parseColor("#858585"));
                break;
            case StatData.HELD_MACHO_BRACE:
                holdItemDisplay.setText("M");
                holdItemDisplay.setBackgroundColor(Color.parseColor("#64a082"));
                break;
            case StatData.HELD_POWER_WEIGHT:
                if (MainActivity.pokemonGeneration < 3) {break;}
                holdItemDisplay.setText("h");
                holdItemDisplay.setBackgroundColor(Color.parseColor("#3cf050"));
                break;
            case StatData.HELD_BRACER:
                if (MainActivity.pokemonGeneration < 3) {break;}
                holdItemDisplay.setText("a");
                holdItemDisplay.setBackgroundColor(Color.parseColor("#f05050"));
                break;
            case StatData.HELD_BELT:
                if (MainActivity.pokemonGeneration < 3) {break;}
                holdItemDisplay.setText("d");
                holdItemDisplay.setBackgroundColor(Color.parseColor("#f0b428"));
                break;
            case StatData.HELD_LENS:
                if (MainActivity.pokemonGeneration < 3) {break;}
                holdItemDisplay.setText("a");
                holdItemDisplay.setBackgroundColor(Color.parseColor("#e68cf0"));
                break;
            case StatData.HELD_BAND:
                if (MainActivity.pokemonGeneration < 3) {break;}
                holdItemDisplay.setText("d");
                holdItemDisplay.setBackgroundColor(Color.parseColor("#faf06e"));
                break;
            case StatData.HELD_ANKLET:
                if (MainActivity.pokemonGeneration < 3) {break;}
                holdItemDisplay.setText("s");
                holdItemDisplay.setBackgroundColor(Color.parseColor("#5af0f0"));
                break;
        }
    }

}
