package com.example.loginapp.fragments.dataLists

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.R
import com.example.loginapp.activities.MainActivity
import com.example.loginapp.adapter.CourseAdapter
import com.example.loginapp.models.CourseModel
import com.example.loginapp.repository.CourseRepository
import com.example.loginapp.viewmodels.dataLists.CoursesListViewModel

class CoursesList : Fragment() {
    private lateinit var v : View
    private val viewModel: CoursesListViewModel by viewModels()
    private var coursesList: MutableList<CourseModel?> = mutableListOf()
    private lateinit var courseListRecyclerView : RecyclerView
    private lateinit var courseListRecyclerViewAdapter: CourseAdapter
    private lateinit var addCourseButton: View
    private lateinit var userInfo: SharedPreferences
    private lateinit var userRole: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_courses_list, container, false)
        courseListRecyclerView = v.findViewById(R.id.coursesListRecyclerView)
        addCourseButton = v.findViewById(R.id.addCourseButton)

        return v
    }

    override fun onStart() {
        super.onStart()
        val context = requireContext()
        val sharedPref: SharedPreferences = context.getSharedPreferences("UserInformation", Context.MODE_PRIVATE)

        val activity = (activity as MainActivity)
        if (activity != null) {
            activity.setTitleText("Courses List")
        }
        if(context != null){
            userInfo = context.getSharedPreferences("UserInformation", Context.MODE_PRIVATE)
            userRole = userInfo.getString("ROLE","")!!
        }

        courseListRecyclerView.setHasFixedSize(true)
        courseListRecyclerView.layoutManager  = LinearLayoutManager(context)
        courseListRecyclerViewAdapter = CourseAdapter(context, coursesList) { courseData ->
            courseData?.let {
//                var detailPageAction = MainMenuDirections.actionFragmentMainMenuToAddCoursePage(courseData)
//                v.findNavController().navigate(detailPageAction)
            }
        }
        if(userRole != "Teacher"){
            addCourseButton.isEnabled = false
            addCourseButton.isVisible = false
            courseListRecyclerViewAdapter.isDeleteButtonDisabled = true
        }
        courseListRecyclerView.adapter = courseListRecyclerViewAdapter

        addCourseButton.setOnClickListener {
            var addCourseAction = CoursesListDirections.actionCoursesListToAddCoursePage()
            v.findNavController().navigate(addCourseAction)
        }

        viewModel.coursesList.observe(viewLifecycleOwner, Observer { result ->
            coursesList.clear()
            coursesList.addAll(result)
            courseListRecyclerViewAdapter.notifyDataSetChanged();
        })
        viewModel.getCourses()

    }

}