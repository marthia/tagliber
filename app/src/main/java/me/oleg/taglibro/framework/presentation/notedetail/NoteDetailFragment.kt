package me.oleg.taglibro.framework.presentation.notedetail


import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.oleg.taglibro.R
import me.oleg.taglibro.databinding.FragmentNoteDetailBinding
import me.oleg.taglibro.utitlies.DelayedTextWatcher


/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class NoteDetailFragment : Fragment() {

    private var noteId: Long = -1
    private lateinit var binding: FragmentNoteDetailBinding
    private var isTextChanged = false

    private val viewModel: NoteDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteDetailBinding.inflate(
            inflater,
            container,
            false
        )
//        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
//        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        setHasOptionsMenu(true)

        setTextWatchers()

        noteId = (arguments?.get("noteId") as String).toLong()

        val liveData = noteId.let { viewModel.getNoteById(it) }

        liveData.observe(viewLifecycleOwner, { note ->
            note?.let {
                binding.note = note
            }
        })

        viewModel.changedId.observe(viewLifecycleOwner, {
            if (it != -1L) noteId = it
        })

        return binding.root
    }

    private fun setTextWatchers() {

        binding.content.addTextChangedListener(DelayedTextWatcher(500) {
            var title = ""

            if (!TextUtils.isEmpty(binding.title.text))
                title = binding.title.text.toString()
            viewModel.save(noteId, title, it)
        })

        binding.title.addTextChangedListener(DelayedTextWatcher(500) {
            var content = ""

            if (!TextUtils.isEmpty(binding.content.text))
                content = binding.content.text.toString()

            viewModel.save(noteId, it, content)
        })
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
                    binding.title.text.toString(),
                    binding.content.text.toString()
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
