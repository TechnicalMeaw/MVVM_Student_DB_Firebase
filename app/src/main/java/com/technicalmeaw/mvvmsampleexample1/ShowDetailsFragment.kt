package com.technicalmeaw.mvvmsampleexample1

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.technicalmeaw.mvvmsampleexample1.databinding.FragmentShowDetailsBinding
import com.technicalmeaw.mvvmsampleexample1.viewModel.ShowDetailsFragmentViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [ShowDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowDetailsFragment : Fragment() {

    lateinit var binding: FragmentShowDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentShowDetailsBinding.inflate(inflater, container, false)

        val id: String = arguments?.get("id").toString()

        // Get the view model
        val studentDetailsViewModel =
            ViewModelProvider(this)[ShowDetailsFragmentViewModel::class.java]

        studentDetailsViewModel.getStudentDetails(id).observe(
            viewLifecycleOwner
        ) { studentData ->
            Picasso.get().load(studentData.profileImageUrl)
                .into(binding.studentDetailsCircleImageView)
            binding.studentNameTextView.text = "${studentData.firstName} ${studentData.lastName}"
            binding.studentRollNumberTextView.text = studentData.rollNum
            binding.studentRegistrationTextView.text = studentData.regNum
            binding.studentEmailTextView.text = studentData.email
            binding.studentBranchTextView.text = studentData.branch
            binding.studentYearTextView.text = studentData.year
        }



        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Leave empty do disable back press or
                    // write your code which you want
                    val manager: FragmentManager = activity!!.supportFragmentManager
                    manager.popBackStackImmediate()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }
}
