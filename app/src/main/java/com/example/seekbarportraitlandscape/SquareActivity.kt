package com.example.seekbarportraitlandscape

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

const val SQUARE_SCALE_FACTOR = 4
const val EXTRA_TAPPED_INSIDE_SQUARE = "com.example.seekbarportraitlandscape.TAPPED_INSIDE_SQUARE"

class SquareActivity : AppCompatActivity() {

    private lateinit var squareImage: ImageView
    private lateinit var container: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_square)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        squareImage = findViewById(R.id.square)
        container = findViewById(R.id.container)

        var squareSize = intent.getIntExtra(EXTRA_SQUARE_SIZE, 100)
        if (squareSize == 0) {
            squareSize = 1
        }
        squareImage.layoutParams.height = squareSize * SQUARE_SCALE_FACTOR
        squareImage.layoutParams.width = squareSize * SQUARE_SCALE_FACTOR

        squareImage.setOnClickListener {
            squareTapped(true)
        }

        container.setOnClickListener {
            squareTapped(false)
        }
    }

    private fun squareTapped(didTapSquare: Boolean) {
        // create intent to carry data back to launching activity
        val resultIntent = Intent()
        resultIntent.putExtra(EXTRA_TAPPED_INSIDE_SQUARE, didTapSquare)
        // set result and include intent
        setResult(RESULT_OK, resultIntent)
        finish() // end this activity
    }
}