package com.example.loginapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels

import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.loginapp.R
import com.example.loginapp.activities.MainActivity
import com.example.loginapp.fragments.dataLists.CoursesList

import com.example.loginapp.fragments.dataLists.UsersList
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MainMenu : Fragment() {

    lateinit var v: View
    lateinit var viewPager: ViewPager2
    lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_main_menu, container, false)

        tabLayout = v.findViewById(R.id.main_menu_tab_layout)

        viewPager = v.findViewById(R.id.main_menu_view_pager)
        
        return v
    }

    override fun onStart() {
        super.onStart()

        viewPager.setAdapter(ViewPagerAdapter(requireActivity()))
        val activity = (activity as MainActivity)

        if (activity != null) {
            activity.setTitleText("Main Menu")
        }
        // viewPager.isUserInputEnabled = false
        TabLayoutMediator(tabLayout, viewPager, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            when (position) {
                0 -> tab.text = "Users"
                1 -> tab.text = "Courses"
                else -> tab.text = "undefined"
            }
        }).attach()
    }


    class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        override fun createFragment(position: Int): Fragment {

            return when(position){
                0 -> UsersList()
                1 -> CoursesList()

                else -> UsersList()
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