package com.example.sarp_mobile;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sarp_mobile.DataGenModeDialogFragment.DataGenModeDialogListener;

public class DisplayAlgorithmsActivity extends ListActivity implements DataGenModeDialogListener{
	
	int selectedDataGenMode = 0;
	int selectedAlgorithm = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Resources res = getResources();
		String[] algorithms = res.getStringArray(R.array.algorithms_descriptions_array);
		
		AlgorithmsArrayAdapter adapter = new AlgorithmsArrayAdapter(this, algorithms);
	    setListAdapter(adapter);
	}
	
	@Override
	  protected void onListItemClick(ListView l, View v, int position, long id) {
	    selectedAlgorithm = position;
	    
	    FragmentManager fm = getFragmentManager();
	    DialogFragment dialog = new DataGenModeDialogFragment();
	    dialog.show(fm, "fragment_data_mode");
	    
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_algorithms, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onFinishEditDialog(int inputMode) {
		selectedDataGenMode = inputMode;
		Intent intent = new Intent(this, AlgorithmsSwipeActivity.class);
		intent.putExtra("DATA_GEN_MODE", selectedDataGenMode);
		intent.putExtra("ALGORITHM", selectedAlgorithm);
		startActivity(intent);
	}
}
