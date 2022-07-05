package com.example.loginapp.fragments.courseDetails

import android.content.Context
import android.content.SharedPreferences
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
import com.example.loginapp.fragments.userDetails.AttendanceInfo
import com.example.loginapp.fragments.userDetails.BasicInfo
import com.example.loginapp.fragments.userDetails.UserDetailsContainerArgs
import com.example.loginapp.models.CourseModel
import com.example.loginapp.models.UserModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CourseDetailsContainer :Fragment() {

    lateinit var v: View
    lateinit var viewPager: ViewPager2
    lateinit var tabLayout: TabLayout
    lateinit var course: CourseModel
    private lateinit var userInfo: SharedPreferences
    private lateinit var userId: String
    private lateinit var userRole: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_user_details_container, container, false)

        tabLayout = v.findViewById(R.id.tab_layout)

        viewPager = v.findViewById(R.id.view_pager)

        course = CourseDetailsContainerArgs.fromBundle(requireArguments()).courseData

        return v
    }

    override fun onStart() {
        super.onStart()
        val activity = (activity as MainActivity)

        if (activity != null) {
            activity.setTitleText("Course Details")
        }
        var context = context
        if(context != null){
            userInfo = context.getSharedPreferences("UserInformation", Context.MODE_PRIVATE)
            userId = userInfo.getString("UUID","")!!
            userRole = userInfo.getString("ROLE","")!!
        }
        viewPager.adapter = ViewPagerAdapter(requireActivity(), course, userRole)
        TabLayoutMediator(tabLayout, viewPager, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            if(userRole == "Student"){
                when (position) {
                    0 -> tab.text = "Information"
//                    1 -> tab.text = "Enrollment Requests"
                    else -> tab.text = "undefined"
                }
            }
            if(userRole == "Teacher"){
                when (position) {
                    0 -> tab.text = "Information"
                    1 -> tab.text = "Enrollment Requests"
                    else -> tab.text = "undefined"
                }
            }

        }).attach()
    }


    class ViewPagerAdapter(fragmentActivity: FragmentActivity, course: CourseModel, userRole: String) : FragmentStateAdapter(fragmentActivity) {
        var course: CourseModel = course
        var userRole: String = userRole
        override fun createFragment(position: Int): Fragment {

            return when(position){
                0 -> CourseBasicInfo(course)
                1 -> CourseEnrollmentRequests(course)

                else -> CourseBasicInfo(course)
            }
        }

        override fun getItemCount(): Int {
            var tabCount = 1
            if(userRole == "Teacher"){
                tabCount = 2
            }
            return tabCount
        }

        companion object {
            private const val TAB_COUNT = 2
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}