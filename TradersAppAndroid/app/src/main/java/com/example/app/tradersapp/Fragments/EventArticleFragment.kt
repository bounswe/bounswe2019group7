package com.example.app.tradersapp.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.example.app.tradersapp.*
import kotlinx.android.synthetic.main.fragment_event_article.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventArticleFragment() : Fragment() {
    var isArticle: Boolean? = null
    val image1: Int = R.drawable.article1
    val image2 = R.drawable.article2
    val image3 = R.drawable.article3
    val image4 = R.drawable.article4
    val image5 = R.drawable.article5
    val image6 = R.drawable.article6

    private var allArticles: List<EventArticleModel> = emptyList()
    private val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)

    val articles = listOf(
        EventArticleModel(image1,"The Fed has lightened the load on America’s banks","AMERICA’S BANKS had high hopes for Donald Trump’s presidency. Perhaps no industry had greater expectations of sweeping deregulation. On the campaign trail in 2016 Mr Trump had promised to dismantle the Dodd-Frank Act of 2010, which put in place stricter rules after the financial crisis. Wall Street seemed to believe him. Banks’ share prices rose by more than a third in the six months after his election", null, null, null, null ),
        EventArticleModel(image2,"How the twists and turns of the trade war are hurting growth","After welcoming the St Louis Blues, a championship-winning ice-hockey team, to the White House on October 15th, President Donald Trump fondly recalled a recent triumph of his own: last week’s tentative trade deal with China. Simply put, America will impose no further punitive tariffs on Chinese imports if China promises to buy American farm goods worth billions of dollars. How many billions? “It’s very big numbers,” Mr Trump emphasised. “I said, ‘Ask for 70.’…My people said, ‘All right, make it 20.’ I said, ‘No, make it 50....", null, null, null, null),
        EventArticleModel(image3,"Protectionism, Trade Uncertainty Are Biggest Threat to Economy, Analysts Say","The risk of a domestic economic slowdown are rising, and President Donald Trump's trade policy is one of the primary reasons why, according to a survey published Monday by the National Association for Business Economics.\n" +
                "\n" +
                "The association's quarterly economic outlook survey polls dozens of economists on their expectations for U.S. growth, inflation and policy over the course of the next two years. In the survey's latest release, analysts cited trade policy as \"the dominant risk\" to America's economic momentum, as 53% of experts considered it \"the key downside risk to the economy through 2020....\"" , null, null, null, null),
        EventArticleModel(image4,"Fed’s Evans says no more rate cuts are needed through 2020","Chicago Federal Reserve President Charles Evans agrees with the two interest rate cuts this year but thinks that’s probably enough for now. As markets anticipate another reduction later this month, Evans, a voting member of the Federal Open Market Committee, said in a speech Wednesday that policy is probably appropriate given an economy that is softening but still in good shape. Evans conceded “there is an argument for more accommodation” as a buffer against downside risks such as a slowing global economy and the U.S.-China trade war.", null, null, null, null),
        EventArticleModel(image5, "The Effect of Dodd-Frank on the Profitability of Community Banks: An Econometric Model","The Dodd-Frank Wall Street Reform and Consumer Protection Act of 2010 was created in response to the financial crisis of 2008 in order to abate poor practices performed by large banks that were deemed “too big to fail.” The issue to be addressed is the effect of the Dodd-Frank Act on community banks. This topic has been previously unexamined econometrically, and has only been explained through anecdotal and survey-based research. There is survey-based evidence that community banks are unnecessarily strained by the new compliance requirements imposed by the Dodd-Frank Act...." ,null, null, null, null),
        EventArticleModel(image6,"A massive money-laundering scandal stains the image of Nordic banks","Danske bank’s headquarters in Copenhagen, reminiscent of a Greek temple, speaks of an illustrious past. But Denmark’s biggest bank has “no vanity left”, says a spokesman. Since 2008 it has been embroiled in a disaster every five years. After one during the financial crisis, it was again in crisis mode in 2013 when the board sacked Eivind Kolding after 18 catastrophic months at its helm. Last year Thomas Borgen, Mr Kolding’s successor, resigned amid revelations about Danske’s role in a vast money-laundering scandal. In May Mr Borgen was charged by Denmark’s prosecutor. The money-laundering crisis is the most damaging yet for Danske, and for other Nordic banks allegedly involved. Last year the Organised Crime and Corruption Reporting Project, a group of investigative journalists, gave Danske its “Corrupt Actor of the Year” award...." , null, null, null, null),
        EventArticleModel(image1,"Is the board overseeing Puerto Rico’s bankruptcy unconstitutional?","Are you and your client here just to defend the integrity of the Constitution?” asked Samuel Alito, an associate justice of the America’s Supreme Court, on October 15th. “Or would one be excessively cynical to think that something else is involved here, involving money?” The court had heard arguments from Donald Verrilli, for the board overseeing Puerto Rico’s bankruptcy; Jeffrey Wall, for the federal government; and Theodore Olson, to whom the judge’s remarks were addressed. His client is Aurelius Capital Management, a hedge fund that invests in distressed debt. At stake are \$125bn of creditor claims. Aurelius was founded in 2006 by Mark Brodsky, formerly of Elliott Management. Both funds were involved in a fight with Argentina about its bonds in 2014, during which Cristina Fernández de Kirchner, then the president, dubbed them “vultures”. They were among six funds that held out for full repayment. In 2016 they settled favourably and were paid \$9.3bn. Aurelius now aims to get the Supreme Court to declare the Puerto Rico oversight board unconstitutional, in the hope of improving on its offer to the territory’s creditors of 35-45 cents on the dollar...." , null, null, null, null),
        EventArticleModel(image2,"The issuer of a star cryptocurrency is being sued for \$1.4trn","Tether aimed to become a more reliable alternative to Bitcoin, the best-known cryptocurrency. With a \$4.1bn market capitalisation, it is now the fifth-largest virtual currency. But its efforts to gain investors’ trust have fallen short. On October 6th a group filed a class-action lawsuit in New York, accusing Tether of being “part-fraud, part-pump-and-dump, and part-money laundering”. They call for truly startling damages: more than \$1.4trn. In response to The Economist’s queries, Tether’s general counsel said that “the lawsuit is meritless and the plaintiffs’ complaint is rife with errors.” The firm “has not used Tethers to manipulate any market”, he added, and operates in “full conformity with applicable laws”...." , null, null, null, null),
        EventArticleModel(image3,"The ECB cuts interest rates and restarts quantitative easing","For weeks the question has not been if the European Central Bank would ease policy on September 12th, but how. The euro area’s economy has been flagging and Mario Draghi, the ECB’s president, whose term ends on October 31st, had indicated that he would act to revive it. But with the bank’s policy rate already negative, it was clear he would have to rely on a combination of tools. Meanwhile Dutch and German hawks had insisted that they saw little need for large stimulus. In the event Mr Draghi won the hawks over, and unveiled several easing measures. The ECB cut the interest rate on bank reserves, money banks deposit with it, for the first time since 2016, by a tenth of a percentage point to -0.5%. It will restart its quantitative-easing (QE) scheme, which it drew to a close last year. From November it will buy €20bn-worth (\$22bn) of bonds a month....." , null, null, null, null),
        EventArticleModel(image4,"The chilling economic effects of Brexit uncertainty are intensifying","Since Britain voted to leave the European Union (eu) in June 2016, Leavers have been gloating. Despite the Remain camp’s dire predictions, the economy seemed to trundle on well enough. But the crowing is dying down. Figures released on August 9th showed that Britain’s gdp shrank in the second quarter. And a growing body of research suggests that Brexit-related uncertainty is doing subtle but serious economic damage. A paper published early this year by Meredith Crowley, Oliver Exton and Lu Han of the University of Cambridge reckons that uncertainty over trade policy has dented export prospects. Had the vote not taken place, 5% more firms would have exported new products to the eu in 2016 alone.....", null, null, null, null)


    )


    val events = listOf(
        EventArticleModel(image6,"February 17 – Deadline For Investigation on U.S. Tariffs on Car Imports","In May 2018, the Trump administration announced that it would launch an official investigation under Section 232 on whether imports of automobiles and parts harm US national security. The deadline for providing the findings of this investigation carried out by USTR (US Trade Representative) is February 17." , null, null, null, null),
        EventArticleModel(image5,"March 1- Deadline for U.S.-China Trade War Ceasefire","On the sidelines of the G20 summit in Buenos Aires at the beginning of December 2018, the US president and his Chinese counterpart agreed on a “ceasefire” in the trade war between the two countries. Both sides agreed to not implement any further tariff increases during a 90 day period of negotiations on ensuring better intellectual property protection in China and actively combating technology theft." , null, null, null, null),
        EventArticleModel(image4,"Mid-March – deadline for Assessment of New North American Free Trade Agreement","Our American report trilogy ends with U.S. President Donald Trump’s signature project for re-defining U.S. trade relations: the “new” NAFTA. In substance, the new deal between Canada, Mexico, and the U.S. involves just enough changes for President Trump to claim a (short-term) win. On style, the aggressive U.S. posture has probably damaged diplomatic relations with two key trading partners (in the long-run). In any case, ratification is still pending. In the U.S., Democrats and Republicans have voiced concerns over lack of strong labor, environmental or enforcement provisions." , null, null, null, null),
        EventArticleModel(image3,"March 29 – Article 50 Deadline for Brexit","The Article 50 two-year timer will expire on March 29, 2019 at midnight (Central European Time, 11pm UK time). At that time, the UK’s membership in the EU will come to an end.  But not only that: unless another agreement is struck between the negotiating partners, also all treaties between the EU and the UK as well as treaties with third countries that the EU has negotiated on behalf of the UK will cease to apply to the UK." , null, null, null, null),
        EventArticleModel(image2,"April/May – Indian General Election","A number of major emerging economies will hold general elections in 2019: Nigeria in February, Indonesia in April and Argentina in October. But India’s election for the Lower House is the most highly anticipated one. Despite solid GDP growth and good approval ratings, the re-election of incumbent president Narendra Modi is no foregone conclusion. " , null, null, null, null),
        EventArticleModel(image1,"May 23 – European Parliamentary Elections","The stakes in the European Parliamentary Election are still high. With populists expected to continue their winning streak at the voting booth, Euro-skepticism and nationalism are likely to increase. Conversely, if the voting share of the center further erodes, the new Parliament will see more fragmentation and less stable coalitions. Both organizationally and culturally, the body will have to adjust to the absence of British members, as their seats are scheduled to drop out or be re-allotted. " , null, null, null, null),
        EventArticleModel(image6,"June 28 – G20 Meeting in Japan","At the G20 Summit in Buenos Aires 2018, the leaders called for a reform of the WTO and for progress to be reviewed at the next summit. In light of trade wars and widespread attacks on the multilateral trading rules, reform of the WTO should become an international policy priority. However, while there is agreement on the need for reform, the positions of the protagonist countries are far apart – doubt is permitted on how much progress can be achieved in just 7 months." , null, null, null, null),
        EventArticleModel(image5,"July/August – 2020 U.S. Presidential Candidate Announcements","On the one hand, the U.S. presidential election in November 2020 seems a long way down the road. On the other hand, President Donald Trump has never ceased to hold rallies and many possible candidates have already started fundraising and courting voters in early primary states. If the last three election cycles are any guide, major contenders should have declared their firm intention to run by summer. While they will only sharpen their platforms during the primary season in 2020, their strengths and weaknesses will be well-known right from the start. " , null, null, null, null),
        EventArticleModel(image4,"September 30 – End of U.S. Fiscal Year","Alongside the interpretation of the Mueller findings, the budget process will be the major battleground between the divided branches of the U.S. government. With the Democrats in charge of the House, the administration will encounter more opposition on fiscal issues. As a result, we are likely to witness – and bear the economic fallout – of several rounds of fiscal trench warfare: In March, the suspension of the debt limit expires. So does, in September, the agreement to relax caps on defense and non-defense discretionary spending." , null, null, null, null),
        EventArticleModel(image3," October 31 – End of Mario Draghi’s Term as ECB President","The change at the helm of the European Central Bank (ECB) comes at a crucial time. Presently, the ECB is cautiously reducing its stimulus to the economy, preparing a normalization of monetary policy. Many of the more hawkish northern European central bank governors have called for a quicker normalization while southern European central bankers fear the impact this may have on a fragile economic recovery and precarious public finances. The field of contenders to the top ECB post reflects this policy controversy: they range from the more hawkish Jens Weidmann, the more pragmatic Erkki Liikanen to the more dovish Francois Villeroy de Galhau or Benoit Couré (among many other candidates)." , null, null, null, null),
        EventArticleModel(image2,"November – Start of Terms of New European Commission and European Council President","Since all European Commission (EC) spots and the Presidency of the European Council are up for grabs as well, the choice of the next ECB President will be part of a package deal. The battle for political direction and proper member state representation of the world’s second largest economic area will go into its crucial phase after the EP election. It will not only bring arduous negotiations among member states but could also involve a struggle for the balance of power between member states and the EP. An inconclusive EP majority structure will come in handy for member state leaders whose aversion to the EP-driven Spitzenkandidaten process is well known. As a result, a compromise candidate – Michel Barnier has been mentioned frequently – and could eventually end up at the EC’s helm." , null, null, null, null)

    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isArticle = arguments?.getBoolean("isArticle")
        if(isArticle == true){
            getAllArticles()
        }
        else{
            // TODO: Move this part into getAllEvents() once it's implemented

            eaList.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = EventArticleAdapter(events, context)
            }
            //getAllEvents()
        }
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
        return inflater.inflate(R.layout.fragment_event_article, container, false)
    }

    private fun getAllArticles(){
        retrofitService.getAllArticles().enqueue(object: Callback<ArticlesListResponse>{
            override fun onFailure(call: Call<ArticlesListResponse>, t: Throwable) {
                Toast.makeText(
                    activity,
                    "Unexpected server error occurred. Please try again.",
                    Toast.LENGTH_SHORT
                ).show();
            }

            override fun onResponse(call: Call<ArticlesListResponse>, response: Response<ArticlesListResponse>) {
                allArticles = response.body()?.allArticles?.map {
                    EventArticleModel(image1, it.title, it.content, it.authorName, it.authorSurname, it.score, it.changeDate)
                } ?: allArticles


                eaList.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = EventArticleAdapter(allArticles, context)
                    addItemDecoration(DividerItemDecoration(eaList.context, LinearLayoutManager.VERTICAL))
                }
            }

        })
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
