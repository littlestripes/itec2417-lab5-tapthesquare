package com.example.seekbarportraitlandscape

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

const val EXTRA_SQUARE_SIZE = "com.example.seekbarportraitlandscape.SQUARE_SIZE"

class MainActivity : AppCompatActivity() {

    private lateinit var seekbar: SeekBar
    private lateinit var seekbarLabel: TextView
    private lateinit var showSquareButton: Button

    private val squareResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result -> handleActivityResult(result)
    }

    private fun handleActivityResult(result: ActivityResult) {
        // read result from SquareActivity
        if (result.resultCode == RESULT_OK) {
            val intent = result.data
            val tapped = intent?.getBooleanExtra(EXTRA_TAPPED_INSIDE_SQUARE, false) ?: false
            val message = if (tapped) { "You tapped the square" } else { "You missed the square" }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } else if (result.resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "You did not try to tap the square", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        seekbar = findViewById(R.id.seek_bar)
        seekbarLabel = findViewById(R.id.seek_bar_label)
        showSquareButton = findViewById(R.id.show_square_button)

        val initialProgress = seekbar.progress
        updateLabel(initialProgress)

        seekbar.setOnSeekBarChangeListener( object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateLabel(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        showSquareButton.setOnClickListener {
            showSquare()
        }
    }

    private fun showSquare() {
        /*
        val showSquareIntent = Intent(this, SquareActivity::class.java)
        showSquareIntent.putExtra(EXTRA_SQUARE_SIZE, seekbar.progress)
        squareResultLauncher.launch(showSquareIntent)
        */

        Intent(this, SquareActivity::class.java).apply {
            putExtra(EXTRA_SQUARE_SIZE, seekbar.progress)
            squareResultLauncher.launch(this)
        }
    }

    fun updateLabel(progress: Int) {
        seekbarLabel.text = getString(R.string.seekbar_value_message, progress)
    }
}