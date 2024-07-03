package com.bignerdranch.android.fuck3.ui.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bignerdranch.android.fuck3.databinding.FragmentHomeBinding
import com.bignerdranch.android.fuck3.ui.database.MyData
import com.bignerdranch.android.fuck3.ui.database.MyDataBase
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var ivMyImage: ImageView
    private lateinit var imageUrl: Uri
    private lateinit var myDataBase: MyDataBase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val lastUser = myDataBase.getDbDao().getLastCreatedUser()
            lastUser?.let {
                binding.username.setText(it.name)
                binding.usersurname.setText(it.surname)
                binding.group.setText(it.group)

                if (it.image.isNotEmpty()) {
                    val bitmap = BitmapFactory.decodeByteArray(it.image, 0, it.image.size)
                    ivMyImage.setImageBitmap(bitmap)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val binding = _binding!!

        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        myDataBase = MyDataBase.getInstance(requireContext())
        imageUrl = createImageUri()

        ivMyImage = binding.ivMyImage
        ivMyImage.setOnClickListener {
            takePicture.launch(imageUrl)
        }

        binding.save.setOnClickListener {
            val name = binding.username.text.toString()
            val surname = binding.usersurname.text.toString()
            val group = binding.group.text.toString()

            if (name.isNotEmpty() && surname.isNotEmpty() && group.isNotEmpty()) {
                lifecycleScope.launch {
                    val imageByteArray = getImageByteArray()
                    val existingUser = myDataBase.getDbDao().getUserByNameAndSurname(name, surname)
                    if (existingUser != null) {
                        val updatedUser = existingUser.copy(group = group)
                        myDataBase.getDbDao().updateMyData(updatedUser)
                        showToast("Данные обновлены")
                    } else {
                        val newUser = MyData(id = 0, image = imageByteArray, name = name, surname = surname, group = group)
                        myDataBase.getDbDao().insertMyData(newUser)
                        showToast("Данные созданы")
                    }
                }
            } else {
                showToast("Обнаружены пустые поля. Заполните их")
            }
        }

        return binding.root
    }

    private fun getImageByteArray(): ByteArray {
        val imageFile = File(requireActivity().filesDir, "myPhoto.png")
        if (!imageFile.exists()) {
            return ByteArray(0)
        }

        val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }


    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            ivMyImage.setImageURI(null)
            ivMyImage.setImageURI(imageUrl)
        }
    }

    private fun createImageUri(): Uri {
        val imageFile = File(requireActivity().filesDir, "myPhoto.png")
        if (!imageFile.exists()) {
            try {
                imageFile.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return FileProvider.getUriForFile(
            requireContext(),
            "com.bignerdranch.android.fuck3.FileProvider",
            imageFile
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
