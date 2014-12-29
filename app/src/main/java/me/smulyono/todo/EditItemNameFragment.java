package me.smulyono.todo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditItemNameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditItemNameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditItemNameFragment extends DialogFragment {

    private OnFragmentInteractionListener mListener;
    private EditText etEditItem;
    private Button btnEdit;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EditItemNameFragment.
     */
    public static EditItemNameFragment newInstance(String title) {
        EditItemNameFragment fragment = new EditItemNameFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    public EditItemNameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_edit_item_name, container, false);
        
        etEditItem = (EditText) view.findViewById(R.id.etEditItem);
        btnEdit = (Button) view.findViewById(R.id.saveBtnText);
        
        String title = getArguments().getString("title");
        getDialog().setTitle(title);
        
        // show focus on the edit text
        etEditItem.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        
        // attach button click
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEditClick(v);
            }
        });
        
        return view;
    }

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
        public void onFragmentInteraction(Uri uri);
    }
    
    
    public void btnEditClick(View v){
        getDialog().dismiss();
    }
}
