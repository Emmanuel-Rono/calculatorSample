package View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mvvm_apk.R
import com.example.mvvm_apk.databinding.ActivityMainBinding
import com.example.mvvm_apk.databinding.FragmentBindingViewBinding

class bindingView : Fragment() {
    //Defining the ViewBinding and referencing the FragmentBindingViewBinding class
    lateinit var binding:FragmentBindingViewBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentBindingViewBinding.bind(view)

        //Accessing the View Directly
        binding.Textvieew

    }
    }


