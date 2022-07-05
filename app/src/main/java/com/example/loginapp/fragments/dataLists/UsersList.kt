package com.example.loginapp.fragments.dataLists

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
import com.example.loginapp.adapter.UsersAdapter
import com.example.loginapp.models.UserModel
import com.example.loginapp.viewmodels.dataLists.UsersListViewModel

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class UsersList : Fragment() {
    private lateinit var v : View
    private val viewModel: UsersListViewModel by viewModels()
    private var usersList: MutableList<UserModel?> = mutableListOf()
    private lateinit var userListRecyclerView : RecyclerView
    private lateinit var userListReciclerViewAdapter: UsersAdapter
    private lateinit var userInfo: SharedPreferences
    private lateinit var userId: String
    private lateinit var userRole: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_users_list, container, false)

        userListRecyclerView = v.findViewById(R.id.UserListRecyclerView)

        return v
    }

    override fun onStart() {
        super.onStart()
        val context = requireContext()

        val activity = (activity as MainActivity)

        if(context != null){
            userInfo = context.getSharedPreferences("UserInformation", Context.MODE_PRIVATE)
            userId = userInfo.getString("UUID","")!!
            userRole = userInfo.getString("ROLE","")!!
        }


        userListRecyclerView.setHasFixedSize(true)
        userListRecyclerView.layoutManager  = LinearLayoutManager(context)
        userListReciclerViewAdapter= UsersAdapter(context, usersList) { userData ->
            userData?.let {
                var detailPageAction = UsersListDirections.actionUsersListToUserDetailsContainer(userData)
                v.findNavController().navigate(detailPageAction)
            }
        }

        userListRecyclerView.adapter = userListReciclerViewAdapter
        viewModel.usersList.observe(viewLifecycleOwner, Observer { result ->
            if(result != null){
                usersList.clear()
                usersList.addAll(result)
                userListReciclerViewAdapter.notifyDataSetChanged();
            }
        })

        viewModel.currentUser.observe(viewLifecycleOwner, Observer { result ->
            with (userInfo.edit()) {
                putString("ROLE", result.role)
                apply()
                if (activity != null) {
                    when(result.role){
                        "Student" -> activity.setTitleText("Teachers List")
                        "Teacher" -> activity.setTitleText("Students List")
                    }
                    viewModel.populateDataByUserRole(userId, result.role)
                }
            }
        })

        if(userRole.isEmpty()){
            viewModel.getCurrentUserById(userId)
        }
        else{
            if (activity != null) {
                when(userRole){
                    "Student" -> activity.setTitleText("Teachers List")
                    "Teacher" -> activity.setTitleText("Students List")
                }
            }
            viewModel.populateDataByUserRole(userId, userRole)
        }
    }
}