package com.example.app.tradersapp.Fragments


import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.ActivityOptionsCompat
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
    private var followersCount = 0

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

        EyeTradeUtils.showSpinner(activity)

        val retrofitService =
            RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)

        val bundle = this.arguments

        val otherEmail = bundle?.getString("email")

        val followButton = follow
        val unfollowButton = unfollow

        if(otherEmail.isNullOrBlank()) { // self profile
            followButton.visibility = View.GONE

            privacySwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked){
                    retrofitService.updatePrivacy(sp?.getString("token", null), "PRIVATE_USER").enqueue(object: Callback<SelfProfileInformationResponse>{
                        override fun onFailure(call: Call<SelfProfileInformationResponse>, t: Throwable) {
                            EyeTradeUtils.toastErrorMessage(activity!!.applicationContext, t)
                        }

                        override fun onResponse(call: Call<SelfProfileInformationResponse>, response: Response<SelfProfileInformationResponse>) {
                            // Do nothing
                        }

                    })
                }
                else{
                    retrofitService.updatePrivacy(sp?.getString("token", null), "PUBLIC_USER").enqueue(object: Callback<SelfProfileInformationResponse>{
                        override fun onFailure(call: Call<SelfProfileInformationResponse>, t: Throwable) {
                            EyeTradeUtils.toastErrorMessage(activity!!.applicationContext, t)
                        }

                        override fun onResponse(call: Call<SelfProfileInformationResponse>, response: Response<SelfProfileInformationResponse>) {
                            // Do nothing
                        }

                    })
                }
            }

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
                            if(body?.privacyType == "PRIVATE_USER"){
                                privacySwitch.isChecked = true
                            }
                            EyeTradeUtils.hideSpinner(activity)
                        } else {
                            Toast.makeText(
                                activity?.applicationContext,
                                "Not logged in.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                })
        }else{  // other profile
            privacySwitch.visibility = View.GONE
            privacyText.visibility = View.GONE
            updateProfile.visibility = View.GONE
            email.visibility = View.GONE // don't show other people's email addresses

            retrofitService.getOtherProfileInformation(sp?.getString("token", null), otherEmail)
                .enqueue(object : Callback<OtherProfileInformationResponse> {
                    override fun onFailure(
                        call: Call<OtherProfileInformationResponse>,
                        t: Throwable
                    ) {
                        Log.i("ApiRequest", "Request failed: " + t.toString())
                        Toast.makeText(
                            activity?.applicationContext,
                            "You cannot see this profile since it's private.",
                            Toast.LENGTH_SHORT
                        ).show()
                        followButton.visibility = View.GONE
                        EyeTradeUtils.hideSpinner(activity)
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
                            EyeTradeUtils.hideSpinner(activity)

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

        // Get the followers of the profile, if this user is already in the list, don't allow to follow again
        retrofitService.getFollowers(sp?.getString("token", null), otherEmail).enqueue(object: Callback<List<MinimalUserResponse>>{
            override fun onFailure(call: Call<List<MinimalUserResponse>>, t: Throwable) {
                EyeTradeUtils.toastErrorMessage(activity!!.applicationContext, t)
            }

            override fun onResponse(call: Call<List<MinimalUserResponse>>, response: Response<List<MinimalUserResponse>>) {
                val list = response.body()!!
                followersCount = list.size
                val thisUserId = sp?.getString("userId", null)
                for(user in list){
                    if(user.id == thisUserId){  // Already following
                        follow.visibility = View.GONE
                        unfollowButton.visibility = View.VISIBLE
                        break
                    }
                }
            }
        })

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
                        follow.visibility = View.GONE
                        unfollow.visibility = View.VISIBLE
                        followersText.text = (followersCount + 1).toString()
                        followersCount += 1
                        Toast.makeText(context, "You are now following ${name.text}", Toast.LENGTH_SHORT).show()
                        val profileFragment = ProfileFragment()
                        val profileBundle = Bundle()
                        profileBundle.apply{
                            putString("email", otherEmail)
                        }
                        profileFragment.arguments = profileBundle


                        (context as FragmentActivity).supportFragmentManager.popBackStack()
                        val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.nav_host_fragment, profileFragment)
                        transaction.addToBackStack(null)
                        transaction.commit()
                    }else {
                        Toast.makeText(context, "There was an error following ${name.text}", Toast.LENGTH_SHORT).show();
                    }
                }

            })
        }

        unfollowButton.setOnClickListener {
            retrofitService.unfollow(sp?.getString("token", null), otherEmail).enqueue(object: Callback<MinimalUserResponse>{
                override fun onFailure(call: Call<MinimalUserResponse>, t: Throwable) {
                    EyeTradeUtils.toastErrorMessage(activity!!.applicationContext, t)
                }

                override fun onResponse(call: Call<MinimalUserResponse>, response: Response<MinimalUserResponse>) {
                    followersText.text = (followersCount - 1).toString()
                    followersCount -= 1
                    unfollowButton.visibility = View.GONE
                    followButton.visibility = View.VISIBLE
                    Toast.makeText(context, "You are not following ${name.text} anymore", Toast.LENGTH_SHORT).show()
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
