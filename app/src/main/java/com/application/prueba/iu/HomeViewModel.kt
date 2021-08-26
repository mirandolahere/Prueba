package com.application.prueba.iu

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.koin.data.model.Configurations
import com.application.koin.data.model.Photos
import com.application.prueba.data.network.Resource
import com.application.prueba.data.repository.ConfigurationRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val configurationRepository : ConfigurationRepository) : ViewModel() {

    private val _authResponse: MutableLiveData<Boolean> = MutableLiveData()
    val authResponse: LiveData<Boolean>
        get() = _authResponse

    private val _photoResponse: MutableLiveData<Resource<List<Photos>>> = MutableLiveData()
    val photoResponse: LiveData<Resource<List<Photos>>>
        get() = _photoResponse

    private val _photoResponseOne: MutableLiveData<Resource<Photos>> = MutableLiveData()
    val photoResponseOne: LiveData<Resource<Photos>>
        get() = _photoResponseOne
    fun updateConfiguration() = viewModelScope.launch {
       val configuration = Configurations("Prueba")
      /* configurationDao.insert(configuration)
       configurationDao.getAll()*/

   }
    fun saveImage(url: String) = viewModelScope.launch {

        val photo = Photos(url)
        configurationRepository.photo(photo)
        _photoResponse.value = configurationRepository.list()

    }

    fun listImage() = viewModelScope.launch {

        _photoResponse.value = configurationRepository.list()
    }
    fun listImageById(idTipo: Int) = viewModelScope.launch {

        _photoResponseOne.value = configurationRepository.listById(idTipo)
    }
    fun deleteImageById(idTipo: Int) = viewModelScope.launch {

        configurationRepository.deleteById(idTipo)
        _photoResponse.value = configurationRepository.list()

    }
   fun login(usuario: Editable, contrasena:Editable) = viewModelScope.launch {

       _authResponse.value = isEmpty(usuario,contrasena)


       //_authResponse.value = configurationRepository.login("Prueba" , "Prueba")
   }

    private fun isEmpty(usuario: Editable, contrasena: Editable) : Boolean {

        if(usuario.toString() == "" || contrasena.toString() == "")
            return true
        return false
    }


}