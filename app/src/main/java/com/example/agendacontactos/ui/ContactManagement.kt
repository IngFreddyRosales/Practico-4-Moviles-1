package com.example.agendacontactos.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.agendacontactos.R
import com.example.agendacontactos.adapter.EmailAdapter
import com.example.agendacontactos.adapter.PhoneAdapter
import com.example.agendacontactos.databinding.FragmentContactManagementBinding
import com.example.agendacontactos.model.Contact
import com.example.agendacontactos.model.Email
import com.example.agendacontactos.model.PhoneNumber
import com.example.agendacontactos.viewmodel.ContactViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class ContactManagement : Fragment() {
    private var _binding: FragmentContactManagementBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ContactViewModel

    private var contactId: Int = -1
    private var selectedImageFile: File? = null

    private var fileChooserResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val bitmap = getBitmapFromIntent(data)
                if (bitmap != null){
                    binding.profileImageLayout.profileImageView.setImageBitmap(bitmap)
                    println(bitmap.toString() + " bitmap")
                    selectedImageFile = bitmapToFile(requireContext(), bitmap)

                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contactId = it.getInt(CONTACT_ID)
        }
        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactManagementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactId = arguments?.getInt(CONTACT_ID) ?: -1
        println(contactId.toString() + " contactId")

        if (contactId != -1) {
            context?.let { ctx ->
                println(contactId.toString() + " contactId2")
                viewModel.loadContactById(ctx, contactId)
            }
        }

        viewModel.contact.observe(viewLifecycleOwner) { contact ->
            contact?.let {
                updateUI(contact)

            }
        }
        setupPhoneRecyclerView()
        setupEmailRecyclerView()

        binding.profileImageLayout.addPhotoButton.setOnClickListener { selectPicture() }
        binding.btnSaveContact.setOnClickListener { setupSaveContact() }

    }


    private fun updateUI(contact: Contact) {

        Glide.with(this)
            .load(contact.profile_picture)
            .placeholder(R.drawable.baseline_person_24)
            .error(R.drawable.baseline_person_24)
            .into(binding.profileImageLayout.profileImageView)


        binding.dataLayout.firstNameEditText.setText(contact.name)
        binding.dataLayout.lastNameEditText.setText(contact.last_name)
        binding.dataLayout.companyEditText.setText(contact.company)
        binding.dataLayout.addressEditText.setText(contact.address)
        binding.dataLayout.cityEditText.setText(contact.city)
        binding.dataLayout.stateEditText.setText(contact.state)

    }

    private fun setupPhoneRecyclerView() {
        val phoneAdapter =
            PhoneAdapter(listOf())

        binding.phoneLayout.rvTelefonos.apply {
            adapter = phoneAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.contact.observe(viewLifecycleOwner) { contact ->
            contact?.let {
                phoneAdapter.updateData(it.phones)
            }
        }

    }

    private fun setupEmailRecyclerView() {

        val emailAdapter = EmailAdapter(listOf())

        binding.emailLayout.rvEmails.apply {
            adapter = emailAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.contact.observe(viewLifecycleOwner) { contact ->
            contact?.let {
                emailAdapter.updateData(it.emails)
            }

        }

    }

    private fun setupSaveContact() {
        val name = binding.dataLayout.firstNameEditText.text.toString()
        val lastName = binding.dataLayout.lastNameEditText.text.toString()
        val company = binding.dataLayout.companyEditText.text.toString()
        val address = binding.dataLayout.addressEditText.text.toString()
        val city = binding.dataLayout.cityEditText.text.toString()
        val state = binding.dataLayout.stateEditText.text.toString()

        val phoneList = getPhoneNumberFromRv()
        val emailList = getEmailFromRv()

        val contact = Contact(
            id = 0,
            name = name,
            last_name = lastName,
            company = company,
            address = address,
            city = city,
            state = state,
            profile_picture =  "",
            phones = phoneList,
            emails = emailList
        )

        viewModel.saveContact(contact)


    }

    private fun getBitmapFromIntent(data: Intent?): Bitmap? {
        val imgUrl = data?.data
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context?.contentResolver?.query(imgUrl!!, filePathColumn, null, null, null)

        cursor!!.moveToFirst()

        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
        val picturePath = cursor.getString(columnIndex)
        cursor.close()

        val bitmap = BitmapFactory.decodeFile(picturePath)
        return bitmap
    }

    private fun bitmapToFile(context: Context, bitmap: Bitmap): File? {
        val file = File(context.cacheDir, "profile_picture")
        return try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            file
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }


    private fun selectPicture() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        fileChooserResultLauncher.launch(intent)

    }

    private fun getEmailFromRv(): List<Email> {
        return emptyList()
    }

    private fun getPhoneNumberFromRv(): List<PhoneNumber> {
        return emptyList()
    }

    companion object {
        private const val CONTACT_ID: String = "contact_id"

        fun newInstance(contact: Contact): ContactManagement {
            return ContactManagement().apply {
                arguments = Bundle().apply {
                    putInt(CONTACT_ID, contact.id)
                }
            }

        }
    }



}