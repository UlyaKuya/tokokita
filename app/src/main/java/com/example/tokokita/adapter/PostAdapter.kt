package com.example.tokokita.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tokokita.databinding.ItemPostBinding
import com.example.tokokita.model.Post

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var posts = emptyList<Post>()

    fun submitList(newPosts: List<Post>) {
        posts = newPosts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder {

        val binding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PostViewHolder,
        position: Int
    ) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class PostViewHolder(
        private val binding: ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {

            binding.tvPostId.text =
                "Post #${post.id}"

            binding.tvPostTitle.text =
                post.title

            binding.tvPostBody.text =
                post.body
        }
    }
}