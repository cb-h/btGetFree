package com.pci.aw.btgetfree.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pci.aw.btgetfree.R;
import com.pci.aw.btgetfree.bean.BeanList;
import com.pci.aw.btgetfree.fragment.MainFragment_web_Activity;
import java.util.ArrayList;




public class MainFragmentAdapter extends BaseAdapter {

    private ArrayList<BeanList> list;
    private Context context;

    public MainFragmentAdapter(ArrayList<BeanList> alist,Context context) {
        this.list = alist;
        this.context=context;
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.main_fragment_list_text,null);
            holder = new ViewHolder();
            holder.title=  convertView.findViewById(R.id.main_fragment_list_title);
            holder.size=  convertView.findViewById(R.id.main_fragment_list_size);
            holder.creatTime=  convertView.findViewById(R.id.main_fragment_list_creattime);
            holder.clickTime=  convertView.findViewById(R.id.main_fragment_list_clicktime);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        holder.title.setText(list.get(position).getTitle());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("--",""+list.get(position).getTitle());
                Intent intent=new Intent(context, MainFragment_web_Activity.class);
                intent.putExtra("hashcode",list.get(position).getHashcode());
                intent.putExtra("title",list.get(position).getTitle());
                intent.putExtra("msgUrl",list.get(position).getMsgUrl());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
    private class ViewHolder{
        private TextView title,size,creatTime,clickTime;

    }

}
