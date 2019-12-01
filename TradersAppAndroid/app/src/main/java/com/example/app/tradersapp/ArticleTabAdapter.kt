package com.example.app.tradersapp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import androidx.annotation.Nullable

class ArticleTabAdapter internal constructor(fm:FragmentManager?): FragmentStatePagerAdapter(fm) {

    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()
    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }

    fun addFragment(fragment: Fragment, title: String){
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }
}