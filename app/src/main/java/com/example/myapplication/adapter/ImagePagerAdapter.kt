package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.myapplication.R
import com.example.myassignmentrahul.utils.loadImageFromURL

class ImagePagerAdapter(context: Context, list:List<String>) :  PagerAdapter() {

    var mContext:Context = context
    var mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var mResources:List<String> = list


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView: View = mLayoutInflater.inflate(R.layout.image_pager_item, container, false)
        val imageView: ImageView = itemView.findViewById(R.id.img)
        imageView.loadImageFromURL(mResources[position], R.mipmap.ic_launcher)

        container.addView(itemView)


        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

    override fun getCount(): Int {
        return mResources.size
    }
}