package me.oleg.tagliber.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import me.oleg.tagliber.databinding.FragmentNoteDetailBinding
import me.oleg.tagliber.utitlies.InjectorUtils
import me.oleg.tagliber.viewmodels.NoteViewModel

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

        val noteId = arguments?.let { NoteDetailFragmentArgs.fromBundle(
            it
        ).noteId }
        val factory = InjectorUtils.provideNoteRepository(activity!!.application)

        val viewModel = ViewModelProviders.of(this, factory)
            .get(NoteViewModel::class.java)

        val liveData = noteId?.let { viewModel.getNoteById(it) }
        liveData?.observe(this, Observer { note ->
            if (note != null) binding.note = note
        })

        return binding.root
    }


}
