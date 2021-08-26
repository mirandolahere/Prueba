package com.application.prueba.iu.ui.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.application.prueba.R
import com.application.prueba.data.network.Resource
import com.application.prueba.databinding.FragmentHomeBinding
import com.application.prueba.iu.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding
    var idVisualizado:Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listImage()

        binding.btnCargar.setOnClickListener {

            if(binding.textHome.text.toString() == "")
                Toast.makeText(context, "Ingresa una url", Toast.LENGTH_SHORT).show()
            else {
                DownloadImage(binding.image).execute(binding.textHome.text.toString())
                viewModel.saveImage(binding.textHome.text.toString())
                binding.textHome.text.clear()
                //DownloadImage(binding.image2).execute(binding.textHome.text.toString())
            }



        }

        binding.ivDelete.setOnClickListener(View.OnClickListener { v ->
                val idTipo = binding.image.id
            Log.d("idVisualizado",idVisualizado.toString())
                viewModel.deleteImageById(idVisualizado)


            })

        viewModel.photoResponse.observe(viewLifecycleOwner, {
            //binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {

                        binding.llPg.removeAllViews()
                        var tableTipo = LinearLayout(context)
                        tableTipo.gravity = Gravity.CENTER_HORIZONTAL
                        for (image in it.datos) {
                            var imageView = ImageView(context)

                            Log.d("photosss", image.url)

                            //imageView.setImageResource(R.drawable.layout_colors)
                            DownloadImage(imageView).execute(image.url)
                            imageView.id = image.id!!
                            val lp = RelativeLayout.LayoutParams(
                                100,
                                100
                            )
                            DownloadImage(binding.image).execute(image.url)


                            imageView.setOnClickListener(View.OnClickListener { v ->
                                val idTipo = (v as ImageView).id
                                idVisualizado = idTipo
                                viewModel.listImageById(idTipo)


                            })

                            imageView.layoutParams = lp
                            tableTipo.addView(imageView)
                        }

                        binding.llPg.addView(tableTipo)
                    if(it.datos.size >=6) {
                        binding.btnCargar.isEnabled = false
                        binding.btnCargar.setBackgroundColor(R.drawable.border_corners_login)
                    }

                }
                is Resource.Failure -> {

                }
                else -> {
                }
            }
        })


        viewModel.photoResponseOne.observe(viewLifecycleOwner, {
            //binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    DownloadImage(binding.image).execute(it.datos.url)


                }
                is Resource.Failure -> {

                }
                else -> {
                }
            }
        })
    }

    private inner class DownloadImage(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>() {
        init {

        }
        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            var image: Bitmap? = null
            try {

                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
                binding.ivDelete.visibility = View.VISIBLE

            }
            catch (e: Exception) {
                Log.e("Error Message", e.message.toString())
                e.printStackTrace()
            }
            return image
        }
        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }
    }

}