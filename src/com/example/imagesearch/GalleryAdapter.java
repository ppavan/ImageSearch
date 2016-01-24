package com.example.imagesearch;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

public class GalleryAdapter extends ArrayAdapter<ParsedDetail> {

	List<ParsedDetail> mData;
	Context mContext;
	int mResource;

	public GalleryAdapter(Context context, int resource,
			List<ParsedDetail> object) {
		super(context, resource, object);
		this.mData = object;
		this.mContext = context;
		this.mResource = resource;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(mResource, parent, false);
		}
		ParsedDetail parsedDetail = mData.get(position);
		ImageView smallimg = (ImageView) convertView
				.findViewById(R.id.imageView1);
		Picasso.with(mContext).load(parsedDetail.photoUrl).into(smallimg);

		TextView phototitle = (TextView) convertView
				.findViewById(R.id.textView1);
		phototitle.setText(parsedDetail.photoName);

		return convertView;
	}
}
