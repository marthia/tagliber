package me.oleg.taglibro.ui.list


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import me.oleg.taglibro.R
import me.oleg.taglibro.adapters.SearchListAdapter
import me.oleg.taglibro.base.BaseFragment
import me.oleg.taglibro.data.model.Note
import me.oleg.taglibro.databinding.FragmentSearchBinding
import me.oleg.taglibro.viewmodels.SearchViewModel
import me.oleg.taglibro.viewmodels.ViewModelFactory
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : BaseFragment() {
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchListAdapter

    @Inject
    lateinit var  viewModelFactory: ViewModelFactory

    override fun layoutRes(): Int = R.layout.fragment_search

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = SearchListAdapter()
        binding.listView.adapter = adapter

        subscribeUi(adapter)

        val query = arguments?.let { SearchFragmentArgs.fromBundle(
            it
        ).query }

        query?.let {
            activity?.title = it
            doMySearch(it)

        }
    }

    private fun subscribeUi(adapter: SearchListAdapter) {

        searchViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SearchViewModel::class.java)
    }

    private fun doMySearch(query: String) {
        searchViewModel.find(query).observe(this, Observer { list ->
            if (list != null) {

             //  transformListContent(query, list)
                adapter.submitList(list)
            }
        })

    }

    private fun transformListContent(query: String,  list: List<Note>) {

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
