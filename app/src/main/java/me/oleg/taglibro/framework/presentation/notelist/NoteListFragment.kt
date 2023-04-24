package me.oleg.taglibro.framework.presentation.notelist

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.Selection
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.oleg.taglibro.R
import me.oleg.taglibro.business.domain.model.Note
import me.oleg.taglibro.databinding.FragmentNoteListBinding
import me.oleg.taglibro.framework.presentation.NoteViewModel
import me.oleg.taglibro.utitlies.SpaceItemDecoration


/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class NoteListFragment : Fragment(),
    OnActionModeDestroyCallback, OnActionItemClickListener {
    private lateinit var binding: FragmentNoteListBinding
    private lateinit var noteListAdapter: NoteListAdapter
    private lateinit var tracker: SelectionTracker<Long>
    private lateinit var actionModeCallback: PrimaryActionModeCallback
    private lateinit var queryTextListener: SearchView.OnQueryTextListener
    private var selectedItems: Selection<Long>? = null

    private val viewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteListBinding.inflate(inflater, container, false)

        noteListAdapter = NoteListAdapter()

        val spaceItemDecoration = SpaceItemDecoration(16)
        binding.listView.addItemDecoration(spaceItemDecoration)

        binding.listView.apply {

            adapter = noteListAdapter
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }

        setupMenu()


        binding.swipeRefreshLayout.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({

                // psudo fetch delay
                binding.swipeRefreshLayout.isRefreshing = false
            }, 2000)
        }

        subscribeUi()

        return binding.root
    }

    private fun setupMenu() {

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main, menu)

                val searchManager =
                    activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
                (menu.findItem(R.id.menu_search)?.actionView as SearchView).apply {

                    setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
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
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_add -> {
                        findNavController().navigate(NoteListFragmentDirections.actionNewNoteEditor())
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        actionModeCallback = PrimaryActionModeCallback(this, this)

        initTracker()

        noteListAdapter.tracker = tracker
    }

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
                            binding.root,
                            R.menu.main_selection,
                            (selectedItems as Selection<Long>).size().toString()
                        )

                    } else actionModeCallback.finishActionMode()


                }
            }

        )
    }

    private fun subscribeUi() {

        lifecycleScope.launch {
            viewModel.getAll().collectLatest {
                noteListAdapter.submitData(it)
            }
        }
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

                viewModel.delete(pendingDeleteItems)
                binding.listView.invalidate()
                binding.listView.layoutManager?.removeAllViews()

                actionModeCallback.finishActionMode()
            }

            R.id.menu_select_all -> {
//                val size = adapter.currentList?.size
//                if (size != null) {
//                    for (index in 0 until size) {
//                        if (!tracker.isSelected(adapter.getItemId(index)))
//                            tracker.select(adapter.getItemId(index))
//                    }
//                }

            }
        }
    }

    override fun onActionModeDestroy() {
        tracker.clearSelection()

        Log.i(tag, "onActionModeDestroy")
    }
}
