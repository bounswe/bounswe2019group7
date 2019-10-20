package com.example.app.tradersapp.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app.tradersapp.EventArticleAdapter
import com.example.app.tradersapp.R
import kotlinx.android.synthetic.main.fragment_event_article.*

// TODO: Rename parameter arguments, choose names that match

class EventArticleFragment() : Fragment() {
    var isArticle: Boolean? = null

    val articles = listOf(
        EventArticleModel("","The Fed has lightened the load on America’s banks","AMERICA’S BANKS had high hopes for Donald Trump’s presidency. Perhaps no industry had greater expectations of sweeping deregulation. On the campaign trail in 2016 Mr Trump had promised to dismantle the Dodd-Frank Act of 2010, which put in place stricter rules after the financial crisis. Wall Street seemed to believe him. Banks’ share prices rose by more than a third in the six months after his election"),
        EventArticleModel("","How the twists and turns of the trade war are hurting growth","After welcoming the St Louis Blues, a championship-winning ice-hockey team, to the White House on October 15th, President Donald Trump fondly recalled a recent triumph of his own: last week’s tentative trade deal with China. Simply put, America will impose no further punitive tariffs on Chinese imports if China promises to buy American farm goods worth billions of dollars. How many billions? “It’s very big numbers,” Mr Trump emphasised. “I said, ‘Ask for 70.’…My people said, ‘All right, make it 20.’ I said, ‘No, make it 50."),
        EventArticleModel("","Protectionism, Trade Uncertainty Are Biggest Threat to Economy, Analysts Say","The risk of a domestic economic slowdown are rising, and President Donald Trump's trade policy is one of the primary reasons why, according to a survey published Monday by the National Association for Business Economics.\n" +
                "\n" +
                "The association's quarterly economic outlook survey polls dozens of economists on their expectations for U.S. growth, inflation and policy over the course of the next two years. In the survey's latest release, analysts cited trade policy as \"the dominant risk\" to America's economic momentum, as 53% of experts considered it \"the key downside risk to the economy through 2020.\""),
        EventArticleModel("","Fed’s Evans says no more rate cuts are needed through 2020","Chicago Federal Reserve President Charles Evans agrees with the two interest rate cuts this year but thinks that’s probably enough for now. As markets anticipate another reduction later this month, Evans, a voting member of the Federal Open Market Committee, said in a speech Wednesday that policy is probably appropriate given an economy that is softening but still in good shape. Evans conceded “there is an argument for more accommodation” as a buffer against downside risks such as a slowing global economy and the U.S.-China trade war."),
        EventArticleModel("", "The Effect of Dodd-Frank on the Profitability of Community Banks: An Econometric Model","The Dodd-Frank Wall Street Reform and Consumer Protection Act of 2010 was created in response to the financial crisis of 2008 in order to abate poor practices performed by large banks that were deemed “too big to fail.” The issue to be addressed is the effect of the Dodd-Frank Act on community banks. This topic has been previously unexamined econometrically, and has only been explained through anecdotal and survey-based research. There is survey-based evidence that community banks are unnecessarily strained by the new compliance requirements imposed by the Dodd-Frank Act.")

    )


    val events = listOf(
        EventArticleModel("","",""),
        EventArticleModel("","entveveve","kdjfsd"),
        EventArticleModel("","event","kdjfsd")


    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isArticle = arguments?.getBoolean("isArticle");
        eaList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = EventArticleAdapter(if(isArticle == true) articles else events)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_article, container, false)
    }


    companion object {
        @JvmStatic
        fun newInstance(isArticle: Boolean) = EventArticleFragment().apply {
            arguments = Bundle().apply {
                putBoolean("isArticle", isArticle)
            }
        }
    }
}
