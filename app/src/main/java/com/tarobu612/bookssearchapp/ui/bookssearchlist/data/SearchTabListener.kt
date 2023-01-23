package com.tarobu612.bookssearchapp.ui.bookssearchlist.data

interface SearchTabListener {
    fun onChanged(changedTabType: SearchTabType, searchWord: String)
}
