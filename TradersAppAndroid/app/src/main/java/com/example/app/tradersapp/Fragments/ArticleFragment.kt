package com.example.app.tradersapp.Fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app.tradersapp.*
import kotlinx.android.synthetic.main.fragment_article.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleFragment() : Fragment() {

    val imagePlaceholder = R.drawable.placeholder

    private var allArticles: List<ArticleModel> = emptyList()
    private val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)

    /*val articles = listOf(
        ArticleModel(image1,"The Fed has lightened the load on America’s banks","AMERICA’S BANKS had high hopes for Donald Trump’s presidency. Perhaps no industry had greater expectations of sweeping deregulation. On the campaign trail in 2016 Mr Trump had promised to dismantle the Dodd-Frank Act of 2010, which put in place stricter rules after the financial crisis. Wall Street seemed to believe him. Banks’ share prices rose by more than a third in the six months after his election", null, null, null, null ),
        ArticleModel(image2,"How the twists and turns of the trade war are hurting growth","After welcoming the St Louis Blues, a championship-winning ice-hockey team, to the White House on October 15th, President Donald Trump fondly recalled a recent triumph of his own: last week’s tentative trade deal with China. Simply put, America will impose no further punitive tariffs on Chinese imports if China promises to buy American farm goods worth billions of dollars. How many billions? “It’s very big numbers,” Mr Trump emphasised. “I said, ‘Ask for 70.’…My people said, ‘All right, make it 20.’ I said, ‘No, make it 50....", null, null, null, null),
        ArticleModel(image3,"Protectionism, Trade Uncertainty Are Biggest Threat to Economy, Analysts Say","The risk of a domestic economic slowdown are rising, and President Donald Trump's trade policy is one of the primary reasons why, according to a survey published Monday by the National Association for Business Economics.\n" +
                "\n" +
                "The association's quarterly economic outlook survey polls dozens of economists on their expectations for U.S. growth, inflation and policy over the course of the next two years. In the survey's latest release, analysts cited trade policy as \"the dominant risk\" to America's economic momentum, as 53% of experts considered it \"the key downside risk to the economy through 2020....\"" , null, null, null, null),
        ArticleModel(image4,"Fed’s Evans says no more rate cuts are needed through 2020","Chicago Federal Reserve President Charles Evans agrees with the two interest rate cuts this year but thinks that’s probably enough for now. As markets anticipate another reduction later this month, Evans, a voting member of the Federal Open Market Committee, said in a speech Wednesday that policy is probably appropriate given an economy that is softening but still in good shape. Evans conceded “there is an argument for more accommodation” as a buffer against downside risks such as a slowing global economy and the U.S.-China trade war.", null, null, null, null),
        ArticleModel(image5, "The Effect of Dodd-Frank on the Profitability of Community Banks: An Econometric Model","The Dodd-Frank Wall Street Reform and Consumer Protection Act of 2010 was created in response to the financial crisis of 2008 in order to abate poor practices performed by large banks that were deemed “too big to fail.” The issue to be addressed is the effect of the Dodd-Frank Act on community banks. This topic has been previously unexamined econometrically, and has only been explained through anecdotal and survey-based research. There is survey-based evidence that community banks are unnecessarily strained by the new compliance requirements imposed by the Dodd-Frank Act...." ,null, null, null, null),
        ArticleModel(image6,"A massive money-laundering scandal stains the image of Nordic banks","Danske bank’s headquarters in Copenhagen, reminiscent of a Greek temple, speaks of an illustrious past. But Denmark’s biggest bank has “no vanity left”, says a spokesman. Since 2008 it has been embroiled in a disaster every five years. After one during the financial crisis, it was again in crisis mode in 2013 when the board sacked Eivind Kolding after 18 catastrophic months at its helm. Last year Thomas Borgen, Mr Kolding’s successor, resigned amid revelations about Danske’s role in a vast money-laundering scandal. In May Mr Borgen was charged by Denmark’s prosecutor. The money-laundering crisis is the most damaging yet for Danske, and for other Nordic banks allegedly involved. Last year the Organised Crime and Corruption Reporting Project, a group of investigative journalists, gave Danske its “Corrupt Actor of the Year” award...." , null, null, null, null),
        ArticleModel(image1,"Is the board overseeing Puerto Rico’s bankruptcy unconstitutional?","Are you and your client here just to defend the integrity of the Constitution?” asked Samuel Alito, an associate justice of the America’s Supreme Court, on October 15th. “Or would one be excessively cynical to think that something else is involved here, involving money?” The court had heard arguments from Donald Verrilli, for the board overseeing Puerto Rico’s bankruptcy; Jeffrey Wall, for the federal government; and Theodore Olson, to whom the judge’s remarks were addressed. His client is Aurelius Capital Management, a hedge fund that invests in distressed debt. At stake are \$125bn of creditor claims. Aurelius was founded in 2006 by Mark Brodsky, formerly of Elliott Management. Both funds were involved in a fight with Argentina about its bonds in 2014, during which Cristina Fernández de Kirchner, then the president, dubbed them “vultures”. They were among six funds that held out for full repayment. In 2016 they settled favourably and were paid \$9.3bn. Aurelius now aims to get the Supreme Court to declare the Puerto Rico oversight board unconstitutional, in the hope of improving on its offer to the territory’s creditors of 35-45 cents on the dollar...." , null, null, null, null),
        ArticleModel(image2,"The issuer of a star cryptocurrency is being sued for \$1.4trn","Tether aimed to become a more reliable alternative to Bitcoin, the best-known cryptocurrency. With a \$4.1bn market capitalisation, it is now the fifth-largest virtual currency. But its efforts to gain investors’ trust have fallen short. On October 6th a group filed a class-action lawsuit in New York, accusing Tether of being “part-fraud, part-pump-and-dump, and part-money laundering”. They call for truly startling damages: more than \$1.4trn. In response to The Economist’s queries, Tether’s general counsel said that “the lawsuit is meritless and the plaintiffs’ complaint is rife with errors.” The firm “has not used Tethers to manipulate any market”, he added, and operates in “full conformity with applicable laws”...." , null, null, null, null),
        ArticleModel(image3,"The ECB cuts interest rates and restarts quantitative easing","For weeks the question has not been if the European Central Bank would ease policy on September 12th, but how. The euro area’s economy has been flagging and Mario Draghi, the ECB’s president, whose term ends on October 31st, had indicated that he would act to revive it. But with the bank’s policy rate already negative, it was clear he would have to rely on a combination of tools. Meanwhile Dutch and German hawks had insisted that they saw little need for large stimulus. In the event Mr Draghi won the hawks over, and unveiled several easing measures. The ECB cut the interest rate on bank reserves, money banks deposit with it, for the first time since 2016, by a tenth of a percentage point to -0.5%. It will restart its quantitative-easing (QE) scheme, which it drew to a close last year. From November it will buy €20bn-worth (\$22bn) of bonds a month....." , null, null, null, null),
        ArticleModel(image4,"The chilling economic effects of Brexit uncertainty are intensifying","Since Britain voted to leave the European Union (eu) in June 2016, Leavers have been gloating. Despite the Remain camp’s dire predictions, the economy seemed to trundle on well enough. But the crowing is dying down. Figures released on August 9th showed that Britain’s gdp shrank in the second quarter. And a growing body of research suggests that Brexit-related uncertainty is doing subtle but serious economic damage. A paper published early this year by Meredith Crowley, Oliver Exton and Lu Han of the University of Cambridge reckons that uncertainty over trade policy has dented export prospects. Had the vote not taken place, 5% more firms would have exported new products to the eu in 2016 alone.....", null, null, null, null)


    )   */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllArticles()
        floatingButton.setOnClickListener {
            val addArticleFragment = AddArticleFragment()
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, addArticleFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    private fun getAllArticles(){
        retrofitService.getAllArticles().enqueue(object: Callback<ArticlesListResponse>{
            override fun onFailure(call: Call<ArticlesListResponse>, t: Throwable) {
                EyeTradeUtils.toastErrorMessage(activity as Context, t)
            }

            override fun onResponse(call: Call<ArticlesListResponse>, response: Response<ArticlesListResponse>) {
                allArticles = response.body()?.allArticles?.map {
                    ArticleModel(imagePlaceholder, it.title, it.content, it.authorName, it.authorSurname, it.score, it.changeDate)
                } ?: allArticles


                aList.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = ArticleAdapter(allArticles, context)
                    addItemDecoration(DividerItemDecoration(aList.context, LinearLayoutManager.VERTICAL))
                }
            }

        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArticleFragment()
    }

}
