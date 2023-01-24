package com.tarobu612.bookssearchapp.ui.bookssearchlist

import com.tarobu612.bookssearchapp.ui.bookssearchlist.data.SearchListDisplayType
import com.tarobu612.bookssearchapp.ui.bookssearchlist.data.SearchListItem
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class BooksSearchListAdapterTest {
    private lateinit var adapter: BooksSearchListAdapter

    @BeforeEach
    fun setUp() {
        adapter = BooksSearchListAdapter(listOf())
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "0,LIST,0", "1,LIST,1", "4,LIST,4", "5,LIST,5", "8,LIST,8", "9,LIST,9",
            "0,SHELVES,0", "1,SHELVES,1", "4,SHELVES,1", "5,SHELVES,2", "8,SHELVES,2", "9,SHELVES,3"
        ]
    )
    @DisplayName("test_getListItemCount_表示する行数分を取得する")
    fun getListItemCount(listCount: Int, displayType: SearchListDisplayType, expected: Int) {
        // arrange
        val list = createSearchList(listCount)

        // act
        val actual = adapter.getListItemCount(list, displayType)

        // assert
        Assertions.assertEquals(expected, actual)
    }


    @ParameterizedTest
    @CsvSource(value = ["0,0", "1,1", "4,1", "5,2", "8,2", "9,3"])
    @DisplayName("test_mapFourOfEach_4つずつを１つにまとめてリスト化する")
    fun mapFourOfEach(listCount: Int, expected: Int) {
        // arrange
        val list = createSearchList(listCount)

        // act
        val actual = adapter.mapFourOfEach(list)

        // assert
        Assertions.assertEquals(expected, actual.count())
    }

    private fun createSearchList(count: Int): List<SearchListItem> {
        return mutableListOf<SearchListItem>().also { list ->
            repeat(count) {
                list.add(createSearchListItem())
            }
        }.toList()
    }

    private fun createSearchListItem() = SearchListItem(
        title = "",
        subtitle = "",
        authors = listOf(),
        publishedDate = "",
        description = "",
        pageCount = 1,
        reviewAverage = 1,
        reviewCount = 1,
        thumbnail = "",
        infoLink = ""
    )

}