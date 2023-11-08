package com.emmanuel_rono.intern_test_apk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class Detail_Screen : AppCompatActivity() {
companion object {
    const val EXTRA_POST = "extra_post"
}
private lateinit var titleTextView: TextView
private lateinit var bodyTextView: TextView
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detail_screen)

    titleTextView = findViewById(R.id.titleTextView)
    bodyTextView = findViewById(R.id.bodyTextView)

    val post = intent.getParcelableExtra<Post>(EXTRA_POST)
    if (post != null) {
        displayPostDetails(post)
    } else {
        Toast.makeText(this, "Error retrieving post details", Toast.LENGTH_SHORT).show()
        finish()
    }
}
private fun displayPostDetails(post: Post) {
    titleTextView.text = post.title
    bodyTextView.text = post.body
}
}