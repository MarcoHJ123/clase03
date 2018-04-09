package pe.com.gescom.acsion.monitoreo.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pe.com.gescom.acsion.monitoreo.R;
import pe.com.gescom.acsion.monitoreo.activities.QuadrillesActivity;
import pe.com.gescom.acsion.monitoreo.models.Quadrille;
import pe.com.gescom.acsion.monitoreo.utils.ColorGenerator;
import pe.com.gescom.acsion.monitoreo.utils.TextDrawable;

/**
 * Created by gescom on 16/11/2016.
 */

public class QuadrilleAdapter extends ArrayAdapter<Quadrille> {
    private QuadrillesActivity activity;
    private ArrayList<Quadrille> friendList;

    public QuadrilleAdapter(QuadrillesActivity context, int resource, ArrayList<Quadrille> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.friendList = objects;
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public Quadrille getItem(int position) {
        return friendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.card_quadrilles_item, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }
        holder.friendName.setText(getItem(position).getNameTechnical());
        //get first letter of each String item
        String firstLetter = "N";
        if(getItem(position).getNameTechnical().length() > 0){
            firstLetter = String.valueOf(getItem(position).getNameTechnical().charAt(0));
        }

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getColor(getItem(position));
        //int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder().buildRect(firstLetter, color); // Rectangulo in px
        holder.imageView.setImageDrawable(drawable);

        holder.friendName.setCompoundDrawablesWithIntrinsicBounds(null,null, this.activity.getResources().getDrawable(R.drawable.ic_action_next_item), null);

        return convertView;
    }

    private class ViewHolder {
        private ImageView imageView;
        private TextView friendName;

        public ViewHolder(View v) {
            imageView = (ImageView) v.findViewById(R.id.imageView);
            friendName = (TextView) v.findViewById(R.id.textView);
        }
    }
}
