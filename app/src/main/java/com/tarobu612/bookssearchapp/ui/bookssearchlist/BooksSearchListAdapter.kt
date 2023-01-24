package com.tarobu612.bookssearchapp.ui.bookssearchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.tarobu612.bookssearchapp.R
import com.tarobu612.bookssearchapp.databinding.NoBookItemBinding
import com.tarobu612.bookssearchapp.databinding.SearchBookListItemBinding
import com.tarobu612.bookssearchapp.databinding.SearchBookShelvesItemBinding
import com.tarobu612.bookssearchapp.ui.bookssearchlist.data.*

class BooksSearchListAdapter(
    private var data: List<SearchItem>,
    private var displayOption: SearchListDisplayType = SearchListDisplayType.LIST
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SearchRecyclerViewType.NO_ITEM.value -> {
                NoItemViewHolder(
                    NoBookItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            SearchRecyclerViewType.EXIST_ITEM.value -> {
                when (displayOption) {
                    SearchListDisplayType.LIST -> SearchListItemViewHolder(
                        SearchBookListItemBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )
                    )
                    SearchListDisplayType.SHELVES -> SearchShelvesItemViewHolder(
                        SearchBookShelvesItemBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )
                    )
                }
            }
            else -> throw Exception("unexpected case")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchListItemViewHolder -> {
                holder.bind(data[position] as SearchListItem)
            }
            is SearchShelvesItemViewHolder -> {
                val set = mapFourOfEach(data)
                holder.bind(set[position])
            }
            is NoItemViewHolder -> {
                holder.bind()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position]) {
            is NoSearchListItem -> SearchRecyclerViewType.NO_ITEM.value
            is SearchListItem -> SearchRecyclerViewType.EXIST_ITEM.value
            else -> super.getItemViewType(position)
        }
    }

    override fun getItemCount() = getListItemCount(data, displayOption)

    fun updateList(newList: List<SearchListItem>) {
        this.data = newList.ifEmpty {
            listOf(NoSearchListItem())
        }
        notifyDataSetChanged()
    }

    fun getCurrentAllData() = data

    @VisibleForTesting
    fun getListItemCount(
        items: List<SearchItem>,
        displayOption: SearchListDisplayType
    ) = when (displayOption) {
        SearchListDisplayType.LIST -> {
            items.count()
        }
        SearchListDisplayType.SHELVES -> {
            if (items.count() % 4 == 0) {
                items.count() / 4
            } else {
                (items.count() / 4) + 1
            }
        }
    }

    @VisibleForTesting
    fun mapFourOfEach(data: List<SearchItem>): List<ShelvesLine> {
        val result: MutableList<ShelvesLine> = mutableListOf()
        var line = ShelvesLine()

        data.forEachIndexed { index, searchItem ->
            when (index % 4) {
                0 -> {
                    if (line.first != null) {
                        result.add(line)
                        line = ShelvesLine()
                    }
                    line.first = (searchItem as? SearchListItem)?.thumbnail
                }
                1 -> {
                    line.second = (searchItem as? SearchListItem)?.thumbnail
                }
                2 -> {
                    line.third = (searchItem as? SearchListItem)?.thumbnail
                }
                3 -> {
                    line.forth = (searchItem as? SearchListItem)?.thumbnail
                }
            }

            if (data.count() - 1 == index) {
                result.add(line)
            }
        }

        return result.toList()
    }

    class SearchListItemViewHolder(
        binding: SearchBookListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val title: AppCompatTextView = binding.title
        private val subtitle: AppCompatTextView = binding.subtitle
        private val thumbnail: AppCompatImageView = binding.thumbnail
        private val reviewCount: AppCompatTextView = binding.reviewCount
        private val reviewCountSign: AppCompatTextView = binding.reviewCountSign
        private val pageCount: AppCompatTextView = binding.pageCount
        private val pageCountSign: AppCompatTextView = binding.pageCountSign
        private val authors: AppCompatTextView = binding.authors
        private val description: AppCompatTextView = binding.description

        fun bind(item: SearchListItem) {
            title.text = item.title
            subtitle.text = item.subtitle
            reviewCount.text = item.reviewCount.toString()
            reviewCountSign.text = "レビュー"
            pageCount.text = item.pageCount.toString()
            pageCountSign.text = "ページ"
            authors.text = item.authors.joinToString(", ")
            description.text = item.description
            setImage(
                imageUrl = item.thumbnail,
                view = thumbnail
            )
        }

        private fun setImage(imageUrl: String, view: AppCompatImageView) {
            if (imageUrl.isEmpty()) {
                return
            }

            Picasso.get()
                .load(imageUrl)
                .fit()
                .centerInside()
                .into(view, object : Callback {
                    override fun onSuccess() {
                        // pass
                    }

                    override fun onError(e: Exception) {
                        view.setImageResource(R.drawable.ic_launcher_foreground)
                    }
                })
        }
    }

    class SearchShelvesItemViewHolder(
        binding: SearchBookShelvesItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val firstImage: AppCompatImageView = binding.firstImage
        private val secondImage: AppCompatImageView = binding.secondImage
        private val thirdImage: AppCompatImageView = binding.thirdImage
        private val forthImage: AppCompatImageView = binding.forthImage

        fun bind(line: ShelvesLine) {
            setImage(line.first, firstImage)
            setImage(line.second, secondImage)
            setImage(line.third, thirdImage)
            setImage(line.forth, forthImage)
        }

        private fun setImage(imageUrl: String?, view: AppCompatImageView) {
            if (imageUrl.isNullOrEmpty()) {
                return
            }

            Picasso.get()
                .load(imageUrl)
                .fit()
                .centerInside()
                .into(view, object : Callback {
                    override fun onSuccess() {
                        // pass
                    }

                    override fun onError(e: Exception) {
                        view.setImageResource(R.drawable.ic_launcher_foreground)
                    }
                })
        }

    }

    class NoItemViewHolder(
        binding: NoBookItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val noItemText: AppCompatTextView = binding.noItemText

        fun bind() {
            noItemText.text = "該当する書籍がありません"
        }

    }

    data class ShelvesLine(
        var first: String? = null,
        var second: String? = null,
        var third: String? = null,
        var forth: String? = null,
    )

}
