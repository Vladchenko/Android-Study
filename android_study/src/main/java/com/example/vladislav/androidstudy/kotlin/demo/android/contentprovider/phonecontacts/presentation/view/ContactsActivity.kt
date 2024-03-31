package com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View.INVISIBLE
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vladislav.androidstudy.MyApplication
import com.example.vladislav.androidstudy.R
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.ContactsPermissionDelegate
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.ContactsPermissionDelegate.Companion.PERMISSIONS_REQUEST_READ_CONTACTS
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.ContactsRecyclerViewAdapter
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.PermissionStatus
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.model.ContactInfoModel
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.viewModel.ContactsViewModel
import com.example.vladislav.androidstudy.kotlin.demo.android.contentprovider.phonecontacts.presentation.viewModel.ContactsViewModelFactory
import java.lang.ref.WeakReference
import javax.inject.Inject

/** Activity to display a cell-phone contacts. */
class ContactsActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var recyclerViewAdapter: ContactsRecyclerViewAdapter

    @Inject
    lateinit var contactsViewModelFactory: ContactsViewModelFactory

    private val contactsViewModel by viewModels<ContactsViewModel> {
        contactsViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contacts_activity)

        (application as MyApplication).contactsComponent.inject(this)

        checkPhoneContactsReadPermission()
        checkPhoneCallPermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            contactsViewModel.downloadContacts(CONTACTS_URI.toString())
        } else {
            Log.e(TAG, getString(R.string.contacts_permission_was_not_granted))
        }
    }

    private fun checkPhoneCallPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CALL_PHONE),
                MAKE_PHONE_CALL_REQUEST_CODE
            )
        } else {
            // User has already granted permission.
            Log.i(TAG, getString(R.string.contacts_permission_already_granted))
        }
    }

    private fun checkPhoneContactsReadPermission() {
        if (ContactsPermissionDelegate().requestContactsReadingPermission(WeakReference(this))
            == PermissionStatus.GrantedBefore
        ) {
            recyclerViewAdapter =
                ContactsRecyclerViewAdapter(
                    { phoneNumber: String -> contactsViewModel.makeCall(phoneNumber) }
                )

            initRecyclerView()
            progressBar = findViewById(R.id.progress_bar)

            contactsViewModel.downloadContacts(CONTACTS_URI.toString())
            contactsViewModel.contactsListLiveData.observe(this, ::showContacts)
            contactsViewModel.phoneCallLiveData.observe(this, ::makePhoneCall)
        }
    }

    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.contacts_recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerViewAdapter
    }

    private fun showContacts(contacts: List<ContactInfoModel>) {
        progressBar.visibility = INVISIBLE
        recyclerViewAdapter.updateContacts(contacts)
    }

    private fun makePhoneCall(phoneNumber: String) {
        Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
            .also { startActivity(it) }
    }

    companion object {
        private const val TAG = "ContactsActivity"
        private const val MAKE_PHONE_CALL_REQUEST_CODE = 4321
        private val CONTACTS_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
    }
}
