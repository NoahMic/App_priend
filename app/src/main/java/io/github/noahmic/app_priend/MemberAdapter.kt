package io.github.noahmic.app_priend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.noahmic.app_priend.databinding.ItemManitoBinding

class MemberAdapter(private val memberList: ArrayList<Member>): RecyclerView.Adapter<MemberAdapter.ViewHolder>() {
    private lateinit var binding : ItemManitoBinding

    inner class ViewHolder: RecyclerView.ViewHolder(binding.root) {
        fun onBind(itemMember: Member?) {
            binding.memberName.text = itemMember?.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_manito, parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(memberList[position])
    }

    override fun getItemCount(): Int = memberList.count()
}