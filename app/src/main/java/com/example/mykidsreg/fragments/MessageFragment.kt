import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mykidsreg.R
import com.example.mykidsreg.databinding.FragmentMessageBinding
import com.example.mykidsreg.models.Message
import com.example.mykidsreg.viewmodels.MessageViewModel

class MessageFragment : Fragment() {

    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!
    private val messageViewModel: MessageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_message_to_secondFragment)
        }

        binding.buttonSend.setOnClickListener {
            val description = binding.editTextMessage.text.toString()
            val userId = 1 // Replace with actual userId
            val institutionId = 1 // Replace with actual institutionId

            val message = Message(
                description = description,
                userId = userId,
                institutionId = institutionId
            )

            messageViewModel.sendMessage(message)
            messageViewModel.sendMessageResult.observe(viewLifecycleOwner, Observer { sentMessage ->
                if (sentMessage != null) {
                    // Add the sent message to the UI
                    updateMessageHistory(listOf(sentMessage))
                }
            })

            // Optionally, clear the input field after sending message
            binding.editTextMessage.text.clear()
        }

        // Observe messages from ViewModel
        messageViewModel.messages.observe(viewLifecycleOwner, Observer { messages ->
            // Update UI with messages
            updateMessageHistory(messages)
        })

        // Fetch messages on initial load
        messageViewModel.getMessages(1) // Replace with actual userId
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateMessageHistory(messages: List<Message>) {
        val messageHistoryLayout = binding.linearLayoutMessageHistory
        messageHistoryLayout.removeAllViews()

        for (message in messages) {
            val messageView = layoutInflater.inflate(R.layout.message_item, null)

            // Set message details to the view
            val senderNameTextView = messageView.findViewById<TextView>(R.id.textViewSenderName)
            val descriptionTextView = messageView.findViewById<TextView>(R.id.textViewMessageDescription)
            val timestampTextView = messageView.findViewById<TextView>(R.id.textViewMessageTimestamp)

            // Check if user is null and provide a default value if it is
            val senderName = message.user?.name ?: "Unknown Sender"
            senderNameTextView.text = senderName
            descriptionTextView.text = message.description
            timestampTextView.text = "${message.date} ${message.time}"

            messageHistoryLayout.addView(messageView)
        }
    }
}
