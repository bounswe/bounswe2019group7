package com.example.app.tradersapp.Fragments


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app.tradersapp.ArticleTabAdapter
import com.example.app.tradersapp.R
import kotlinx.android.synthetic.main.fragment_article_base.*

class ArticleBaseFragment : Fragment() {

    //Variables regarding the tabs
    private lateinit var mTabAdapter: ArticleTabAdapter
    private lateinit var mTabLayout: TabLayout
    private lateinit var mViewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTabLayout = tabLayout
        mViewPager = viewPager
        mTabAdapter = ArticleTabAdapter(childFragmentManager)
        mTabAdapter.addFragment(ArticleFragment.newInstance(false), getString(R.string.articles_title))
        mTabAdapter.addFragment(ArticleFragment.newInstance(true), getString(R.string.my_articles_title))
        mViewPager.adapter = mTabAdapter
        mTabLayout.setupWithViewPager(mViewPager)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArticleBaseFragment()
    }

}
