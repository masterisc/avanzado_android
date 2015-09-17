package com.example.ciromine.sqliteexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Button;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button New, Update;
    private Adaptador adapter;
    private ArrayList<User> lista;
    private EditText id;
    private EditText name;
    private UserBD sqlite_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = new ArrayList<User>();
        adapter = new Adaptador();

        id = (EditText) findViewById(R.id.editText);
        name = (EditText) findViewById(R.id.editText2);

        New = (Button) findViewById(R.id.button);
        New.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentManager fm = getSupportFragmentManager();
                final CreateFragment createDialog = new CreateFragment();
                createDialog.show(fm, "dialog_create");
            }
        });

        Update = (Button) findViewById(R.id.button2);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadUsers();
            }
        });

        ListView Lista = (ListView) findViewById(R.id.listView);
        Lista.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                final FragmentManager fm = getSupportFragmentManager();
                final UserFragment userDialog = new UserFragment();
                Bundle args = new Bundle();
                args.putInt("id", lista.get(position).getId());
                userDialog.setArguments(args);
                userDialog.show(fm, "dialog_users");
            }
        });
        Lista.setAdapter(adapter);
        LoadUsers();
    }

    public void LoadUsers(){
        lista = new ArrayList<User>();
        sqlite_user = new UserBD(getApplicationContext());
        lista = sqlite_user.getAllUsers();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class Adaptador extends BaseAdapter{

        public int getCount() { return lista.size(); }

        public Object getItem(int arg0) { return lista.get(arg0); }

        public long getItemId(int position) {
            final User user =  lista.get(position);
            if(user != null) {
                return user.getId();
            } else {
                return 0;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            final LayoutInflater inflater = LayoutInflater.from(MainActivity.this);

            if(convertView == null) {
                convertView = inflater.inflate(R.layout.lista, null);
                holder = new ViewHolder();
                holder.id = (TextView)convertView.findViewById(R.id.lista_id);
                holder.name = (TextView)convertView.findViewById(R.id.lista_name);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            if(lista != null) {
                User user = lista.get(position);
                if(user != null){
                    holder.id.setText(String.valueOf(user.getId() + ""));
                    holder.name.setText(user.getName());
                }
            }
            return convertView;
        }
    }

    static class ViewHolder{
        private TextView id;
        private TextView name;
    }
}