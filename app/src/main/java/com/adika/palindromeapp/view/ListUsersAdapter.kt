package com.adika.palindromeapp.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adika.palindromeapp.api.response.DataItem
import com.adika.palindromeapp.databinding.ItemUsersBinding
import com.bumptech.glide.Glide

class ListUsersAdapter(private val onUserClick: (DataItem) -> Unit) :
    RecyclerView.Adapter<ListUsersAdapter.ListViewHolder>() {

    private var listUsers: List<DataItem> = listOf()

    class ListViewHolder(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(user: DataItem, onUserClick: (DataItem) -> Unit) {
            binding.apply {
                tvItemName.text = "${user.firstName} ${user.lastName}"
                tvItemEmail.text = user.email
                Glide.with(root.context)
                    .load(user.avatar)
                    .circleCrop()
                    .into(imgUser)

                root.setOnClickListener {
                    onUserClick(user)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listUsers.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUsers.getOrNull(position)
        user?.let { holder.bind(it, onUserClick) }
    }

    fun submitList(newList: List<DataItem>) {
        listUsers = newList
        notifyDataSetChanged()
    }
}
