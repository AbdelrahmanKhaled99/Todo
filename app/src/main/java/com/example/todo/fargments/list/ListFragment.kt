package com.example.todo.fargments.list

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.Constants
import com.example.todo.R
import com.example.todo.database.MyDatebase
import com.example.todo.databinding.FragmentListBinding
import com.example.todo.timeInMillis
import com.example.todo.ui.edit_todo.EditTodoFragment
import com.example.todo.ui.home.adapters.TodoAdapter
import com.prolificinteractive.materialcalendarview.CalendarDay

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private var adapter = TodoAdapter(listOf())
    var selectedDate = CalendarDay.today()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.todoRecyclerView.adapter = adapter
        adapter.setOnTaskClickListener{task ->
            if (activity == null)return@setOnTaskClickListener
            val fragment = EditTodoFragment()
            val bundle = Bundle()
            bundle.putParcelable(Constants.PASSED_TASK , task)
            fragment.arguments =bundle
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container , fragment)
                .commit()
        }
        binding.calendarView.selectedDate = selectedDate
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            selectedDate = date
            refreshTodoList()
        }
        refreshTodoList()
    }



    @RequiresApi(Build.VERSION_CODES.O)
    public fun refreshTodoList() {
        val todosList = MyDatebase.getInstance(requireActivity()).todoDao().getTodoByDate(selectedDate.timeInMillis())
        adapter.updateTodos(todosList)
        if (todosList.isEmpty()) {
            binding.emptyView.visibility = View.VISIBLE
        } else {
            binding.emptyView.visibility = View.GONE
        }
    }
}
