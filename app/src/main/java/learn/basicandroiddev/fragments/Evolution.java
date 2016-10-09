package learn.basicandroiddev.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import learn.basicandroiddev.MainActivity;
import learn.basicandroiddev.PokeData;
import learn.basicandroiddev.R;
import learn.basicandroiddev.RowEvolutionAdapter;
import learn.basicandroiddev.RowNatureAdapter;

/**
 * Created by Jerry on 9/21/2016.
 */
public class Evolution extends Fragment
{
    private int selectedPokemon = PokeData.SPECIES_NONE;

    private int searchResult[] = {0,0,0,0,0,0,0,0};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_evolution, container, false);
        System.out.println("Display Evolution");

        MainActivity mainActivity = (MainActivity) getActivity();

        // get species names for evolution
        String[] evolutionName = new String[8];
        // populate evolution array
        if(mainActivity.POKEMON.species == 133)
        {
            // special case eevee check
            for(int i = 0; i < 8; i++)
            {
                searchResult[i] = PokeData.eevolutions[i];
                evolutionName[i] = PokeData.Name[PokeData.eevolutions[i]];
            }
        }
        else if(mainActivity.POKEMON.species == 412)
        {
            // special case burmy check
            int i = 0;
            for(;i < 4; i++)
            {
                searchResult[i] = PokeData.burmyevolutions[i];
                evolutionName[i] = PokeData.Name[PokeData.burmyevolutions[i]];
            }
            for(;i < 8; i++)
            {
                searchResult[i] = 0;
                evolutionName[i] = "";
            }
        }
        else
        {
            int i = 0;
            for(; i < 3; i++)
            {
                searchResult[i] = PokeData.evolutions[mainActivity.POKEMON.species][i];

                if (searchResult[i] == 0)
                    evolutionName[i] = "";
                else
                    evolutionName[i] = PokeData.Name[PokeData.evolutions[mainActivity.POKEMON.species][i]];
            }
            for (; i < 8; i++)
            {
                searchResult[i] = 0;
                evolutionName[i] = "";
            }
        }


        ListView evolutionListView = (ListView) rootView.findViewById(R.id.evolution_listview);
        evolutionListView.setAdapter(new RowEvolutionAdapter(getActivity(), evolutionName));

        selectedPokemon = PokeData.SPECIES_NONE;

        evolutionListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                selectedPokemon = searchResult[position];

                MainActivity mainActivity = (MainActivity) getActivity();

                // chosen nature name and image here
                //mainActivity.POKEMON.species = position;
                mainActivity.setTempSpeciesName(selectedPokemon);
            }
        });

        return rootView;
    }

    public int getOutput()
    {
        return selectedPokemon;
    }

    public void resetOutput()
    {
        selectedPokemon = 0;
    }
}
