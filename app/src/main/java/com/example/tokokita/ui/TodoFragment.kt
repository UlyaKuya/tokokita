package com.example.tokokita.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tokokita.adapter.TodoAdapter
import com.example.tokokita.databinding.FragmentTodoBinding
import com.example.tokokita.model.TodoEntity
import com.example.tokokita.viewmodel.TodoViewModel
import kotlinx.coroutines.launch

class TodoFragment : Fragment() {

    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TodoViewModel by viewModels()

    private lateinit var adapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTodoBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeTodos()

        binding.btnTambahTodo.setOnClickListener {
            showTambahDialog()
        }
    }

    private fun setupRecyclerView() {

        adapter = TodoAdapter(

            onSelesaiChange = { todo ->
                viewModel.updateTodo(todo)
            },

            onEditClick = { todo ->
                showEditDialog(todo)
            },

            onHapusClick = { todo ->
                showDeleteDialog(todo)
            }
        )

        binding.recyclerViewTodo.layoutManager =
            LinearLayoutManager(requireContext())

        binding.recyclerViewTodo.adapter = adapter
    }

    private fun observeTodos() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(
                Lifecycle.State.STARTED
            ) {

                viewModel.todos.collect { todos ->
                    adapter.submitList(todos)
                }
            }
        }
    }

    private fun showTambahDialog() {

        val input = EditText(requireContext())

        input.hint = "Masukkan tugas"

        AlertDialog.Builder(requireContext())
            .setTitle("Tambah Tugas")
            .setView(input)
            .setNegativeButton("BATAL", null)
            .setPositiveButton("SIMPAN") { _, _ ->

                val judul = input.text.toString().trim()

                if (judul.isNotEmpty()) {
                    viewModel.tambahTodo(judul)
                }
            }
            .show()
    }

    private fun showEditDialog(todo: TodoEntity) {

        val input = EditText(requireContext())

        input.setText(todo.judul)
        input.setSelection(input.text.length)

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Tugas")
            .setView(input)
            .setNegativeButton("BATAL", null)
            .setPositiveButton("SIMPAN") { _, _ ->

                val judul = input.text.toString().trim()

                if (judul.isNotEmpty()) {

                    viewModel.updateTodo(
                        todo.copy(judul = judul)
                    )
                }
            }
            .show()
    }

    private fun showDeleteDialog(todo: TodoEntity) {

        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Tugas")
            .setMessage(
                "Hapus tugas \"${todo.judul}\"?"
            )
            .setNegativeButton("BATAL", null)
            .setPositiveButton("HAPUS") { _, _ ->

                viewModel.hapusTodo(todo)
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}