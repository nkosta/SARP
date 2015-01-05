package com.example.sarp_mobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

// Dialog za izbiro načina generiranja podatkov pri

public class DataGenModeDialogFragment extends DialogFragment {
	public int selectedMode = 0;
	
	public interface DataGenModeDialogListener {
	    void onFinishEditDialog(int inputMode);
	}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {		
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog1_title)
        	   .setSingleChoiceItems(R.array.data_gen_mode_array, 0, 
        			   new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							selectedMode = which;							
						}
					}) 

               .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       DataGenModeDialogListener activity = (DataGenModeDialogListener) getActivity();
                       activity.onFinishEditDialog(selectedMode);
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
	

}
