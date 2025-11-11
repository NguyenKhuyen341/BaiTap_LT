package com.example.el2.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class AuthViewModel : ViewModel() {
    // Màn hình 1: Quên mật khẩu
    var email by mutableStateOf("")

    // Màn hình 2: Verify Code
    var code1 by mutableStateOf("")
    var code2 by mutableStateOf("")
    var code3 by mutableStateOf("")
    var code4 by mutableStateOf("")

    // MỚI: Trạng thái lỗi cho màn hình Verify Code
    var verificationError by mutableStateOf<String?>(null)

    // Màn hình 3: Đặt lại mật khẩu
    var newPassword by mutableStateOf("")
    var confirmNewPassword by mutableStateOf("")

    // MỚI: Trạng thái lỗi cho màn hình Reset Password
    var passwordResetError by mutableStateOf<String?>(null)

    // Màn hình 4: Confirm
    var confirmEmail by mutableStateOf("")
    var confirmField2 by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    // MỚI: Hàm kiểm tra mã OTP
    fun verifyOtp(): Boolean {
        val enteredOtp = code1 + code2 + code3 + code4

        // Giả lập: Mã OTP đúng là "1234"
        val correctOtp = "1234"

        return if (enteredOtp == correctOtp) {
            verificationError = null // Xóa lỗi nếu đúng
            true // Trả về true để điều hướng
        } else {
            verificationError = "Mã code không hợp lệ." // Set lỗi
            false // Trả về false
        }
    }

    // MỚI: Hàm kiểm tra mật khẩu mới
    fun validateNewPassword(): Boolean {
        if (newPassword.isBlank() || confirmNewPassword.isBlank()) {
            passwordResetError = "Vui lòng nhập đầy đủ mật khẩu."
            return false
        }
        if (newPassword != confirmNewPassword) {
            passwordResetError = "Hai mật khẩu không khớp."
            return false
        }
        if (newPassword.length < 6) {
            passwordResetError = "Mật khẩu phải có ít nhất 6 ký tự."
            return false
        }

        passwordResetError = null // Xóa lỗi nếu mọi thứ OK
        return true // Trả về true để điều hướng
    }
}