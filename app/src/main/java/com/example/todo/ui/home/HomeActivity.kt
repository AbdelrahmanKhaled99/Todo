package com.example.todo.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.example.todo.database.MyDatebase
import com.example.todo.databinding.ActivityHomeBinding
import com.example.todo.fargments.add_todo.AddTodoFragment
import com.example.todo.fargments.list.ListFragment
import com.example.todo.fargments.settings.SettingsFragment

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    private var lisfFragment = ListFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showFragment(lisfFragment)
        intiListeners()

    }

    private fun intiListeners() {
        binding.fabAddTask.setOnClickListener {
            val addFragment = AddTodoFragment{
                lisfFragment.refreshTodoList()
            }
            addFragment.show(supportFragmentManager , "")
        }
        binding.bottomBar.setOnItemSelectedListener {item ->
            if (item.itemId == R.id.tasks_men_item){
                showFragment(lisfFragment)
            }else{
                showFragment(SettingsFragment())
            }
            return@setOnItemSelectedListener true
        }
    }
    fun showFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container
         , fragment , "").commit()
    }
}