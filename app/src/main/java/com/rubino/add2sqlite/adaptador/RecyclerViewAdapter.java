package com.rubino.add2sqlite.adaptador;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
//Imports!!!!
import com.bumptech.glide.Glide;
import com.rubino.add2sqlite.InfoReceta;
import com.rubino.add2sqlite.R;
import com.rubino.add2sqlite.recetario.GestorReceta;
import com.rubino.add2sqlite.recetario.Receta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<Receta> mValues;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public Receta mBoundReceta;

            public final View mView;
            public final ImageView mImageView;
            public final TextView mTextView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.ivFoto);
                mTextView = (TextView) view.findViewById(R.id.tvItemNomb);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        public Receta getValueAt(int position) {
            return mValues.get(position);
        }

        public RecyclerViewAdapter(Context context, List<Receta> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mBoundReceta = mValues.get(position);
            holder.mTextView.setText((CharSequence) mValues.get(position));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, InfoReceta.class);
                    intent.putExtra(InfoReceta.EXTRA_NAME, holder.mBoundReceta+"");

                    context.startActivity(intent);
                }
            });

            Glide.with(holder.mImageView.getContext())
                    .load(GestorReceta.getDrawable())
                    .fitCenter()
                    .into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
        private List<String> getRandomSublist(String[] array, int amount) {
            ArrayList<String> list = new ArrayList<>(amount);
            Random random = new Random();
            while (list.size() < amount) {
                list.add(array[random.nextInt(array.length)]);
            }
            return list;
        }
    }
