import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mykidsreg.R
import java.text.SimpleDateFormat
import java.util.*

class FirstFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChildrenAdapter
    private val childrenList = mutableListOf<Child>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewChildren)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ChildrenAdapter(childrenList)
        recyclerView.adapter = adapter

        // Populate the list with real-time data
        populateChildrenList()

        return view
    }

    private fun populateChildrenList() {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        for (i in 0 until 7) {
            val date = dateFormat.format(calendar.time)
            childrenList.add(
                Child(
                    date = date,
                    name = "Child Name",
                    arrivalTime = "08:00",
                    collectionTime = "16:00",
                    reasonForAbsence = "N/A"
                )
            )
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        adapter.notifyDataSetChanged()
    }
}

data class Child(
    val date: String,
    val name: String,
    val arrivalTime: String,
    val collectionTime: String,
    val reasonForAbsence: String
)

class ChildrenAdapter(private val childrenList: List<Child>) : RecyclerView.Adapter<ChildrenAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewDate: TextView = view.findViewById(R.id.textViewDate)
        val textViewName: TextView = view.findViewById(R.id.textViewName)
        val textViewArrivalTime: TextView = view.findViewById(R.id.textViewArrivalTime)
        val textViewCollectionTime: TextView = view.findViewById(R.id.textViewCollectionTime)
        val textViewReasonForAbsence: TextView = view.findViewById(R.id.textViewReasonForAbsence)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_child, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val child = childrenList[position]
        holder.textViewDate.text = child.date
        holder.textViewName.text = child.name
        holder.textViewArrivalTime.text = child.arrivalTime
        holder.textViewCollectionTime.text = child.collectionTime
        holder.textViewReasonForAbsence.text = child.reasonForAbsence
    }

    override fun getItemCount(): Int {
        return childrenList.size
    }
}
