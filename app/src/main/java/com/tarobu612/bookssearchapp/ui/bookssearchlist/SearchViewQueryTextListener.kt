package com.tarobu612.bookssearchapp.ui.bookssearchlist

import androidx.appcompat.widget.SearchView

class SearchViewQueryTextListener(
    private val listener: SearchViewListener
) : SearchView.OnQueryTextListener {

    override fun onQueryTextChange(newText: String): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        if (query.isNotEmpty()) {
            listener.onSearch(query)
        }

        return false
    }

}
