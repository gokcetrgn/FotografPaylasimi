package com.gokcenaztorgan.fotografpaylasimi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gokcenaztorgan.fotografpaylasimi.databinding.RecyclerRowBinding
import com.gokcenaztorgan.fotografpaylasimi.model.Post
import com.squareup.picasso.Picasso

class PostAdapter(private val postList: ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.PostHolder>() {

    class PostHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.recyclerCommentText.text = postList[position].comment
        holder.binding.recyclerEmailText.text = postList[position].email
        Picasso.get().load(postList[position].downloadUrl).into(holder.binding.recyclerImageView)
    }
}