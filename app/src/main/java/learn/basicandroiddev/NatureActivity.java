package learn.basicandroiddev;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jerry on 9/6/2016.
 */
public class NatureActivity extends Activity
{
    public static int currentNature = StatData.NATURE_NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nature);

        Intent calledNatureInput = getIntent();
        currentNature = calledNatureInput.getIntExtra("callNature", StatData.NATURE_NONE);

        ListView natureListView = (ListView) findViewById(R.id.nature_listview);

        natureListView.setAdapter(new RowNatureAdapter(NatureActivity.this, StatData.NatureName));

        natureListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                currentNature = position;

                // chosen nature name and image here
                TextView name = (TextView) findViewById(R.id.nature_selected_name);
                ImageView positive = (ImageView) findViewById(R.id.nature_selected_positive);
                ImageView negative = (ImageView) findViewById(R.id.nature_selected_negative);

                name.setText(StatData.NatureName[position]);

                positive.setBackgroundColor(Color.parseColor(
                        StatData.STAT_BACKGROUND[StatData.Modifier[position][0]]));
                negative.setBackgroundColor(Color.parseColor(
                        StatData.STAT_BACKGROUND[StatData.Modifier[position][1]]));

            }
        });
    }

    public void onNatureConfirm(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent goingBack = new Intent();

        goingBack.putExtra("returnNature", currentNature);

        setResult(RESULT_OK, goingBack);

        finish();
    }

    public void onNatureCancel(View view)
    {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Intent goingBack = new Intent();

        setResult(RESULT_CANCELED, goingBack);

        finish();
    }

}
