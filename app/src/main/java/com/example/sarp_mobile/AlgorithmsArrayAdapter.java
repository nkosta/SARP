package com.example.sarp_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AlgorithmsArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
	private final int[] images = {R.drawable.image_algorithms_ps, R.drawable.image_algorithms_sjn,
								  R.drawable.image_algorithms_srt, R.drawable.image_algorithms_hrrn,
								  R.drawable.image_algorithms_rr};

	public AlgorithmsArrayAdapter(Context context, String[] values) {
		super(context, R.layout.algorithms_row, values);
	    this.context = context;
	    this.values = values;
    }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
    	LayoutInflater inflater = (LayoutInflater) context
    			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	View rowView = inflater.inflate(R.layout.algorithms_row, parent, false);
    	TextView textView = (TextView) rowView.findViewById(R.id.label);
	    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
	    textView.setText(values[position]);
	    // Change the icon for Windows and iPhone
	    String s = values[position];
	    
	    imageView.setImageResource(images[position]);
	    

	    return rowView;
    }
}
