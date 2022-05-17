package com.example.loginapp.fragments.userDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.loginapp.R
import com.example.loginapp.activities.MainActivity
import com.example.loginapp.models.UserModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class UserDetailsContainer : Fragment() {


    lateinit var v: View
    lateinit var viewPager: ViewPager2
    lateinit var tabLayout: TabLayout
    lateinit var user: UserModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_user_details_container, container, false)

        tabLayout = v.findViewById(R.id.tab_layout)

        viewPager = v.findViewById(R.id.view_pager)

        user = UserDetailsContainerArgs.fromBundle(requireArguments()).userData

        return v
    }

    override fun onStart() {
        super.onStart()

        viewPager.setAdapter(ViewPagerAdapter(requireActivity(), user))
        val activity = (activity as MainActivity)

        if (activity != null) {
            activity.setTitleText("User Details")
        }
        // viewPager.isUserInputEnabled = false
        TabLayoutMediator(tabLayout, viewPager, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            when (position) {
                0 -> tab.text = "Basic Information"
                1 -> tab.text = "Assistance Information"
                else -> tab.text = "undefined"
            }
        }).attach()
    }


    class ViewPagerAdapter(fragmentActivity: FragmentActivity, user: UserModel) : FragmentStateAdapter(fragmentActivity) {
        var user: UserModel = user
        override fun createFragment(position: Int): Fragment {

            return when(position){
                0 -> BasicInfo(user)
                1 -> AttendanceInfo(user)

                else -> BasicInfo(user)
            }
        }

        override fun getItemCount(): Int {
            return TAB_COUNT
        }

        companion object {
            private const val TAB_COUNT = 2
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}