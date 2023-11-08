package com.reemmousa.toTrade.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.reemmousa.toTrade.R
import com.reemmousa.toTrade.app.invisible
import com.reemmousa.toTrade.app.updateStatusBarColor
import com.reemmousa.toTrade.app.visible
import com.reemmousa.toTrade.dataStructures.courses.Course
import com.reemmousa.toTrade.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CartViewModel by viewModels()
    private val cartAdapter: CartAdapter by lazy { CartAdapter(viewModel) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root

        requireActivity().updateStatusBarColor(R.color.grey_E3E2E5)

        viewModel.readCartFromDB.observe(viewLifecycleOwner) { cartEntity ->
            val courses = mutableListOf<Course>()
            val coursesPrice = mutableListOf<Float>()
            if (cartEntity.isNotEmpty()) {
                cartEntity.forEach { cartCourse ->
                    courses.add(cartCourse.course)
                    try {
                        coursesPrice.add(
                            cartCourse.course.price?.replace(",", "")?.removePrefix("E£")?.toFloat()
                                ?: 0.0f
                        )
                    }catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                cartAdapter.setData(cartEntity)
                binding.rvCourses.adapter = cartAdapter
            }
            binding.errorLayout.apply {
                if (cartEntity.isEmpty()) visible() else invisible()
            }
            binding.rvCourses.apply {
                if (cartEntity.isNotEmpty()) visible() else invisible()
            }
            binding.tvTotal.text = buildString {
                append("Total: ")
                append(coursesPrice.sum())
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}