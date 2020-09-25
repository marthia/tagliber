package me.oleg.taglibro.framework.presentation.notesearch


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.oleg.taglibro.databinding.FragmentSearchBinding
import me.oleg.taglibro.framework.presentation.NoteViewModel

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchListAdapter

    private val viewModel: NoteViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter = SearchListAdapter()
        binding.listView.adapter = adapter

        val query = arguments?.let { SearchFragmentArgs.fromBundle(it).query }

        query?.let {
            activity?.title = it
            doMySearch(it)

        }
    }

    private fun doMySearch(query: String) {
        viewModel.get(query).observe(viewLifecycleOwner, { list ->
            if (list != null) {
                adapter.setQuery(query = query)
                adapter.submitList(list)
            }
        })

    }


}
