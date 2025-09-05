package com.example.memorycue

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memorycue.databinding.ActivityMainBinding

/**
 * Main screen for the Memory Cue app. Users can add short memory cues and schedule a reminder
 * in five minutes. Cues are displayed in a simple list.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val cues = mutableListOf<String>()
    private lateinit var adapter: CueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CueAdapter(cues)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.buttonAdd.setOnClickListener {
            val text = binding.editTextCue.text.toString()
            if (text.isNotBlank()) {
                cues.add(text)
                adapter.notifyItemInserted(cues.size - 1)
                binding.editTextCue.text.clear()
                // TODO: persist data as needed
            }
        }

        binding.buttonRemind.setOnClickListener {
            scheduleReminder()
        }
    }

    /**
     * Schedule a reminder notification five minutes from now using AlarmManager.
     */
    private fun scheduleReminder() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val triggerAtMillis = System.currentTimeMillis() + 5 * 60 * 1000
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
    }
}