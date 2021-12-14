package codes.routour.rotodo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import codes.routour.rotodo.R
import codes.routour.rotodo.data.local.Database
import codes.routour.rotodo.databinding.MainFragmentBinding
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

import android.R.string.no




class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel> {
        MainViewModelFactory(Database.getDb(requireContext().applicationContext))
    }
    private lateinit var todoClickListener: TodoClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
//        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        Database.getDb(requireContext()).toDoDao()

        setupTodoListAdapter(binding.todoListRecyclerView)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addTodoFab.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addToDoFragment)
        }
    }

    private fun setupTodoListAdapter(recyclerView: RecyclerView) {
        val todoClickListener = TodoClickListener {todo, _ ->
            Snackbar.make(
                binding.root,
                "Clicked on '${todo.text}' (id: ${todo.id})",
                Snackbar.LENGTH_LONG
            ).show()
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