package com.tarobu612.bookssearchapp

import android.os.Bundle
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayout
import com.tarobu612.bookssearchapp.ui.bookssearchlist.SearchViewListener
import com.tarobu612.bookssearchapp.ui.bookssearchlist.SearchViewQueryTextListener
import com.tarobu612.bookssearchapp.ui.bookssearchlist.data.SearchTabListener
import com.tarobu612.bookssearchapp.ui.bookssearchlist.data.SearchTabType

class MainActivity : AppCompatActivity() {
    private lateinit var searchTabListener: SearchTabListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout = findViewById<TabLayout>(R.id.books_search_list_tab)
        tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val searchWord = findViewById<SearchView>(R.id.searchView).query.toString()

                    when (tab?.text) {
                        // TODO とりあえず文字列一致
                        "Google" -> {
                            searchTabListener.onChanged(SearchTabType.GOOGLE, searchWord)
                        }
                        "楽天" -> {
                            searchTabListener.onChanged(SearchTabType.RAKUTEN, searchWord)
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    // empty
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    // empty
                }
            }
        )
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

    fun setSearchTabListener(listener: SearchTabListener) {
        this.searchTabListener = listener
    }

}
