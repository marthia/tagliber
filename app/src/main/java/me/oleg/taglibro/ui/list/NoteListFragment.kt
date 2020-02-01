package me.oleg.taglibro.ui.list

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.Selection
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import me.oleg.taglibro.R
import me.oleg.taglibro.adapters.*
import me.oleg.taglibro.base.BaseFragment
import me.oleg.taglibro.data.model.Note
import me.oleg.taglibro.databinding.FragmentNoteListBinding
import me.oleg.taglibro.viewmodels.NoteViewModel
import me.oleg.taglibro.viewmodels.ViewModelFactory
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class NoteListFragment : BaseFragment(),
    OnActionModeDestroyCallback, OnActionItemClickListener {
    private lateinit var binding: FragmentNoteListBinding
    private lateinit var adapter: NoteListAdapter
    private lateinit var tracker: SelectionTracker<Long>
    private lateinit var viewModel: NoteViewModel
    private lateinit var actionModeCallback: PrimaryActionModeCallback
    private lateinit var queryTextListener: SearchView.OnQueryTextListener

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun layoutRes(): Int = R.layout.fragment_note_list


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NoteListAdapter()
        binding.listView.adapter = adapter


        binding.swipeRefreshLayout.setOnRefreshListener {
            Handler().postDelayed({

                // psudo fetch delay
                binding.swipeRefreshLayout.isRefreshing = false
            }, 2000)
        }

        subscribeUi(adapter)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        actionModeCallback = PrimaryActionModeCallback(this, this)

        initTracker()

        adapter.tracker = tracker
    }

    private var selectedItems: Selection<Long>? = null

    private fun initTracker() {

        tracker = SelectionTracker.Builder(

            "mySelection",
            binding.listView,
            CustomItemKeyProvider(binding.listView),
            ItemSelectionLookUp(binding.listView),
            StorageStrategy.createLongStorage()

        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        tracker.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onItemStateChanged(key: Long, selected: Boolean) {

                    selectedItems = tracker.selection
                    Log.i("tacker", selectedItems.toString())
                    if (selectedItems?.isEmpty == false) {

                        actionModeCallback.startActionMode(
                            binding.container,
                            R.menu.main_selection,
                            (selectedItems as Selection<Long>).size().toString()
                        )

                    } else actionModeCallback.finishActionMode()


                }
            }

        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.menu_search)?.actionView as SearchView).apply {

            setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))
            setIconifiedByDefault(true)
            queryHint = "Select Notes"

            queryTextListener = object : SearchView.OnQueryTextListener {

                override fun onQueryTextChange(newText: String): Boolean = false

                override fun onQueryTextSubmit(query: String): Boolean {

                    view?.findNavController()?.navigate(
                        NoteListFragmentDirections.actionSumbitSearchQuery(
                            query = query
                        )
                    )
                    Log.i("onQueryTextSubmit", query)

                    return true
                }
            }
            setOnQueryTextListener(queryTextListener)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.menu_add -> {
                findNavController().navigate(NoteListFragmentDirections.actionNewNoteEditor())
                true
            }
            else -> false
        }
    }

    private fun subscribeUi(adapter: NoteListAdapter) {

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NoteViewModel::class.java)

        viewModel.noteList.observe(this, Observer { notes ->
            if (notes != null && notes.isNotEmpty())
                binding.listView.invalidate()
            adapter.submitList(notes)
        })
    }

    override fun onActionItemClick(item: MenuItem?) {
        when (item?.itemId) {

            R.id.menu_delete_item -> {
                val pendingDeleteItems = ArrayList<Note>()
                for (key in selectedItems!!) {

                    val holder = binding.listView.findViewHolderForItemId(key)
                    val note = holder?.itemView?.tag as Note?

                    note?.let { pendingDeleteItems.add(it) }

                }

                viewModel.deleteData(pendingDeleteItems)
                binding.listView.invalidate()
                binding.listView.layoutManager?.removeAllViews()

                actionModeCallback.finishActionMode()
            }

            R.id.menu_select_all -> {
                val size = adapter.currentList?.size
                if (size != null) {
                    for (index in 0 until size) {
                        if (!tracker.isSelected(adapter.getItemId(index)))
                            tracker.select(adapter.getItemId(index))
                    }
                }

            }
        }
    }

    override fun onActionModeDestroy() {
        tracker.clearSelection()

        Log.i(tag, "onActionModeDestroy")
    }
}
