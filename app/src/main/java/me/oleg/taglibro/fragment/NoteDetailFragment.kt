package me.oleg.taglibro.fragment


import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import me.oleg.taglibro.R
import me.oleg.taglibro.databinding.FragmentNoteDetailBinding
import me.oleg.taglibro.utitlies.InjectorUtils
import me.oleg.taglibro.viewmodels.NoteDetailViewModel


/**
 * A simple [Fragment] subclass.
 */
class NoteDetailFragment : Fragment() {

    private var noteId: Int = -1
    private lateinit var binding: FragmentNoteDetailBinding
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteDetailBinding.inflate(
            inflater,
            container,
            false
        )

        setHasOptionsMenu(true)

        noteId = arguments.let {
            NoteDetailFragmentArgs.fromBundle(
                it!!
            ).noteId
        }
        val factory = InjectorUtils.provideNoteDetailRepository(activity!!.application)

        viewModel = ViewModelProvider(this, factory)
            .get(NoteDetailViewModel::class.java)

        val liveData = noteId.let { viewModel.getNoteById(it) }
        liveData.observe(this, Observer { note ->
            note?.let {
                binding.note = note

                // delay textWatcher attachment for the view to have time for a complete population
                Handler().postDelayed({ setTextWatchers() }, 2000)
            }
        })

        return binding.root
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
                noteId,
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
                    noteId,
                    binding.title?.text.toString(),
                    binding.content?.text.toString()
                )
                Toast.makeText(
                    binding.root.context,
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
