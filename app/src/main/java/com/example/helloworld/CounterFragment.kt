import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.helloworld.databinding.FragmentCounterBinding

class CounterFragment : Fragment() {

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
            Toast.makeText(requireContext(), "Counter reset!", Toast.LENGTH_SHORT).show()
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
}
