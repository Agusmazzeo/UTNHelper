package com.example.loginapp.fragments.courseDetails

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.R
import com.example.loginapp.activities.MainActivity
import com.example.loginapp.adapter.CourseEnrollmentRequestsAdapter
import com.example.loginapp.adapter.UsersAdapter
import com.example.loginapp.fragments.dataLists.UsersListDirections
import com.example.loginapp.models.CourseModel
import com.example.loginapp.models.EnrollmentModel
import com.example.loginapp.models.UserModel
import com.example.loginapp.viewmodels.courseDetails.CourseEnrollmentRequestsViewModel

class CourseEnrollmentRequests(course: CourseModel): Fragment() {
    private lateinit var v: View
    var course : CourseModel = course
    private lateinit var courseEnrollmentRequestsRecyclerView : RecyclerView
    private lateinit var courseEnrollmentRequestsRecyclerViewAdapter: CourseEnrollmentRequestsAdapter
    private lateinit var userInfo: SharedPreferences
    private lateinit var userId: String
    private lateinit var userRole: String
    private val viewModel: CourseEnrollmentRequestsViewModel by viewModels()
    private var enrollmentList: MutableList<EnrollmentModel?> = mutableListOf()

    companion object {
        fun newInstance(course: CourseModel) = CourseBasicInfo(course)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_course_enrollment_requests, container, false)
        courseEnrollmentRequestsRecyclerView = v.findViewById(R.id.course_enrollment_requests_recycler_view)

        return v
    }

    override fun onStart() {
        super.onStart()
        val context = requireContext()

        val activity = (activity as MainActivity)

        if (context != null) {
            userInfo = context.getSharedPreferences("UserInformation", Context.MODE_PRIVATE)
            userId = userInfo.getString("UUID", "")!!
            userRole = userInfo.getString("ROLE", "")!!
        }

        viewModel.getCourseEnrollmentRequests(course)
        courseEnrollmentRequestsRecyclerView.setHasFixedSize(true)
        courseEnrollmentRequestsRecyclerView.layoutManager = LinearLayoutManager(context)
        courseEnrollmentRequestsRecyclerViewAdapter = CourseEnrollmentRequestsAdapter(
            context,
            enrollmentList,
            onAcceptCallback= {userId: String, course: CourseModel ->
                viewModel.enrollUserToCourse(userId, course)
            },
            onRejectCallback= {userId: String, course: CourseModel ->
                viewModel.rejectEnrollmentRequest(userId, course)
            }
        )
        courseEnrollmentRequestsRecyclerView.adapter = courseEnrollmentRequestsRecyclerViewAdapter
            viewModel.enrollmentList.observe(viewLifecycleOwner, Observer { result ->
                if (result != null) {
                    enrollmentList.clear()
                    enrollmentList.addAll(result)
                    courseEnrollmentRequestsRecyclerViewAdapter.notifyDataSetChanged();
                }
            })
        }
}