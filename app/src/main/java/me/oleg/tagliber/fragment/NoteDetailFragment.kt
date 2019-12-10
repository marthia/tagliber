package me.oleg.tagliber.fragment


import android.os.Bundle
import android.view.*
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNoteDetailBinding.inflate(
            inflater,
            container,
            false
        )

        setHasOptionsMenu(true)

        val noteId = arguments?.let {
            NoteDetailFragmentArgs.fromBundle(
                it
            ).noteId
        }
        val factory = InjectorUtils.provideNoteDetailRepository(activity!!.application)

        val viewModel = ViewModelProviders.of(this, factory)
            .get(NoteDetailViewModel::class.java)

        val liveData = noteId?.let { viewModel.getNoteById(it) }
        liveData?.observe(this, Observer { note ->
            note?.let {
                binding.note = note
            }
        })

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
            else -> false
        }
    }


}
