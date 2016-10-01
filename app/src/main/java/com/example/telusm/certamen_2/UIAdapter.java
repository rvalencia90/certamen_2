package com.example.telusm.certamen_2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.telusm.certamen_2.objects.DataObject;

import java.util.List;




/**
 * Created by telusm on 30-09-2016.
 */

public class UIAdapter extends RecyclerView.Adapter<UIAdapter.ViewHolder>  {

    private List<DataObject> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextName;
        public TextView mDesc;
        public TextView mUp;

        public ViewHolder(View v) {
            super(v);
            mTextName = (TextView) v.findViewById(R.id.textName);
            mDesc = (TextView) v.findViewById(R.id.textDesc);
            mUp = (TextView) v.findViewById(R.id.textUp);
        }
    }


    public UIAdapter(List<DataObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public UIAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataObject inf = mDataset.get(position);

        holder.mTextName.setText(inf.getNombre());
        holder.mDesc.setText(inf.getDescripcion());
        holder.mUp.setText(inf.getUpdate());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
