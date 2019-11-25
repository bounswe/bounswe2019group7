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
import kotlinx.android.synthetic.main.fragment_event.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventFragment() : Fragment() {
/*
    val image1: Int = R.drawable.article1
    val image2 = R.drawable.article2
    val image3 = R.drawable.article3
    val image4 = R.drawable.article4
    val image5 = R.drawable.article5
    val image6 = R.drawable.article6

*/
    val imagePlaceholder = R.drawable.placeholder

/*

    val events = listOf(
        EventModel(image6,"February 17 – Deadline For Investigation on U.S. Tariffs on Car Imports","In May 2018, the Trump administration announced that it would launch an official investigation under Section 232 on whether imports of automobiles and parts harm US national security. The deadline for providing the findings of this investigation carried out by USTR (US Trade Representative) is February 17." ),
        EventModel(image5,"March 1- Deadline for U.S.-China Trade War Ceasefire","On the sidelines of the G20 summit in Buenos Aires at the beginning of December 2018, the US president and his Chinese counterpart agreed on a “ceasefire” in the trade war between the two countries. Both sides agreed to not implement any further tariff increases during a 90 day period of negotiations on ensuring better intellectual property protection in China and actively combating technology theft."),
        EventModel(image4,"Mid-March – deadline for Assessment of New North American Free Trade Agreement","Our American report trilogy ends with U.S. President Donald Trump’s signature project for re-defining U.S. trade relations: the “new” NAFTA. In substance, the new deal between Canada, Mexico, and the U.S. involves just enough changes for President Trump to claim a (short-term) win. On style, the aggressive U.S. posture has probably damaged diplomatic relations with two key trading partners (in the long-run). In any case, ratification is still pending. In the U.S., Democrats and Republicans have voiced concerns over lack of strong labor, environmental or enforcement provisions."),
        EventModel(image3,"March 29 – Article 50 Deadline for Brexit","The Article 50 two-year timer will expire on March 29, 2019 at midnight (Central European Time, 11pm UK time). At that time, the UK’s membership in the EU will come to an end.  But not only that: unless another agreement is struck between the negotiating partners, also all treaties between the EU and the UK as well as treaties with third countries that the EU has negotiated on behalf of the UK will cease to apply to the UK."),
        EventModel(image2,"April/May – Indian General Election","A number of major emerging economies will hold general elections in 2019: Nigeria in February, Indonesia in April and Argentina in October. But India’s election for the Lower House is the most highly anticipated one. Despite solid GDP growth and good approval ratings, the re-election of incumbent president Narendra Modi is no foregone conclusion. " ),
        EventModel(image1,"May 23 – European Parliamentary Elections","The stakes in the European Parliamentary Election are still high. With populists expected to continue their winning streak at the voting booth, Euro-skepticism and nationalism are likely to increase. Conversely, if the voting share of the center further erodes, the new Parliament will see more fragmentation and less stable coalitions. Both organizationally and culturally, the body will have to adjust to the absence of British members, as their seats are scheduled to drop out or be re-allotted. "),
        EventModel(image6,"June 28 – G20 Meeting in Japan","At the G20 Summit in Buenos Aires 2018, the leaders called for a reform of the WTO and for progress to be reviewed at the next summit. In light of trade wars and widespread attacks on the multilateral trading rules, reform of the WTO should become an international policy priority. However, while there is agreement on the need for reform, the positions of the protagonist countries are far apart – doubt is permitted on how much progress can be achieved in just 7 months."),
        EventModel(image5,"July/August – 2020 U.S. Presidential Candidate Announcements","On the one hand, the U.S. presidential election in November 2020 seems a long way down the road. On the other hand, President Donald Trump has never ceased to hold rallies and many possible candidates have already started fundraising and courting voters in early primary states. If the last three election cycles are any guide, major contenders should have declared their firm intention to run by summer. While they will only sharpen their platforms during the primary season in 2020, their strengths and weaknesses will be well-known right from the start. " ),
        EventModel(image4,"September 30 – End of U.S. Fiscal Year","Alongside the interpretation of the Mueller findings, the budget process will be the major battleground between the divided branches of the U.S. government. With the Democrats in charge of the House, the administration will encounter more opposition on fiscal issues. As a result, we are likely to witness – and bear the economic fallout – of several rounds of fiscal trench warfare: In March, the suspension of the debt limit expires. So does, in September, the agreement to relax caps on defense and non-defense discretionary spending."),
        EventModel(image3," October 31 – End of Mario Draghi’s Term as ECB President","The change at the helm of the European Central Bank (ECB) comes at a crucial time. Presently, the ECB is cautiously reducing its stimulus to the economy, preparing a normalization of monetary policy. Many of the more hawkish northern European central bank governors have called for a quicker normalization while southern European central bankers fear the impact this may have on a fragile economic recovery and precarious public finances. The field of contenders to the top ECB post reflects this policy controversy: they range from the more hawkish Jens Weidmann, the more pragmatic Erkki Liikanen to the more dovish Francois Villeroy de Galhau or Benoit Couré (among many other candidates)." ),
        EventModel(image2,"November – Start of Terms of New European Commission and European Council President","Since all European Commission (EC) spots and the Presidency of the European Council are up for grabs as well, the choice of the next ECB President will be part of a package deal. The battle for political direction and proper member state representation of the world’s second largest economic area will go into its crucial phase after the EP election. It will not only bring arduous negotiations among member states but could also involve a struggle for the balance of power between member states and the EP. An inconclusive EP majority structure will come in handy for member state leaders whose aversion to the EP-driven Spitzenkandidaten process is well known. As a result, a compromise candidate – Michel Barnier has been mentioned frequently – and could eventually end up at the EC’s helm." )

    )

*/

    private var allEvents: List<EventModel> = emptyList()
    private val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllEvents()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    private fun getAllEvents(){
        retrofitService.getAllEvents().enqueue(object: Callback<EventsListResponse>{
            override fun onFailure(call: Call<EventsListResponse>, t: Throwable) {
                EyeTradeUtils.toastErrorMessage(activity as Context, t)
            }

            override fun onResponse(call: Call<EventsListResponse>, response: Response<EventsListResponse>) {
                allEvents = response.body()?.allEvents?.map {
                    EventModel(
                        imagePlaceholder,
                        it.title,
                        it.content
                        )
                } ?: allEvents


                eList.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = EventAdapter(allEvents, context)
                    addItemDecoration(DividerItemDecoration(eList.context, LinearLayoutManager.VERTICAL))
                }
            }

        })
    }


    companion object {
        @JvmStatic
        fun newInstance() = EventFragment()
    }

}
