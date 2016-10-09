package learn.basicandroiddev.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import learn.basicandroiddev.MainActivity;
import learn.basicandroiddev.PokeObject;
import learn.basicandroiddev.R;
import learn.basicandroiddev.StatData;

/**
 * Created by Jerry on 9/21/2016.
 */
public class StatInfo extends Fragment
{
    public final int INPUT_MODE_UNSET = 0;
    public final int INPUT_MODE_EV = 1;
    public final int INPUT_MODE_ADD_EV = 2;
    public final int INPUT_MODE_STAT = 3;

    private int INPUT_MODE = INPUT_MODE_UNSET;

    TextView hpText;
    TextView hpNumber;
    TextView atkText;
    TextView atkNumber;
    TextView defText;
    TextView defNumber;
    TextView spAtkText;
    TextView spAtkNumber;
    TextView spDefText;
    TextView spDefNumber;
    TextView speedText;
    TextView speedNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_display_stat_info, container, false);

        System.out.println("Display StatInfo");

        hpText = (TextView) rootView.findViewById(R.id.fragment_display_poke_info_hp_text);
        hpNumber = (TextView) rootView.findViewById(R.id.fragment_display_poke_info_hp_number);

        atkText = (TextView) rootView.findViewById(R.id.fragment_display_poke_info_atk_text);
        atkNumber = (TextView) rootView.findViewById(R.id.fragment_display_poke_info_atk_number);

        defText = (TextView) rootView.findViewById(R.id.fragment_display_poke_info_def_text);
        defNumber = (TextView) rootView.findViewById(R.id.fragment_display_poke_info_def_number);

        spAtkText = (TextView) rootView.findViewById(R.id.fragment_display_poke_info_spa_text);
        spAtkNumber = (TextView) rootView.findViewById(R.id.fragment_display_poke_info_spa_number);

        spDefText = (TextView) rootView.findViewById(R.id.fragment_display_poke_info_spd_text);

        spDefNumber = (TextView) rootView.findViewById(R.id.fragment_display_poke_info_spd_number);

        speedText = (TextView) rootView.findViewById(R.id.fragment_display_poke_info_spe_text);
        speedNumber = (TextView) rootView.findViewById(R.id.fragment_display_poke_info_spe_number);

        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        MainActivity rootView = (MainActivity) getActivity();

        highlightTextView(rootView.selectedStat);
        setStatInfo(rootView.POKEMON);

        System.out.println("fragStatInfo.onResume() selected stat = " + rootView.selectedStat);
    }



    public void setInputMode(int inputMode)
    {
        INPUT_MODE = inputMode;
    }

    public void setStatInfo(PokeObject pokeObject)
    {
        TextView[] textView = new TextView[6];
        textView[StatData.STAT_HP] = hpNumber;
        textView[StatData.STAT_ATK] = atkNumber;
        textView[StatData.STAT_DEF] = defNumber;
        textView[StatData.STAT_SPATK] = spAtkNumber;
        textView[StatData.STAT_SPDEF] = spDefNumber;
        textView[StatData.STAT_SPEED] = speedNumber;
        String empty = "---";

        switch(INPUT_MODE)
        {
            default: System.out.println("fragStatInfo.setStatInfo() switch(INPUT_MODE) = default(" + INPUT_MODE + ")"); break;
            case INPUT_MODE_STAT:
            {
                for(int i = StatData.STAT_HP; i < StatData.NUMBER_STATS; i++)
                {
                    if(pokeObject.stats[i] <= 0)
                        textView[i].setText(empty);
                    else
                        textView[i].setText(String.valueOf(pokeObject.stats[i]));
                }
                for(int i = StatData.STAT_HP; i < StatData.NUMBER_STATS; i++)
                {
                    if(pokeObject.stats[i] <= 0)
                        textView[i].setText(empty);
                    else
                        textView[i].setText(String.valueOf(pokeObject.stats[i]));
                }
            } break;
            case INPUT_MODE_EV:
            {
                for(int i = StatData.STAT_HP; i < StatData.NUMBER_STATS; i++)
                {
                    if(pokeObject.EVs[i] <= 0)
                        textView[i].setText(empty);
                    else
                        textView[i].setText(String.valueOf(pokeObject.EVs[i]));
                }
            }  break;
            case INPUT_MODE_ADD_EV:
            {
                for(int i = StatData.STAT_HP; i < StatData.NUMBER_STATS; i++)
                {
                    if(pokeObject.EVs[i] <= 0)
                        textView[i].setText(empty);
                    else
                        textView[i].setText(String.valueOf(pokeObject.EVs[i]));
                }
            } break;
        }

    }

    public void updateStatInfo(int statValue, int statEnum)
    {
        switch(statEnum)
        {
            default: System.out.println("fragStatInfo.updateStatInfo(int, int) switch(statEnum) = default(" + statEnum + ")"); break;
            case StatData.STAT_HP: hpNumber.setText(String.valueOf(statValue)); break;
            case StatData.STAT_ATK: atkNumber.setText(String.valueOf(statValue)); break;
            case StatData.STAT_DEF: defNumber.setText(String.valueOf(statValue)); break;
            case StatData.STAT_SPATK: spAtkNumber.setText(String.valueOf(statValue)); break;
            case StatData.STAT_SPDEF: spDefNumber.setText(String.valueOf(statValue)); break;
            case StatData.STAT_SPEED: speedNumber.setText(String.valueOf(statValue)); break;
        }

    }
    public void updateStatInfo(String statValue, int statEnum)
    {
        switch(statEnum)
        {
            default: System.out.println("fragStatInfo.updateStatInfo(string, int) switch(statEnum) = default(" + statEnum + ")"); break;
            case StatData.STAT_HP: hpNumber.setText(statValue); break;
            case StatData.STAT_ATK: atkNumber.setText(statValue); break;
            case StatData.STAT_DEF: defNumber.setText(statValue); break;
            case StatData.STAT_SPATK: spAtkNumber.setText(statValue); break;
            case StatData.STAT_SPDEF: spDefNumber.setText(statValue); break;
            case StatData.STAT_SPEED: speedNumber.setText(statValue); break;
        }
    }

    public void highlightTextView(int statEnum)
    {
        hpNumber.setBackgroundColor(Color.parseColor("#858585"));
        atkNumber.setBackgroundColor(Color.parseColor("#858585"));
        defNumber.setBackgroundColor(Color.parseColor("#858585"));
        spAtkNumber.setBackgroundColor(Color.parseColor("#858585"));
        spDefNumber.setBackgroundColor(Color.parseColor("#858585"));
        speedNumber.setBackgroundColor(Color.parseColor("#858585"));
        hpText.setBackgroundColor(Color.parseColor("#858585"));
        atkText.setBackgroundColor(Color.parseColor("#858585"));
        defText.setBackgroundColor(Color.parseColor("#858585"));
        spAtkText.setBackgroundColor(Color.parseColor("#858585"));
        spDefText.setBackgroundColor(Color.parseColor("#858585"));
        speedText.setBackgroundColor(Color.parseColor("#858585"));

        switch(statEnum)
        {
            default: System.out.println("fragStatInfo.highlightTextView(int) switch(statEnum) = default(" + statEnum + ")"); break;
            case StatData.STAT_HP:    hpNumber.setBackgroundColor(Color.parseColor("#EE0000")); hpText.setBackgroundColor(Color.parseColor("#EE0000")); break;
            case StatData.STAT_ATK:   atkNumber.setBackgroundColor(Color.parseColor("#EE0000")); atkText.setBackgroundColor(Color.parseColor("#EE0000")); break;
            case StatData.STAT_DEF:   defNumber.setBackgroundColor(Color.parseColor("#EE0000")); defText.setBackgroundColor(Color.parseColor("#EE0000")); break;
            case StatData.STAT_SPATK: spAtkNumber.setBackgroundColor(Color.parseColor("#EE0000")); spAtkText.setBackgroundColor(Color.parseColor("#EE0000")); break;
            case StatData.STAT_SPDEF: spDefNumber.setBackgroundColor(Color.parseColor("#EE0000")); spDefText.setBackgroundColor(Color.parseColor("#EE0000")); break;
            case StatData.STAT_SPEED: speedNumber.setBackgroundColor(Color.parseColor("#EE0000")); speedText.setBackgroundColor(Color.parseColor("#EE0000")); break;
        }
    }

}
