package com.example.loginapp.fragments.dataLists

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.R
import com.example.loginapp.activities.MainActivity
import com.example.loginapp.adapter.CourseAdapter
import com.example.loginapp.database.AppDatabase
import com.example.loginapp.entities.Course
import com.example.loginapp.repository.CourseRepository

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class CoursesList : Fragment() {
    private lateinit var v : View
    private lateinit var coursesList: MutableList<Course?>
    private lateinit var courseListRecyclerView : RecyclerView
    private lateinit var courseRepository: CourseRepository
    private lateinit var name: String
    private lateinit var addCourseButton: View

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


        if (context != null) {
            val db = AppDatabase.getAppDataBase(context)
            if (db != null) {
                courseRepository = CourseRepository(db.coursesDao())
            }
        }


        name = sharedPref.getString("UserName","default")!!
        coursesList = courseRepository.getCourses()
        courseListRecyclerView.setHasFixedSize(true)
        courseListRecyclerView.layoutManager  = LinearLayoutManager(context)
        courseListRecyclerView.adapter = CourseAdapter(context, coursesList) { courseData ->
            courseData?.let {
//                var detailPageAction = MainMenuDirections.actionFragmentMainMenuToAddCoursePage(courseData)
//                v.findNavController().navigate(detailPageAction)
            }
        }

        addCourseButton.setOnClickListener {
            var addCourseAction = CoursesListDirections.actionCoursesListToAddCoursePage()
            v.findNavController().navigate(addCourseAction)
        }

    }

}