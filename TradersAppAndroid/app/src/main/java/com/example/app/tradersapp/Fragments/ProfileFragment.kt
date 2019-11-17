package com.example.app.tradersapp.Fragments


import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.app.tradersapp.*
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private var sp:SharedPreferences?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sp = PreferenceManager.getDefaultSharedPreferences(context)

        val retrofitService = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        retrofitService.getUserProfileInformation(sp?.getString("token",null)).enqueue(object: Callback<ProfileInformationResponse>{
            override fun onFailure(call: Call<ProfileInformationResponse>, t: Throwable) {
                Log.i("ApiRequest", "Request failed: " + t.toString())
                Toast.makeText(
                    activity?.applicationContext,
                    "Unexpected server error occurred. Please try again.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<ProfileInformationResponse>, response: Response<ProfileInformationResponse>) {
                if (response.code() == 200) {
                    val body = response.body()
                    name.text = body?.name + " " + body?.surname
                    email.text = body?.email
                }
                else {
                    Toast.makeText(activity?.applicationContext, "Not logged in.", Toast.LENGTH_SHORT).show()
                }
            }

        })

        val profileImage = profilePic
        val followButton = follow

        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.hansgraham)
        val mDrawable: RoundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources,bitmap)
        mDrawable.isCircular = true
        profileImage.setImageDrawable(mDrawable)
        //mDrawable.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), PorterDuff.Mode.DST_OVER);

        followButton.setOnClickListener {
            // TODO: Handle following feature
        }

        updateProfile.setOnClickListener {
            val intent = Intent(context, RegistrationActivity::class.java)
            intent.putExtra("updateProfile", true)
            startActivity(intent)
        }

        linearLayoutFollowers.setOnClickListener {
            (activity as HomepageActivity).startFragment(FollowersFragment.newInstance(true))
        }

        linearLayoutFollowing.setOnClickListener {
            (activity as HomepageActivity).startFragment(FollowersFragment.newInstance(false))
        }
    }


}
