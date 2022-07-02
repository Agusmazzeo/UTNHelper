package com.example.loginapp.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginapp.R
import com.example.loginapp.database.AppDatabase
import com.example.loginapp.entities.Course
import com.example.loginapp.models.CourseModel
import com.example.loginapp.repository.CourseRepository
import com.example.loginapp.utils.StorageHandler
import com.example.loginapp.viewmodels.adapter.CourseAdapterViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CourseAdapter(
var context: Context?,
var courseList : MutableList <CourseModel?>,
var onClick : (CourseModel) -> Unit
) : RecyclerView.Adapter<CourseAdapter.CourseHolder>() {

    private var courseRepository = CourseRepository()
    private var storageHandler = StorageHandler()

    class CourseHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View

        init {
            this.view = v
        }

        fun setTitle(title: String) {
            var txt_title: MaterialTextView = view.findViewById(R.id.course_item_text)
            txt_title.text = title
        }

        fun setCourseImage(image_uri: String){
            var image_view: ImageView = view.findViewById(R.id.course_item_image)
            Glide.with(view).load(Uri.parse(image_uri)).into(image_view)
        }

        fun getCardView(): CardView {
            return view.findViewById(R.id.course_item)
        }

        fun getDeleteButtonView(): ImageButton {
            return view.findViewById(R.id.delete_course_button)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_item, parent, false)
        return (CourseHolder(view))
    }

    override fun onBindViewHolder(holder: CourseHolder, position: Int) {
        if(courseList != null && courseList.size != 0){
            var course: CourseModel? = courseList[position]
            course?.let {
                holder.setTitle(it.name)
                holder.setCourseImage(it.icon)
            }
            holder.getCardView().setOnClickListener {
                course?.let {
                    onClick(course)
                }
            }
            holder.getDeleteButtonView().setOnClickListener {
                if (context != null) {
                    MaterialAlertDialogBuilder(context!!)
                        .setTitle("Delete Course?")
                        .setNegativeButton("Cancel") { _, _ ->
                            // Respond to negative button press
                        }.setPositiveButton("Delete") { _, _ ->
                            deleteAction(course)
                            courseList.removeAt(position)
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position,courseList.size)
                        }.show()
                }

            }
        }

    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    private fun deleteAction(course: CourseModel?){
        CoroutineScope(Dispatchers.IO).launch {
            var deleteImageResult = async { course?.let { storageHandler.deleteCourseImage(it.icon) } }
            var deleteResult = async { course?.let { it1 ->
                courseRepository.deleteCourseByID(
                    it1.id)
            } }
            deleteImageResult.await()
            deleteResult.await()
        }
    }
}