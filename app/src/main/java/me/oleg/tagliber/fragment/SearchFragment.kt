package me.oleg.tagliber.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import me.oleg.tagliber.adapters.SearchListAdapter
import me.oleg.tagliber.databinding.FragmentSearchBinding
import me.oleg.tagliber.utitlies.InjectorUtils
import me.oleg.tagliber.viewmodels.SearchViewModel

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {
    private lateinit var searchViewModel: SearchViewModel
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

        query?.let { doMySearch(it) }
    }

    private fun subscribeUi(adapter: SearchListAdapter) {

        val factory = InjectorUtils.provideSearchRepository(activity!!)

        searchViewModel = ViewModelProviders.of(this, factory)
            .get(SearchViewModel::class.java)
    }

    private fun doMySearch(query: String) {
        searchViewModel.find(query).observe(this, Observer { list ->
            if (list != null) adapter.submitList(list)
        })

    }


}
