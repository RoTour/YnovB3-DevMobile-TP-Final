package codes.routour.rotodo.ui.add

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import codes.routour.rotodo.R
import codes.routour.rotodo.data.local.Database
import codes.routour.rotodo.databinding.AddToDoFragmentBinding
import codes.routour.rotodo.ui.main.MainViewModelFactory
import com.google.android.material.snackbar.Snackbar

class AddToDoFragment : Fragment() {
    private var _binding: AddToDoFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AddToDoViewModel> {
        AddToDoViewModelFactory(Database.getDb(requireContext().applicationContext))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.add_to_do_fragment, container, false)

        binding.todoEditText.addTextChangedListener {
            viewModel.todoTxt = it?.toString() ?: ""
        }

        binding.createTodoBtn.setOnClickListener {
            viewModel.createTodo(binding.root)
        }

        viewModel.eventTodoInserted.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_addToDoFragment_to_mainFragment)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}