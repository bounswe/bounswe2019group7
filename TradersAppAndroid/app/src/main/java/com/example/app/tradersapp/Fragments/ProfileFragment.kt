package com.example.app.tradersapp.Fragments


import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.app.tradersapp.*
import kotlinx.android.synthetic.main.currency_item.*
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Header

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

        if(sp?.getString("token", null).isNullOrBlank()){
            Toast.makeText(context, "Please login to view user profiles", Toast.LENGTH_SHORT).show()
            (context as FragmentActivity).supportFragmentManager.popBackStack() //  go back
            return
        }

        val retrofitService =
            RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)

        val bundle = this.arguments

        val otherEmail = bundle?.getString("email")

        val profileImage = profilePic
        val followButton = follow

        if(otherEmail.isNullOrBlank()) { // self profile
            followButton.visibility = View.GONE

            retrofitService.getSelfProfileInformation(sp?.getString("token", null))
                .enqueue(object : Callback<SelfProfileInformationResponse> {
                    override fun onFailure(
                        call: Call<SelfProfileInformationResponse>,
                        t: Throwable
                    ) {
                        Log.i("ApiRequest", "Request failed: " + t.toString())
                        Toast.makeText(
                            activity?.applicationContext,
                            "Unexpected server error occurred. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<SelfProfileInformationResponse>,
                        response: Response<SelfProfileInformationResponse>
                    ) {
                        if (response.code() == 200) {
                            val body = response.body()
                            name.text = body?.name + " " + body?.surname
                            email.text = body?.email
                            followersText.text = body?.followerCount.toString()
                            followingText.text = body?.followingCount.toString()
                        } else {
                            Toast.makeText(
                                activity?.applicationContext,
                                "Not logged in.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                })
        }else{
            updateProfile.visibility = View.GONE
            retrofitService.getOtherProfileInformation(sp?.getString("token", null), otherEmail)
                .enqueue(object : Callback<OtherProfileInformationResponse> {
                    override fun onFailure(
                        call: Call<OtherProfileInformationResponse>,
                        t: Throwable
                    ) {
                        Log.i("ApiRequest", "Request failed: " + t.toString())
                        Toast.makeText(
                            activity?.applicationContext,
                            "Unexpected server error occurred. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<OtherProfileInformationResponse>,
                        response: Response<OtherProfileInformationResponse>
                    ) {
                        if (response.code() == 200) {
                            val body = response.body()
                            name.text = body?.name + " " + body?.surname
                            email.text = body?.email
                            followersText.text = body?.followerCount.toString()
                            followingText.text = body?.followingCount.toString()
                        } else {
                            Toast.makeText(
                                activity?.applicationContext,
                                "User not found.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                })
        }

        /*
        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.hansgraham)
        val mDrawable: RoundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources,bitmap)
        mDrawable.isCircular = true
        profileImage.setImageDrawable(mDrawable)
        */
        //mDrawable.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), PorterDuff.Mode.DST_OVER);

        followButton.setOnClickListener {
            retrofitService.followUser(sp?.getString("token", null), otherEmail).enqueue(object: Callback<MinimalUserResponse>{
                override fun onFailure(call: Call<MinimalUserResponse>, t: Throwable) {
                    EyeTradeUtils.toastErrorMessage(context!!, t)
                }

                override fun onResponse(
                    call: Call<MinimalUserResponse>,
                    response: Response<MinimalUserResponse>
                ) {
                    if(response.code() == 200){
                        Toast.makeText(context, "You are now following ${name.text}", Toast.LENGTH_SHORT).show()
                    }else {
                        Toast.makeText(context, "There was an error following ${name.text}", Toast.LENGTH_SHORT).show();
                    }
                }

            })
        }

        updateProfile.setOnClickListener {
            val intent = Intent(context, RegistrationActivity::class.java)
            intent.putExtra("updateProfile", true)
            startActivity(intent)
        }
    }
}
