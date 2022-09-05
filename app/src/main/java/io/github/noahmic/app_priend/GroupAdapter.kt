package io.github.noahmic.app_priend

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.noahmic.app_priend.databinding.ItemGroupBinding

class GroupAdapter(private val groupList: ArrayList<Group>): RecyclerView.Adapter<GroupAdapter.ViewHolder>() {
    private lateinit var binding : ItemGroupBinding

    inner class ViewHolder: RecyclerView.ViewHolder(binding.root) {
        fun onBind(itemGroup: Group?) {
            binding.groupTitle.text = itemGroup!!.name
            binding.ownerName.text = itemGroup.owner.name
            binding.groupCard.setOnClickListener {
                val intent = Intent(binding.root.context, MemberActivity::class.java)
                intent.putExtra("group", itemGroup.code)
                intent.putExtra("name", itemGroup.name)
                intent.putExtra("owner", itemGroup.owner.name)
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_group, parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(groupList[position])
    }

    override fun getItemCount(): Int = groupList.count()

}
