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
import com.example.loginapp.adapter.UsersAdapter
import com.example.loginapp.database.AppDatabase
import com.example.loginapp.repository.UserRepository

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class UsersList : Fragment() {
    private lateinit var v : View
    private lateinit var userListRecyclerView : RecyclerView
    private lateinit var userRepository: UserRepository
    private lateinit var name: String
    private lateinit var addUserButton: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_users_list, container, false)
        userListRecyclerView = v.findViewById(R.id.UserListRecyclerView)
        addUserButton = v.findViewById(R.id.addUserButton)

        return v
    }

    override fun onStart() {
        super.onStart()
        val context = requireContext()
        val sharedPref: SharedPreferences = context.getSharedPreferences("UserInformation", Context.MODE_PRIVATE)

        val activity = (activity as MainActivity)
        if (activity != null) {
            activity.setTitleText("Users List")
        }

        if (context != null) {
            val db = AppDatabase.getAppDataBase(context)
            if (db != null) {
                userRepository = UserRepository(db.userDao())
            }
        }


        name = sharedPref.getString("UserName","default")!!
        userListRecyclerView.setHasFixedSize(true)
        userListRecyclerView.layoutManager  = LinearLayoutManager(context)
        userListRecyclerView.adapter = UsersAdapter(context, userRepository.getUsers()) { userData ->
            userData?.let {
                var detailPageAction = UsersListDirections.actionUsersListToUserDetailsContainer(userData)
                v.findNavController().navigate(detailPageAction)
            }
        }
        addUserButton.setOnClickListener {
            var addUserAction = UsersListDirections.actionUsersListToAddUserPage()
            v.findNavController().navigate(addUserAction)
        }

    }
}