package learn.basicandroiddev.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import learn.basicandroiddev.MainActivity;
import learn.basicandroiddev.PokeData;
import learn.basicandroiddev.PokeObject;
import learn.basicandroiddev.R;
import learn.basicandroiddev.StatData;

/**
 * Created by Jerry on 9/21/2016.
 */
public class PokeInfo extends Fragment
{
    TextView level;
    TextView species;
    TextView nature;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_display_poke_info, container, false);

        System.out.println("Display PokeInfo");

        level = (TextView) rootView.findViewById(R.id.main_level_input_text);
        species = (TextView) rootView.findViewById(R.id.main_species_text);
        nature = (TextView) rootView.findViewById(R.id.main_nature_text);

        return rootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        MainActivity rootView = (MainActivity) getActivity();

        updatePokeInfo(rootView.POKEMON);

        System.out.println("fragStatInfo.onResume() selected stat = " + rootView.selectedStat);
    }

    public void setLevelText(String string)
    {
        level.setText(string);
    }

    public void setSpeciesText(String string)
    {
        species.setText(string);
    }

    public void updatePokeInfo(PokeObject pokeObject)
    {
        resetPokeInfo();

        System.out.println("fragPokeInfo.updatePokeInfo() level(" + pokeObject.level + ")");

        if(pokeObject.nature != StatData.NATURE_NONE)
            nature.setText(String.valueOf(StatData.NatureName[pokeObject.nature]));
        if(pokeObject.level > 0)
            level.setText(String.valueOf(pokeObject.level));
        if(pokeObject.species != PokeData.SPECIES_NONE)
            species.setText(String.valueOf(PokeData.Name[pokeObject.species]));
    }

    private void resetPokeInfo()
    {
        final String dash = "---";
        final String selectSpecies = "Select Species";
        final String selectNature = "Select Nature";

        level.setText(dash);
        species.setText(selectSpecies);
        nature.setText(selectNature);
    }

    public void setViewsEnabled(boolean isEnabled)
    {
        level.setClickable(isEnabled);
        species.setClickable(isEnabled);
        nature.setClickable(isEnabled);

        level.setEnabled(isEnabled);
        species.setEnabled(isEnabled);
        nature.setEnabled(isEnabled);
    }
}
