package me.smulyono.todo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import me.smulyono.todo.models.TodoItems;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddNewItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddNewItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewItemFragment extends DialogFragment{
    private final String TAG = this.getClass().getSimpleName();

    private OnFragmentInteractionListener mListener;
    
    private EditText etAddItem;
    
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddNewItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNewItemFragment newInstance() {
        AddNewItemFragment fragment = new AddNewItemFragment();
        return fragment;
    }

    public AddNewItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting up theme
        setStyle(DialogFragment.STYLE_NORMAL,android.R.style.Theme_Holo_Light_DarkActionBar);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(R.string.addTxtTitle);
    
        // add input
        etAddItem = new EditText(getActivity());
        etAddItem.setHint(R.string.addItemHint);
        
        alertDialogBuilder.setView(etAddItem);
        
        alertDialogBuilder.setPositiveButton(R.string.addBtn,  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
                // pass input data to main activity
                String newTask = etAddItem.getText().toString();
                if (!newTask.isEmpty()){
                    Log.d(TAG, "New Task :: " + etAddItem.getText().toString());
                    TodoActivity parent = (TodoActivity) getActivity();
                    TodoItems rec = new TodoItems();
                    rec.task = etAddItem.getText().toString();
                    // show some random rating
                    rec.priority = ((int) Math.round(Math.random() * 5));

                    parent.addNewTask(rec);
                    // close dialog
                    dialog.dismiss();
                }
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        
        return alertDialogBuilder.create();
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_add_new_item, container, false);
//
//        btnAddItem = (Button) view.findViewById(R.id.btnAddItem);
//        etAddItem = (EditText) view.findViewById(R.id.etAddItem);
//
//        getDialog().setTitle("Add a new task?");
//        etAddItem.requestFocus();
//
//        btnAddItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getDialog().dismiss();
//            }
//        });
//
//        return view;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
