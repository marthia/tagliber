package me.oleg.taglibro.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import me.oleg.taglibro.adapters.SearchListAdapter
import me.oleg.taglibro.data.Note
import me.oleg.taglibro.databinding.FragmentSearchBinding
import me.oleg.taglibro.utitlies.InjectorUtils
import me.oleg.taglibro.viewmodels.NoteViewModel

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {
    private lateinit var viewModel: NoteViewModel
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchListAdapter


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

        subscribeUi(adapter)

        val query = arguments?.let { SearchFragmentArgs.fromBundle(it).query }

        query?.let {
            activity?.title = it
            doMySearch(it)

        }
    }

    private fun subscribeUi(adapter: SearchListAdapter) {

        val factory = InjectorUtils.provideNoteRepository(requireActivity().application)

        viewModel = ViewModelProvider(this, factory)
            .get(NoteViewModel::class.java)
    }

    private fun doMySearch(query: String) {
        viewModel.find(query).observe(viewLifecycleOwner, { list ->
            if (list != null) {

                //  transformListContent(query, list)
                adapter.submitList(list)
            }
        })

    }

    private fun transformListContent(query: String, list: List<Note>) {

//        list.forEach {
//            var indexOfQuery = it.content.indexOf(query, 0);
//            val wordToSpan: Spannable = SpannableString(it.content)
//
//            var ofs = 0
//            while (ofs < it.content.length && indexOfQuery != -1) {
//
//
//                indexOfQuery = it.content.indexOf(query, ofs)
//                if (indexOfQuery == -1)
//                    break
//                else {
//
//                    wordToSpan.setSpan(
//                        BackgroundColorSpan(Color.YELLOW),
//                        indexOfQuery,
//                        indexOfQuery + query.length,
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                    )
//                    binding.content.setText(wordToSpan, TextView.BufferType.SPANNABLE)
//                }
//
//                ofs = indexOfQuery + 1
//            }
//
//            Log.i("onQueryTextSubmit", query)
//        }


    }


}
