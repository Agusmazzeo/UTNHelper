package com.example.loginapp.fragments.dataLists

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
import com.google.android.material.snackbar.Snackbar

class CoursesList : Fragment() {
    private lateinit var v : View
    private val viewModel: CoursesListViewModel by viewModels()
    private var coursesList: MutableList<CourseModel?> = mutableListOf()
    private lateinit var courseListRecyclerView : RecyclerView
    private lateinit var courseListRecyclerViewAdapter: CourseAdapter
    private lateinit var addCourseButton: View
    private lateinit var userInfo: SharedPreferences
    private lateinit var userId: String
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
            userId = userInfo.getString("UUID","")!!
            userRole = userInfo.getString("ROLE","")!!
        }

        courseListRecyclerView.setHasFixedSize(true)
        courseListRecyclerView.layoutManager  = LinearLayoutManager(context)
        courseListRecyclerViewAdapter = CourseAdapter(context, coursesList) { courseData ->
            courseData?.let {
                var detailPageAction = CoursesListDirections.actionCoursesListToCourseDetailsContainer(courseData)
                v.findNavController().navigate(detailPageAction)
            }
        }
        if(userRole != "Teacher"){
            courseListRecyclerViewAdapter.isDeleteButtonDisabled = true
        }
        courseListRecyclerView.adapter = courseListRecyclerViewAdapter

        addCourseButton.setOnClickListener {
            if(userRole == "Teacher"){
                var addCourseAction = CoursesListDirections.actionCoursesListToAddCoursePage()
                v.findNavController().navigate(addCourseAction)
            }
            else if(userRole == "Student"){
                courseJoinRequestDialog()
            }

        }

        viewModel.coursesList.observe(viewLifecycleOwner, Observer { result ->
            coursesList.clear()
            coursesList.addAll(result)
            courseListRecyclerViewAdapter.notifyDataSetChanged();
        })
        viewModel.getCourses(userId, userRole)

        viewModel.joinRequestCorrectlySent.observe(viewLifecycleOwner, Observer { result ->
            if(result == 0){
                Snackbar.make(v, "Error during Course request.", Snackbar.LENGTH_SHORT).show()
            }
            if(result == 1){
                Snackbar.make(v, "Successfully requested to join the Course.", Snackbar.LENGTH_SHORT).show()
            }
        })

    }

    private fun courseJoinRequestDialog(){
        val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("Request to join Course")

        val input = EditText(context)
        input.hint = "Course Code"
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("Send", DialogInterface.OnClickListener { dialog, which ->
            var courseCode = input.text.toString()
            viewModel.sendJoinRequestToCourse(userId, courseCode)
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

}