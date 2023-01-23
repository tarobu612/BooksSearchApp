package com.tarobu612.bookssearchapp

import android.os.Bundle
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.tarobu612.bookssearchapp.ui.bookssearchlist.SearchViewListener
import com.tarobu612.bookssearchapp.ui.bookssearchlist.SearchViewQueryTextListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showLoading() {
        findViewById<ProgressBar>(R.id.loading_progress_bar).also { it.isVisible = true }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun hideLoading() {
        findViewById<ProgressBar>(R.id.loading_progress_bar)
            .also { it.isGone = true }
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun setUpSearchBar(listener: SearchViewListener) {
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(SearchViewQueryTextListener(listener))
    }

    fun clearSearchBar() {
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(null)
    }

}
