package com.student.learnlanguage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.student.learnlanguage.R;
import com.student.learnlanguage.model.Contacts;

import java.util.ArrayList;
import java.util.List;


public class ContactAdapter extends ArrayAdapter {
    List list=new ArrayList();
    List listnew=new ArrayList();
    Contacts contacts;
    public ContactAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(Contacts object) {
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
        ContactHolder contactHolder;
        if(row==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.progression,parent,false);
            contactHolder =new ContactHolder();
            contactHolder.quiz_name=(TextView)row.findViewById(R.id.quiz_name);
            contactHolder.total=(TextView)row.findViewById(R.id.total);
            row.setTag(contactHolder);
        }else {
            contactHolder =(ContactHolder)row.getTag();
        }
      contacts=(Contacts)this.getItem(position);
        contactHolder.quiz_name.setText(contacts.getQuiz_name());
        contactHolder.total.setText(contacts.getTotal());
        return row;
    }

    static class ContactHolder
    {
        TextView quiz_name,total;

    }







}
