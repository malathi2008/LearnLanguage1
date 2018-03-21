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
import com.student.learnlanguage.model.Contacts1;
import com.student.learnlanguage.model.WordsModel;

import java.util.ArrayList;
import java.util.List;


public class WordsListAdapter extends ArrayAdapter {
    List<WordsModel> list=new ArrayList();
    List<WordsModel> listnew=new ArrayList();
    public WordsListAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(WordsModel object) {
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
            row=layoutInflater.inflate(R.layout.wordslistitems,parent,false);
            contactHolder =new ContactHolder1();
            contactHolder.wordone=(TextView) row.findViewById(R.id.wordone);
            contactHolder.wordtwo=(TextView)row.findViewById(R.id.wordtwo);
            row.setTag(contactHolder);
        }else {
            contactHolder =(ContactHolder1)row.getTag();
        }
        WordsModel contacts=(WordsModel)this.getItem(position);
        contactHolder.wordone.setText(contacts.getFirstword());
        contactHolder.wordtwo.setText(contacts.getSecoundword());
        return row;
    }

    static class ContactHolder1
    {
        TextView wordone,wordtwo,tx_actype;
        ImageView imageView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase();
        list.clear();
        if (charText.length() == 0) {
            list.addAll(listnew);
        } else {
            for (WordsModel wp : listnew) {
                if (wp.getFirstword().toLowerCase()
                        .contains(charText)) {
                    list.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }



}
