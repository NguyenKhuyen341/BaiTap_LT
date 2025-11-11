package com.example.el2.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.el2.R

// Định nghĩa các route (không đổi)
sealed class AuthScreen(val route: String) {
    object ForgetPassword : AuthScreen("forget_password")
    object Verification : AuthScreen("verification")
    object ResetPassword : AuthScreen("reset_password")
    object Confirm : AuthScreen("confirm")
}

// Composable chung (không đổi)
@Composable
fun CommonLogoHeader() {
    Image(
        painter = painterResource(id = R.drawable.logo_uth),
        contentDescription = "UTH Logo",
        modifier = Modifier.size(100.dp),
        contentScale = ContentScale.Fit
    )
    Text(
        text = "SmartTasks",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF0055A4)
    )
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
fun CommonAuthButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = text, fontSize = 18.sp)
    }
}

@Composable
fun AuthTopAppBar(onBackClick: () -> Unit) {
    IconButton(onClick = onBackClick) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

/**
 * Luồng chính chứa NavHost (không đổi)
 */
@Composable
fun ForgotPasswordFlow() {
    val navController = rememberNavController()
    val viewModel: AuthViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = AuthScreen.ForgetPassword.route
    ) {
        composable(AuthScreen.ForgetPassword.route) {
            ForgetPasswordScreen(navController = navController, viewModel = viewModel)
        }
        composable(AuthScreen.Verification.route) {
            VerificationScreen(navController = navController, viewModel = viewModel)
        }
        composable(AuthScreen.ResetPassword.route) {
            ResetPasswordScreen(navController = navController, viewModel = viewModel)
        }
        composable(AuthScreen.Confirm.route) {
            ConfirmScreen(navController = navController, viewModel = viewModel)
        }
    }
}

/**
 * Màn hình 1: Forget Password (không đổi)
 */
@Composable
fun ForgetPasswordScreen(navController: NavController, viewModel: AuthViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        CommonLogoHeader()

        Text(
            text = "Forget Password?",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Enter your Email, we will send you a verification code.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = { Text("Your Email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))

        CommonAuthButton(text = "Next") {
            navController.navigate(AuthScreen.Verification.route)
        }
    }
}

/**
 * Màn hình 2: Verification (CÓ CẬP NHẬT)
 */
@Composable
fun VerificationScreen(navController: NavController, viewModel: AuthViewModel) {
    val focusManager = LocalFocusManager.current
    val (focus1, focus2, focus3, focus4) = remember { FocusRequester.createRefs() }

    // CẬP NHẬT: Lấy trạng thái lỗi từ ViewModel
    val error = viewModel.verificationError

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthTopAppBar { navController.popBackStack() }
        Spacer(modifier = Modifier.height(30.dp))
        CommonLogoHeader()

        Text(
            text = "Verify Code",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Enter the the code we just sent you on your registered Email",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        // 4 ô nhập OTP
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OtpCell(value = viewModel.code1, onValueChange = {
                viewModel.code1 = it; if (it.isNotEmpty()) focus2.requestFocus()
                viewModel.verificationError = null // Xóa lỗi khi gõ
            }, modifier = Modifier.focusRequester(focus1), isError = error != null) // CẬP NHẬT

            OtpCell(value = viewModel.code2, onValueChange = {
                viewModel.code2 = it; if (it.isNotEmpty()) focus3.requestFocus()
                viewModel.verificationError = null
            }, modifier = Modifier.focusRequester(focus2), isError = error != null) // CẬP NHẬT

            OtpCell(value = viewModel.code3, onValueChange = {
                viewModel.code3 = it; if (it.isNotEmpty()) focus4.requestFocus()
                viewModel.verificationError = null
            }, modifier = Modifier.focusRequester(focus3), isError = error != null) // CẬP NHẬT

            OtpCell(value = viewModel.code4, onValueChange = {
                viewModel.code4 = it; if (it.isNotEmpty()) focusManager.clearFocus()
                viewModel.verificationError = null
            }, modifier = Modifier.focusRequester(focus4), isError = error != null) // CẬP NHẬT
        }

        // CẬP NHẬT: Hiển thị lỗi nếu có
        if (error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        CommonAuthButton(text = "Next") {
            // CẬP NHẬT: Gọi hàm kiểm tra từ ViewModel
            if (viewModel.verifyOtp()) {
                navController.navigate(AuthScreen.ResetPassword.route)
            }
        }
    }
}

// Ô nhập OTP (CÓ CẬP NHẬT)
@Composable
fun OtpCell(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean // CẬP NHẬT: Thêm trạng thái lỗi
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            if (it.length <= 1) {
                onValueChange(it)
            }
        },
        modifier = modifier
            .size(60.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        textStyle = LocalTextStyle.current.copy(
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        isError = isError // CẬP NHẬT: Áp dụng trạng thái lỗi
    )
}

/**
 * Màn hình 3: Reset Password (CÓ CẬP NHẬT)
 */
@Composable
fun ResetPasswordScreen(navController: NavController, viewModel: AuthViewModel) {
    // CẬP NHẬT: Lấy trạng thái lỗi từ ViewModel
    val error = viewModel.passwordResetError

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthTopAppBar { navController.popBackStack() }
        Spacer(modifier = Modifier.height(30.dp))
        CommonLogoHeader()

        Text(
            text = "Create new password",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Your new password must be different form previously used password",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Password
        OutlinedTextField(
            value = viewModel.newPassword,
            onValueChange = {
                viewModel.newPassword = it
                viewModel.passwordResetError = null // Xóa lỗi khi gõ
            },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            isError = error != null // CẬP NHẬT: Hiển thị viền đỏ nếu lỗi
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password
        OutlinedTextField(
            value = viewModel.confirmNewPassword,
            onValueChange = {
                viewModel.confirmNewPassword = it
                viewModel.passwordResetError = null // Xóa lỗi khi gõ
            },
            label = { Text("Confirm Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            isError = error != null // CẬP NHẬT: Hiển thị viền đỏ nếu lỗi
        )

        // CẬP NHẬT: Hiển thị thông báo lỗi
        if (error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        CommonAuthButton(text = "Next") {
            // CẬP NHẬT: Gọi hàm kiểm tra từ ViewModel
            if (viewModel.validateNewPassword()) {
                navController.navigate(AuthScreen.Confirm.route)
            }
        }
    }
}

/**
 * Màn hình 4: Confirm (không đổi)
 */
@Composable
fun ConfirmScreen(navController: NavController, viewModel: AuthViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AuthTopAppBar { navController.popBackStack() }
        Spacer(modifier = Modifier.height(30.dp))
        CommonLogoHeader()

        Text(
            text = "Confirm",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "We are here to help you!",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = viewModel.confirmEmail,
            onValueChange = { viewModel.confirmEmail = it },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") },
            placeholder = { Text("uth@gmail.com") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.confirmField2,
            onValueChange = { viewModel.confirmField2 = it },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Field 2") },
            placeholder = { Text("123456") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.confirmPassword,
            onValueChange = { viewModel.confirmPassword = it },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password") },
            placeholder = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))

        CommonAuthButton(text = "Submit") {
            // TODO: Xử lý logic đăng nhập
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewForgetPasswordScreen() {
    ForgetPasswordScreen(rememberNavController(), viewModel())
}