package com.tarobu612.bookssearchapp.ui.bookssearchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.tarobu612.bookssearchapp.R
import com.tarobu612.bookssearchapp.databinding.NoBookItemBinding
import com.tarobu612.bookssearchapp.databinding.SearchBookItemBinding
import com.tarobu612.bookssearchapp.ui.bookssearchlist.data.SearchListItem

class BooksSearchListAdapter(
    private var data: List<SearchListItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when {
            data.isEmpty() -> NoItemViewHolder(
                NoBookItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> SearchItemViewHolder(
                SearchBookItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchItemViewHolder -> holder.bind(data[position])
            is NoItemViewHolder -> holder.bind()
        }
    }

    override fun getItemCount() = data.count()

    fun updateList(newList: List<SearchListItem>) {
        this.data = newList
        notifyDataSetChanged()
    }

    class SearchItemViewHolder(
        binding: SearchBookItemBinding
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

}
