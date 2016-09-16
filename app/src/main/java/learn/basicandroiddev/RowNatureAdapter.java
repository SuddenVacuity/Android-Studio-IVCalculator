package learn.basicandroiddev;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jerry on 9/6/2016.
 */
public class RowNatureAdapter extends BaseAdapter
{
    Context context;
    String[] data;
    private static LayoutInflater inflater = null;

    public RowNatureAdapter(Context context, String[] data)
    {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
       return data.length;
    }

    @Override
    public Object getItem(int position)
    {
        return data[position];
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View vi = convertView;
        if(vi == null)
            vi = inflater.inflate(R.layout.row_nature_layout, null);

        // handle data here
        TextView text = (TextView) vi.findViewById(R.id.row_nature_list);
        text.setText(data[position]);

        ImageView positive = (ImageView) vi.findViewById(R.id.row_nature_positive);
        ImageView negative = (ImageView) vi.findViewById(R.id.row_nature_negative);

        positive.setBackgroundColor(Color.parseColor(
                StatData.STAT_BACKGROUND[StatData.Modifier[position][0]]));
        negative.setBackgroundColor(Color.parseColor(
                StatData.STAT_BACKGROUND[StatData.Modifier[position][1]]));

        return vi;
    }


}
