package com.example.app.tradersapp.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.app.tradersapp.R
import kotlinx.android.synthetic.main.fragment_article_detail.*


class EventDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        eaImage.setImageResource(bundle!!.getInt("image"))
        eaTitle.text = bundle.getString("title")
        eaBody.text = bundle.getString("body")
    }


}
