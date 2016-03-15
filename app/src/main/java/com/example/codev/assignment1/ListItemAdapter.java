package com.example.codev.assignment1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Codev on 10/4/2015.
 */
public class ListItemAdapter extends ArrayAdapter<ListItem> {

    LayoutInflater l;
    Context context;
    List<ListItem> objects;
    TextView tvdisplayname, tvid, tvaccountno, tvexternalid, tvofficeid, tvofficename;


    public ListItemAdapter(Context context, int resource, List<ListItem> objects, LayoutInflater l) {
        super(context, resource, objects);

        this.l=l;
        this.context=context;
        this.objects=objects;
        
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v=convertView;

        if(v==null)
        {
            v=l.inflate(R.layout.listitem, null);
        }


        tvdisplayname=(TextView)v.findViewById(R.id.tv_list_displayname);
        tvid=(TextView)v.findViewById(R.id.tv_list_id);
        tvaccountno=(TextView)v.findViewById(R.id.tv_list_accountno);
        tvexternalid=(TextView)v.findViewById(R.id.tv_list_externalid);
        tvofficeid=(TextView)v.findViewById(R.id.tv_list_officeid);
        tvofficename=(TextView)v.findViewById(R.id.tv_list_officename);

        final ListItem l1=objects.get(position);
        tvdisplayname.setText(l1.displayname);
        tvid.setText(l1.id);
        tvaccountno.setText("Account No: "+l1.accountno);
        tvexternalid.setText("External Id: "+l1.externalid);
        tvofficeid.setText("Office Id: "+l1.officeid);
        tvofficename.setText("Office Name: "+l1.officename);

        return v;
    }
}
