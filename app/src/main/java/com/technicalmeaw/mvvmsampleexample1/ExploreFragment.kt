package com.technicalmeaw.mvvmsampleexample1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.technicalmeaw.mvvmsampleexample1.databinding.FragmentExploreBinding
import com.technicalmeaw.mvvmsampleexample1.listeners.StudentListListener
import com.technicalmeaw.mvvmsampleexample1.model.DataModel
import com.technicalmeaw.mvvmsampleexample1.rv.StudentRVAdapter
import com.technicalmeaw.mvvmsampleexample1.viewModel.ExploreFragmentViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [ExploreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExploreFragment : Fragment(), StudentListListener {

    private lateinit var binding: FragmentExploreBinding
    private lateinit var adapter: StudentRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentExploreBinding.inflate(inflater, container, false)

        // Set up the RecyclerView
        adapter = StudentRVAdapter(this, this)
        binding.exploreRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.exploreRecyclerView.adapter = this.adapter

        // Get the ViewModel
        val exploreFragmentViewModel = ViewModelProvider(this)[ExploreFragmentViewModel::class.java]

        // Observe changes in LiveData
        exploreFragmentViewModel.getAllData().observe(viewLifecycleOwner
        ) { listData -> adapter.updateStudents(listData) }

        return binding.root
    }

    override fun onClickedStudent(student: DataModel) {
        val fragment = ShowDetailsFragment()
        val args = Bundle()
        args.putString("id", student.id)
        fragment.arguments = args

        parentFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .addToBackStack("Explore")
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }



}