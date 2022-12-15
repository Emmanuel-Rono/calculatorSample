package View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvm_apk.R
import com.example.mvvm_apk.databinding.Activity2Binding

private lateinit var binding:Activity2Binding
class Activity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        binding= Activity2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
  setContentView(binding.root)
        binding.
    }
}