package View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvm_apk.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var name:String="Emmanuel"
        binding.TextName.text=name
    }
}