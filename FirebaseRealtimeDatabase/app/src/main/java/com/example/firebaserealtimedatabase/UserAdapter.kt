package com.example.firebaserealtimedatabase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaserealtimedatabase.databinding.ItemDataBinding

class UserAdapter(private val dataSet: ArrayList<User>,private var listener: OnItemClickListener) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    inner class ViewHolder(var binding:ItemDataBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.getnama.text=user.nama
            binding.getnim.text=user.nim

            binding.root.setOnClickListener(){
                var position=adapterPosition
                if (position!=RecyclerView.NO_POSITION){
                    var item=dataSet[position]
                    item?.let {
                        binding.edit.setOnClickListener(){
                            listener.updateUser(item)
                        }
                        binding.delete.setOnClickListener(){
                            listener.deleteUser(item)
                        }
                    }
                }
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding=ItemDataBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
//        viewHolder.textView.text = dataSet[position]
        var user=dataSet[position]
        viewHolder.bind(user)
        viewHolder.binding.edit.setOnClickListener(){
            listener.updateUser(user)
        }
        viewHolder.binding.delete.setOnClickListener(){
            listener.deleteUser(user)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
     interface OnItemClickListener{
         fun updateUser(user: User)
         fun deleteUser(user: User)
     }
}