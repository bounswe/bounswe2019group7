package com.example.app.tradersapp.Fragments


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.app.tradersapp.R
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileImage = profilePic
        val followButton = follow

        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.trading_logo)
        val mDrawable: RoundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources,bitmap)
        mDrawable.isCircular = true
        profileImage.setImageDrawable(mDrawable)
        //mDrawable.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), PorterDuff.Mode.DST_OVER);

        followButton.setOnClickListener {
            // TODO: Handle following feature
        }


    }


}
