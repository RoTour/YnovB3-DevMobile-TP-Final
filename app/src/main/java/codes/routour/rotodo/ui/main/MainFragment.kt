package codes.routour.rotodo.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import codes.routour.rotodo.R
import codes.routour.rotodo.data.Repository
import codes.routour.rotodo.data.local.Database
import codes.routour.rotodo.databinding.MainFragmentBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore


class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel> {
        val ds = Database.getDb(requireContext().applicationContext)
        MainViewModelFactory(
            ds,
            Repository(ds, FirebaseFirestore.getInstance())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        Database.getDb(requireContext()).toDoDao()

        setupTodoListAdapter(binding.todoListRecyclerView)
        viewModel.todos.observe(this) {
            if (it.isEmpty()) {
                binding.noTodoText.visibility = View.VISIBLE
            } else {
                binding.noTodoText.visibility = View.GONE
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addTodoFab.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addToDoFragment)
        }
    }

    private fun setupTodoListAdapter(recyclerView: RecyclerView) {
        val todoClickListener = TodoClickListener { todo, viewHolder, adapter ->
            Snackbar.make(
                binding.root,
                "Clicked on '${todo.text}' (status: ${!todo.completed})",
                Snackbar.LENGTH_LONG
            ).show()
            viewModel.todos.value
                ?.get(viewHolder.absoluteAdapterPosition)
                ?.completed = viewModel.toggleCompleted(todo)
            adapter.notifyItemChanged(viewHolder.layoutPosition)
        }

        val adapter = ToDoListAdapter(todoClickListener)

        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val todo = adapter.getItemAt(viewHolder.bindingAdapterPosition)
                todo?.let {
                    viewModel.deleteTodo(it)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}