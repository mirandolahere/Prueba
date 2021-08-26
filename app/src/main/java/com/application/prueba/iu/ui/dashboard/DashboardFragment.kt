package com.application.prueba.iu.ui.dashboard

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
import com.application.prueba.data.network.Resource
import com.application.prueba.databinding.FragmentDashboardBinding
import com.application.prueba.iu.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listImage()

        viewModel.photoResponse.observe(viewLifecycleOwner, {
            //binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    binding.llMosaico.removeAllViews()
                    var colum : Int = 0


                    var tableTipoFila = LinearLayout(context)
                    tableTipoFila.gravity = Gravity.CENTER_HORIZONTAL
                    var tableTipo = LinearLayout(context)
                    tableTipo.gravity = Gravity.CENTER_HORIZONTAL
                    for (image in it.datos) {


                            colum++
                            if(colum<2)
                            {
                                var imageView = ImageView(context)
                                DownloadImage(imageView).execute(image.url)
                                imageView.id = image.id!!
                                val lp = RelativeLayout.LayoutParams(
                                    400,
                                    200
                                )

                                imageView.layoutParams = lp
                                tableTipo.addView(imageView)
                            }else
                            {
                               // tableTipoFila.addView(tableTipo)
                                colum = 0
                            }

                        //tableTipo.addView(tipoMenu)
                    }

                    binding.llMosaico.addView(tableTipo)

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