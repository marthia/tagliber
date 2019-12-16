package me.oleg.tagliber.fragment


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import me.oleg.tagliber.R
import me.oleg.tagliber.databinding.FragmentNoteDetailBinding
import me.oleg.tagliber.utitlies.InjectorUtils
import me.oleg.tagliber.viewmodels.NoteDetailViewModel


/**
 * A simple [Fragment] subclass.
 */
class NoteDetailFragment : Fragment() {

    private var noteId: Int = -1
    private lateinit var binding: FragmentNoteDetailBinding
    private lateinit var viewModel: NoteDetailViewModel

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

//        findNavController().addOnDestinationChangedListener { _, destination, _ ->
//            if (destination.id == R.id.note_detail_fragment || destination.id == R.id.search_list_fragment) {
//                b.toolbar.visibility = View.GONE
//            } else b.toolbar.visibility = View.VISIBLE
//            }
//        }

        noteId = arguments.let {
            NoteDetailFragmentArgs.fromBundle(
                it!!
            ).noteId
        }
        val factory = InjectorUtils.provideNoteDetailRepository(activity!!.application)

        viewModel = ViewModelProviders.of(this, factory)
            .get(NoteDetailViewModel::class.java)

        if (noteId != -1) {
            val liveData = noteId.let { viewModel.getNoteById(it) }
            liveData.observe(this, Observer { note ->
                note?.let {
                    binding.note = note
                }
            })

        }
        /* binding.title.addTextChangedListener(object : TextWatcher {
             override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                 // TODO Auto-generated method stub
             }

             override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                 // TODO Auto-generated method stub
             }

             override fun afterTextChanged(s: Editable) {
                 viewModel.insertOrUpdate(note)
             }
         })*/


        return binding.root
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
                if (noteId == -1) {
                    viewModel.saveNewNote(
                        binding.title.text.toString(),
                        binding.content.text.toString()
                    )
                } else {
                    binding.note?.let {
                        viewModel.saveChanges(
                            it.noteId,
                            binding.title.text.toString(),
                            binding.content.text.toString()
                        )
                    }
                }
                Toast.makeText(
                    binding.root.context,
                    "successfully saved",
                    Toast.LENGTH_SHORT
                )
                    .show()
                true
            }
            else -> false
        }
    }


}
