package com.example.el1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ProductUiState {
    data class Success(val product: Product) : ProductUiState
    data class Error(val message: String) : ProductUiState
    object Loading : ProductUiState
}

class ProductViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    init {
        fetchProductDetails()
    }

    private fun fetchProductDetails() {
        _uiState.value = ProductUiState.Loading
        viewModelScope.launch {
            try {
                val productData = RetrofitInstance.api.getProductDetail()
                _uiState.value = ProductUiState.Success(productData)
            } catch (e: Exception) {
                _uiState.value = ProductUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}