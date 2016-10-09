package learn.basicandroiddev.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import learn.basicandroiddev.MainActivity;
import learn.basicandroiddev.R;
import learn.basicandroiddev.StatData;

/**
 * Created by Jerry on 9/21/2016.
 */
public class PushButton extends Fragment
{
    TextView button = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_push_button, container, false);
        System.out.println("Display PushButton");

        button = (TextView) rootView.findViewById(R.id.fragment_push_button);

        //  set up listeners
        this.button.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    view.setTag(true);
                }
                else if(view.isPressed() && (boolean) view.getTag())
                {
                    MainActivity mainActivity = (MainActivity) getActivity();

                    long eventDuration = event.getEventTime() - event.getDownTime();
                    if(eventDuration > ViewConfiguration.getLongPressTimeout())
                    {
                        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                        view.setTag(false);
                        mainActivity.evolve(view);

                        for(int i = 0; i < StatData.NUMBER_STATS; i++)
                            mainActivity.POKEMON.stats[i] = -1;

                        return true;
                    }
                    else if(event.getAction() == MotionEvent.ACTION_UP)
                    {
                        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                        mainActivity.onPushButton(view);
                        return true;
                    }
                }
                return false;
            }
        });

        return rootView;
    }


}
