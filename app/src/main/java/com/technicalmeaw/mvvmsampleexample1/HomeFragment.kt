package com.technicalmeaw.mvvmsampleexample1

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.technicalmeaw.mvvmsampleexample1.databinding.FragmentHomeBinding


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.addStudentBtn.setOnClickListener {
            val intent = Intent(activity, AddStudentActivity::class.java)
            startActivity(intent)
        }

        binding.viewAllStudentBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .addToBackStack("Home")
                .replace(R.id.fragmentContainerView, ExploreFragment())
                .commit()
        }


        return binding.root
    }
}