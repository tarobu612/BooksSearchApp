package com.tarobu612.bookssearchapp.ui.bookssearchlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.tarobu612.bookssearchapp.MainActivity
import com.tarobu612.bookssearchapp.R
import com.tarobu612.bookssearchapp.databinding.BooksSearchListFragmentBinding
import com.tarobu612.bookssearchapp.repository.GoogleBooksRepository
import com.tarobu612.bookssearchapp.repository.RakutenBooksBookRepository
import com.tarobu612.bookssearchapp.ui.bookssearchlist.data.SearchListItem
import com.tarobu612.bookssearchapp.ui.bookssearchlist.data.SearchTabListener
import com.tarobu612.bookssearchapp.ui.bookssearchlist.data.SearchTabType

class BooksSearchListFragment : Fragment(), BooksSearchListContract.View, SearchViewListener,
    SearchTabListener {
    private var _binding: BooksSearchListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var activity: MainActivity

    private lateinit var presenter: BooksSearchListPresenter

    private var booksSearchListAdapter: BooksSearchListAdapter? = null

    private var currentTab: SearchTabType = SearchTabType.GOOGLE

    override fun onAttach(context: Context) {
        super.onAttach(context)

        presenter = BooksSearchListPresenter(
            output = this,
            googleBooksRepository = GoogleBooksRepository.getInstance(),
            rakutenBooksBookRepository = RakutenBooksBookRepository.getInstance()
        )

        activity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BooksSearchListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpList()
        activity.setUpSearchBar(this)
        activity.setSearchTabListener(this)

        presenter.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        activity.clearSearchBar()
        presenter.viewDestroyed()
    }

    override fun showBooksList(data: List<SearchListItem>) {
        booksSearchListAdapter?.updateList(data)
    }

    override fun showNoBooksList() {
        booksSearchListAdapter?.updateList(listOf())
    }

    override fun showLoading() {
        activity.showLoading()
    }

    override fun hideLoading() {
        activity.hideLoading()
    }

    override fun onSearch(query: String) {
        presenter.startSearch(query, currentTab)
    }

    override fun onChanged(changedTabType: SearchTabType, searchWord: String) {
        if (searchWord.isNotEmpty()) {
            presenter.startSearch(searchWord, changedTabType)
        }

        currentTab = changedTabType
    }

    private fun setUpList() {
        if (booksSearchListAdapter != null) {
            binding.booksSearchList.adapter = booksSearchListAdapter
            return
        }

        binding.booksSearchList.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )

            val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            val drawable = ContextCompat.getDrawable(
                context,
                R.drawable.shape_search_books_list_divider
            )
            drawable?.let {
                divider.setDrawable(it)
            }
            addItemDecoration(divider)

            adapter = BooksSearchListAdapter(listOf()).also { booksSearchListAdapter = it }
        }
    }

}
