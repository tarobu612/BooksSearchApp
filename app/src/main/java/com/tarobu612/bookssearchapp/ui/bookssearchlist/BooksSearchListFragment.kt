package com.tarobu612.bookssearchapp.ui.bookssearchlist

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.tarobu612.bookssearchapp.ui.bookssearchlist.data.SearchListItem

class BooksSearchListFragment : Fragment(), BooksSearchListContract.View, SearchViewListener {
    private var _binding: BooksSearchListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var activity: MainActivity

    private lateinit var presenter: BooksSearchListPresenter

    private var booksSearchListAdapter: BooksSearchListAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        presenter = BooksSearchListPresenter(
            output = this,
            googleBooksRepository = GoogleBooksRepository.getInstance()
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
        Log.d(TAG, "showNoBooksList")
    }

    override fun showLoading() {
        activity.showLoading()
    }

    override fun hideLoading() {
        activity.hideLoading()
    }

    override fun onSearch(query: String) {
        presenter.startSearch(query)
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

    companion object {
        private const val TAG: String = "BooksSearchListFragment"
    }

}
