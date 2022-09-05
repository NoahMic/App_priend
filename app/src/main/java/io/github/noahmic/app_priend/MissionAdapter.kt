package io.github.noahmic.app_priend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.noahmic.app_priend.databinding.ItemMissionBinding

class MissionAdapter(private val missionList: ArrayList<Mission>): RecyclerView.Adapter<MissionAdapter.ViewHolder>() {

    private lateinit var binding: ItemMissionBinding

    inner class ViewHolder: RecyclerView.ViewHolder(binding.root) {
        fun onBind(itemMission: Mission?) {
            binding.missionSetTxt.text = itemMission!!.sets
            binding.missionContentTxt.text = itemMission.content
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_mission, parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(missionList[position])
    }

    override fun getItemCount(): Int = missionList.count()
}