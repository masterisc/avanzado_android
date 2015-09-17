package com.example.ciromine.sqliteexample;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;


public class UserFragment extends DialogFragment {

    private EditText id;
    private EditText name;
    private UserBD sqlite_user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqlite_user = new UserBD(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user, container, false);

        id = (EditText)v.findViewById(R.id.editText);
        name = (EditText)v.findViewById(R.id.editText2);

        User user = sqlite_user.getUser(getArguments().getInt("id"));

        id.setText(user.getId()+"");
        name.setText(user.getName());

        // Watch for button clicks.
        Button button = (Button)v.findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sqlite_user.update(Integer.parseInt(id.getText().toString()), name.getText().toString());
                dismiss();
            }
        });

        Button button2 = (Button)v.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sqlite_user.destroy(Integer.parseInt(id.getText().toString()));
                dismiss();
            }
        });

        Button button3 = (Button)v.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });


        return v;
    }
}