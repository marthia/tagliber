package me.oleg.taglibro.ui.detail


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import me.oleg.taglibro.R
import me.oleg.taglibro.base.BaseFragment
import me.oleg.taglibro.databinding.FragmentNoteDetailBinding
import me.oleg.taglibro.viewmodels.NoteDetailViewModel
import me.oleg.taglibro.viewmodels.ViewModelFactory
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class NoteDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentNoteDetailBinding

    val args: NoteDetailFragmentArgs by navArgs()


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: NoteDetailViewModel

    private var isTextChanged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This callback will only be called when MyFragment is at least Started.
        // This callback will only be called when MyFragment is at least Started.
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() { // Handle the back button event
                    if (isTextChanged)
                        showConfirmationDialog()
                    else findNavController().navigateUp()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun layoutRes(): Int = R.layout.fragment_note_detail


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setHasOptionsMenu(true)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(
            NoteDetailViewModel::class.java
        )

        viewModel.note.observe(this, Observer {note ->
            runBlocking {
                delay(2000)
                setTextWatchers() }
        })


    }

    private fun setTextWatchers() {

        // add text watchers for exit dialog
        binding.content.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isTextChanged = true
            }
        })

        binding.title.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isTextChanged = true
            }
        })
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(activity!!)

        // Set the alert dialog title
        builder.setTitle("Confirm")

        // Display a message on alert dialog
        builder.setMessage("Exit without saving?")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("YES") { dialog, which ->
            findNavController().navigateUp()
        }
        builder.setNeutralButton("SAVE") { dialog, which ->
            viewModel.save(
                args.noteId,
                binding.title?.text.toString(),
                binding.content?.text.toString()
            ).also {
                findNavController().navigateUp()
            }
        }

        builder.setNegativeButton("NO") { dialog, which ->
            // do nothing
        }


        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_detail_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.menu_info -> {
                true
            }

            R.id.menu_save -> {

                viewModel.save(
                    args.noteId,
                    binding.title?.text.toString(),
                    binding.content?.text.toString()
                )
                Toast.makeText(
                    context,
                    "successfully saved",
                    Toast.LENGTH_SHORT
                )
                    .show()

                isTextChanged = false
                true
            }
            else -> false
        }
    }


}
