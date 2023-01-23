package com.tarobu612.bookssearchapp.ui.bookssearchlist

import androidx.appcompat.widget.SearchView
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class SearchViewQueryTextListener(
    private val listener: SearchViewListener
) : SearchView.OnQueryTextListener {
    private var lastSearchedTime: LocalDateTime? = null

    override fun onQueryTextChange(newText: String): Boolean {
        if (newText.isEmpty()) {
            return false
        }

        if (lastSearchedTime == null) {
            lastSearchedTime = LocalDateTime.now()
            listener.onSearch(newText)
            return false
        }

        val now = LocalDateTime.now()
        if (lastSearchedTime!!.until(now, ChronoUnit.SECONDS) > 1) {
            listener.onSearch(newText)
        }
        lastSearchedTime = now

        return false
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        lastSearchedTime = LocalDateTime.now()
        listener.onSearch(query)

        return false
    }

}
