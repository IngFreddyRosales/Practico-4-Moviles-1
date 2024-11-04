package com.example.agendacontactos.ui

import ContactAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agendacontactos.R
import com.example.agendacontactos.databinding.FragmentContactListBinding
import com.example.agendacontactos.model.Contact
import com.example.agendacontactos.viewmodel.ContactViewModel

class ContactListFragment : Fragment(), ContactAdapter.OnContactClickListener {

    private var _binding: FragmentContactListBinding? = null
    private val binding get() = _binding!!

    private val viewModel = ContactViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main){ v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()
        setupViewModelObservers()
        viewModel.loadContacts()

        binding.btnContactManage.setOnClickListener {
            if(savedInstanceState == null){
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ContactManagement())
                    .commit()
            }
        }

    }

    private fun setupRecyclerView() {
        binding.rvContactListMain.apply {
            adapter = ContactAdapter(listOf(), this@ContactListFragment)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupViewModelObservers() {
        viewModel.contacts.observe(viewLifecycleOwner) {
            val adapter = binding.rvContactListMain.adapter as ContactAdapter
            if (it != null) {
                adapter.updateData(it)
            }
        }
    }

    override fun onContactClick(contact: Contact) {
        println("Contact clicked: $contact")
        val intent = ContactManagement.newInstance(contact)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, intent)
            .addToBackStack(null)
            .commit()

    }


}
