package com.samkecy.spim;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class myAdapter extends RecyclerView.Adapter<ListViewHolder> {


    private List<ListItems> listItemsList;
    private Context mContext;
    private int focusedItem = 0;

    public myAdapter(Context context){
        mContext = context;
        listItemsList = new ArrayList<>();
        ContentResolver cr = mContext.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        String name;
        String phone = null;
        String image_uri = "";
        ListItems listItems;
        int i = 1;
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                if(i == 1){
                    cur.moveToFirst();
                    i++;
                }
                String id = cur.getString(cur
                        .getColumnIndex(ContactsContract.Contacts._ID));
                name = cur
                        .getString(cur
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                image_uri = cur
                        .getString(cur
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                Cursor pCur = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                + " = ?", new String[]{id}, null);
                if (pCur.moveToNext()) {
                    phone = pCur
                            .getString(pCur
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                   // System.out.println("Name: "+ name + "phone: " + phone);
                }
                pCur.close();
                listItems = new ListItems(name,phone,image_uri);
                listItemsList.add(listItems);
            }
            cur.close();
        }
    }
    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        Context context = viewGroup.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.item_list_item,null);
        //View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row,null);
        ListViewHolder holder = new ListViewHolder(v);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        ListItems listItems = listItemsList.get(position);

        TextView contact_name = holder.contactname;
        //TextView contact_num = holder.contactnum;
        ImageView contact_image = holder.contactimage;

        contact_name.setText(listItems.getContactname());
       // contact_num.setText(listItems.getContactnum());

        if (listItems.getThumbnail() != null) {
            Uri myuri = Uri.parse(listItems.getThumbnail());
            InputStream photo_stream = ContactsContract.Contacts.openContactPhotoInputStream(mContext.getContentResolver(),myuri);
            BufferedInputStream buf =new BufferedInputStream(photo_stream);
            Bitmap my_btmp = BitmapFactory.decodeStream(buf);
            contact_image.setImageBitmap(my_btmp);
        } else {
            // Find an alternative method, like displaying custom ImageView with name initials
           // Create new photo entry


        }

    }

    @Override
    public int getItemCount() {
        return listItemsList.size();
    }
}
