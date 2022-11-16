package com.example.inzynierka_app.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inzynierka_app.ErrorDialog
import com.example.inzynierka_app.R
import com.example.inzynierka_app.adapter.ErrorAdapter
import com.example.inzynierka_app.databinding.FragmentErrorBinding
import com.example.inzynierka_app.viewmodel.GripperViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ErrorFragment : Fragment() {

    private  var _binding: FragmentErrorBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GripperViewModel
    private lateinit var errorAdapter: ErrorAdapter

    private val errorDialog = ErrorDialog()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentErrorBinding.inflate(inflater, container, false)
        val view = binding.root

        setupMenu()

        viewModel = ViewModelProvider(requireActivity()).get(GripperViewModel::class.java)

//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
//        val current = LocalDateTime.now().format(formatter)

        setupRecyclerView()

        viewModel.errorsSortedByDate.observe(viewLifecycleOwner, Observer {
            errorAdapter.submitList(it)
        })
        return view
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.options_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                errorDialog.createDialog(
                    context,
                    R.string.delete_history,
                    R.string.delete_history_desc,
                    android.R.drawable.ic_menu_delete,
                    true)
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() = binding.rvRuns.apply {
        errorAdapter = ErrorAdapter()
        adapter = errorAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }
}