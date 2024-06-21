import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mykidsreg.R

class FooterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.footer_navigation, container, false)

        // Find buttons
        val button1 = view.findViewById<Button>(R.id.button1)
        val button2 = view.findViewById<Button>(R.id.button2)
        val button3 = view.findViewById<Button>(R.id.button3)
        val button4 = view.findViewById<Button>(R.id.button4)

        // Set OnClickListener for each button
        button1.setOnClickListener {
            // Handle button click
            handleButtonClick(button1)
        }

        button2.setOnClickListener {
            // Handle button click
            handleButtonClick(button2)
        }

        button3.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_fragment_message)
            handleButtonClick(button3)
        }

        button4.setOnClickListener {
            // Navigate to the desired destination using the navigation controller
            findNavController().navigate(R.id.action_firstFragment_to_padagogue)
            handleButtonClick(button4)
        }

        return view
    }

    private fun handleButtonClick(button: Button) {
        // Change background color to green when button is pressed
        button.setBackgroundResource(R.color.green)

        // Optionally, you can reset the background color after a delay or on another event
        button.postDelayed({
            button.setBackgroundResource(R.drawable.button_selector)
        }, 500) // 500 milliseconds delay, adjust as needed
    }
}
