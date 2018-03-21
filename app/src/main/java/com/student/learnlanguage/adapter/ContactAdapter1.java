package com.student.learnlanguage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;
import com.student.learnlanguage.R;
import com.student.learnlanguage.model.Contacts;
import com.student.learnlanguage.model.Contacts1;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ContactAdapter1 extends ArrayAdapter {
    List<Contacts1> list=new ArrayList();
    List<Contacts1> listnew=new ArrayList();
    public ContactAdapter1(Context context, int resource) {
        super(context, resource);
    }


    public void add(Contacts1 object) {
        super.add(object);
        list.add(object);
        listnew.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row=convertView;
        ContactHolder1 contactHolder;
        if(row==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.imageview,parent,false);
            contactHolder =new ContactHolder1();
            contactHolder.imageView=(ImageView) row.findViewById(R.id.showimage);
            contactHolder.tx_name=(TextView)row.findViewById(R.id.language_name);
            row.setTag(contactHolder);
        }else {
            contactHolder =(ContactHolder1)row.getTag();
        }
         Contacts1 contacts=(Contacts1)this.getItem(position);

        Picasso.with(getContext())
                .load(contacts.getimg())
                .resize(400, 400) // here you resize your image to whatever width and height you like
                .into(contactHolder.imageView);
        contactHolder.tx_name.setText(contacts.getName());
        return row;
    }

    static class ContactHolder1
    {
        TextView tx_name,tx_email,tx_actype;
        ImageView imageView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase();
        list.clear();
        if (charText.length() == 0) {
            list.addAll(listnew);
        } else {
            for (Contacts1 wp : listnew) {
                if (wp.getName().toLowerCase()
                        .contains(charText)) {
                    list.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }





}
