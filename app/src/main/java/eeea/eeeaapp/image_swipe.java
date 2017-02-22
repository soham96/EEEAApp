package eeea.eeeaapp;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Soham on 01-02-2017.
 */

public class image_swipe extends PagerAdapter{

    private int [] ImageResources= {R.mipmap.img1, R.mipmap.img2, R.mipmap.img3, R.mipmap.img4};

    private Context ctx;
    private LayoutInflater layoutInflater;

    public image_swipe() {
    }

    public image_swipe(Context c) {
        ctx=c;
    }

    @Override
    public int getCount() {
        return ImageResources.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=layoutInflater.inflate(R.layout.image_switch, container, false);
        ImageView imageView=(ImageView) itemView.findViewById(R.id.image_view);
        imageView.setImageResource(ImageResources[position]);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==object);
    }
}
