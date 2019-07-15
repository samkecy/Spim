package com.samkecy.spim;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;

public class ListViewHolder extends RecyclerView.ViewHolder {
    protected ImageView contactimage;
    protected TextView contactname;//,contactnum;
    protected LinearLayout relativeLayout;
    public ListViewHolder(View view){
        super(view);

        contactimage = (ImageView)view.findViewById(R.id.contactImage);
        contactname = (TextView) view.findViewById(R.id.contactName);
        //contactnum = (TextView) view.findViewById(R.id.contactNo);
        relativeLayout = (LinearLayout) view.findViewById(R.id.linerCont);

        view.setClickable(true);
    }
}
