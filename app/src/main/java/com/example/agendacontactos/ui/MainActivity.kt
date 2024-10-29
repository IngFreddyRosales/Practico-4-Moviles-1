package com.example.agendacontactos.ui

import ContactAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agendacontactos.R
import com.example.agendacontactos.databinding.ActivityMainBinding
import com.example.agendacontactos.viewmodel.ContactViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel = ContactViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        binding.btnContactManage.setOnClickListener {
//            switchtoContactManagement()
//        }

        setupRecyclerView()
        setupViewModelOberservers()
        viewModel.loadContacts()
    }

    private fun setupViewModelOberservers() {
        viewModel.contacts.observe(this){
            val adapter = binding.rvContactListMain.adapter as ContactAdapter
            adapter.updateData(it)
        }
    }

    private fun setupRecyclerView(){
        binding.rvContactListMain.apply {
            adapter = ContactAdapter(
                listOf()
            )
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

//    private fun switchtoContactManagement(){
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.btnContactManage, ContactManagement())
//            .commit()
//    }
}