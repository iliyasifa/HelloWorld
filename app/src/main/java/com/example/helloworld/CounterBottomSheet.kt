package com.example.helloworld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.helloworld.databinding.FragmentCounterBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CounterBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentCounterBinding? = null
    private val binding get() = _binding!!

    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCounterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateCounterText()

        binding.incrementButton.setOnClickListener {
            count++
            updateCounterText()
            showToastIfNeeded()
        }

        binding.decrementButton.setOnClickListener {
            count--
            updateCounterText()
            showToastIfNeeded()
        }

        binding.resetButton.setOnClickListener {
            count = 0
            updateCounterText()

            // ✅ Send data to activity
            parentFragmentManager.setFragmentResult("counterResult", Bundle().apply {
                putInt("finalCount", count)
                putString("action", "reset")
                putBoolean("confirmed", true)
            })

            dismiss() // close the sheet
        }
    }

    private fun updateCounterText() {
        binding.counterText.text = "Count: $count"
    }

    private fun showToastIfNeeded() {
        if (count == 0) {
            Toast.makeText(requireContext(), "Count is Zero", Toast.LENGTH_SHORT).show()
        } else if (count == 10) {
            Toast.makeText(requireContext(), "Count reached TEN!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val bottomSheet =
                it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
        }
    }
}
