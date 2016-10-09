package learn.basicandroiddev.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import learn.basicandroiddev.MainActivity;
import learn.basicandroiddev.R;
import learn.basicandroiddev.RowNatureAdapter;
import learn.basicandroiddev.StatData;

/**
 * Created by Jerry on 9/21/2016.
 */
public class Nature extends Fragment
{
    private int selectedNature = StatData.NATURE_NONE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_nature, container, false);
        System.out.println("Display NatureInput");

        ListView natureListView = (ListView) rootView.findViewById(R.id.nature_listview);
        natureListView.setAdapter(new RowNatureAdapter(getActivity(), StatData.NatureName));

        selectedNature = StatData.NATURE_NONE;

        natureListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                selectedNature = position;

                MainActivity mainActivity = (MainActivity) getActivity();

                // chosen nature name and image here
                mainActivity.POKEMON.nature = position;
                mainActivity.updateViews();
            }
        });

        return rootView;
    }

    public int getNature()
    {
        return selectedNature;
    }
}
