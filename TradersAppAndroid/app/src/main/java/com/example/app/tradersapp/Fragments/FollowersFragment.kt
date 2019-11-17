package com.example.app.tradersapp.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app.tradersapp.Adapters.FollowersAdapter
import com.example.app.tradersapp.R
import kotlinx.android.synthetic.main.followers_fragment.*

class FollowersFragment : Fragment() {

    val followersAdapter: FollowersAdapter = FollowersAdapter()

    var isFollowers: Boolean? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFollowers = arguments?.getBoolean(KEY_IS_FOLLOWERS)
        followersList.apply {
            adapter = followersAdapter
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.followers_fragment, container, false)
    }

    companion object {

        private const val KEY_IS_FOLLOWERS = "IS_FOLLOWERS"

        @JvmStatic
        fun newInstance(isFollowers: Boolean): FollowersFragment {
            return FollowersFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(KEY_IS_FOLLOWERS, isFollowers)
                }
            }
        }
    }
}