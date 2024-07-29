package com.gokcenaztorgan.fotografpaylasimi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.gokcenaztorgan.fotografpaylasimi.R
import com.gokcenaztorgan.fotografpaylasimi.adapter.PostAdapter
import com.gokcenaztorgan.fotografpaylasimi.databinding.FragmentAnasayfaBinding
import com.gokcenaztorgan.fotografpaylasimi.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AnasayfaFragment : Fragment(), PopupMenu.OnMenuItemClickListener {
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private var adapter : PostAdapter? = null

    val postList : ArrayList<Post> = ArrayList()
    private var _binding: FragmentAnasayfaBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        db = Firebase.firestore
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnasayfaBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener{
            floatingButtonTiklandi(it)

        }
        binding.recyclerRow.layoutManager = LinearLayoutManager(requireContext())
        firestoreVerileriAl()
        adapter = PostAdapter(postList)
        binding.recyclerRow.adapter = adapter
    }

    fun firestoreVerileriAl(){

        db.collection("Posts").orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Toast.makeText(requireContext(), exception.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                } else {

                    if (snapshot != null) {
                        if (!snapshot.isEmpty) {

                            postList.clear()

                            val documents = snapshot.documents
                            for (document in documents) {
                                val useremail = document.get("email")
                                val comment = document.get("comment")
                                val downloadUrl = document.get("downloadurl")
                                //val timestamp = document.get("date") as Timestamp
                                //val date = timestamp.toDate()

                                val post = Post(useremail.toString(), comment.toString(), downloadUrl.toString())
                                postList.add(post)
                            }
                            adapter!!.notifyDataSetChanged()

                        }
                    }

                }
            }
    }
    fun floatingButtonTiklandi(view: View){
        val popup = PopupMenu(requireContext(),binding.floatingActionButton)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.my_popup_menu,popup.menu)
        popup.setOnMenuItemClickListener(this)
        popup.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.yuklemeEkran){
            val action = AnasayfaFragmentDirections.actionAnasayfaFragmentToYuklemeFragment()
            Navigation.findNavController(requireView()).navigate(action)
        } else if (item?.itemId == R.id.cikisEkran) {
            auth.signOut()
            val action = AnasayfaFragmentDirections.actionAnasayfaFragmentToKayitFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }
        return true
    }


}