package com.example.markos.golfcourses;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Markos on 5. 11. 2016.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<SportPlace> sportPlacesData;
    private ItemClicked listeningActivity;

    public static class ImageDownload extends AsyncTask<String, Void, Void>{

        private ImageView imageView;
        private Bitmap image = null;

        public ImageDownload(ImageView imageViewPlace){
            imageView = imageViewPlace;
        }

        @Override
        protected Void doInBackground(String... urlPath) {
            try {
                URL url = new URL(urlPath[0]);
                InputStream is = url.openStream();
                image = BitmapFactory.decodeStream(is);
            }catch (MalformedURLException e){
            }
            catch (IOException e){
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            imageView.setImageBitmap(image);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {

        private SportPlace detailInfo;
        private ImageView itemImage;
        private TextView itemMainName;
        private TextView fieldType;
        private TextView fieldAddress;
        private TextView fieldGPS;
        private TextView phone;

        public ViewHolder(View v, final ItemClicked activity) {
            super(v);
            itemImage = (ImageView) v.findViewById(R.id.itemImage);
            itemMainName = (TextView) v.findViewById(R.id.itemMainName);
            fieldType = (TextView) v.findViewById(R.id.fieldType);
            fieldAddress = (TextView) v.findViewById(R.id.fieldAddress);
            fieldGPS = (TextView) v.findViewById(R.id.fieldGPS);
            phone = (TextView) v.findViewById(R.id.phone);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.itemHasBeenClicked(detailInfo);
                }
            });
        }


        public ImageView getItemImage() {
            return itemImage;
        }

        public TextView getItemMainName() {
            return itemMainName;
        }

        public TextView getFieldType() {
            return fieldType;
        }

        public TextView getFieldAddress() {
            return fieldAddress;
        }

        public TextView getFieldGPS() {
            return fieldGPS;
        }

        public TextView getPhone() {
            return phone;
        }

        public void setDetailInfo(SportPlace detailInfo) {
            this.detailInfo = detailInfo;
        }
    }

    public RecyclerAdapter(List<SportPlace> sportPlaces, ItemClicked activity) {
        sportPlacesData = sportPlaces;
        listeningActivity = activity;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder(v, listeningActivity);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setDetailInfo(sportPlacesData.get(position));

        ImageDownload imageDownload = new ImageDownload(holder.getItemImage());
        imageDownload.execute(sportPlacesData.get(position).getPricturePath());
        holder.getItemMainName().setText(sportPlacesData.get(position).getField());
        holder.getFieldType().setText(sportPlacesData.get(position).getType());
        holder.getFieldAddress().setText(sportPlacesData.get(position).getAddress());
        holder.getFieldGPS().setText(sportPlacesData.get(position).getLatitude()+", "+sportPlacesData.get(position).getLongitude());
        holder.getPhone().setText(sportPlacesData.get(position).getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return sportPlacesData.size();
    }

    public interface ItemClicked{
        void itemHasBeenClicked(SportPlace detailInfo);
    }
}
