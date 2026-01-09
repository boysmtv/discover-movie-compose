package com.mtv.app.movie.feature.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mtv.based.core.network.firebase.result.FirebaseResult
import com.mtv.based.core.network.utils.Resource
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.DialogCenterV1
import com.mtv.based.uicomponent.core.component.dialog.dialogv1.ErrorDialogStateV1

@Composable
fun RegisterRoute(
    navController: NavController,
    registerViewModel: RegisterViewModel = hiltViewModel(),
) {
    val registerState by registerViewModel.registerState.collectAsState()
    val baseUiState by registerViewModel.baseUiState.collectAsState()

    baseUiState.errorDialog?.let {
        DialogCenterV1(
            state = it,
            onDismiss = registerViewModel::dismissError
        )
    }

    RegisterScreen(
        registerState = registerState,
        onRegisterClick = { name, email, phone, password ->
            registerViewModel.doRegister(
                name, email, phone, password
            )
        },
        onRegisterSuccess = {
            navController.navigate("login") {
                popUpTo("login") { inclusive = true }
            }
        }
    )
}

@Composable
fun RegisterScreen(
    registerState: FirebaseResult<String>,
    onRegisterClick: (String, String, String, String) -> Unit,
    onRegisterSuccess: () -> Unit,
) {

    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val nameError = remember { mutableStateOf(false) }
    val emailError = remember { mutableStateOf(false) }
    val phoneError = remember { mutableStateOf(false) }
    val passwordError = remember { mutableStateOf(false) }

    val showPassword = remember { mutableStateOf(false) }
    val showSuccessDialog = remember { mutableStateOf(false) }

    // Handle register success
    LaunchedEffect(registerState) {
        if (registerState is FirebaseResult.Success) {
            showSuccessDialog.value = true
        }

        name.value = "Boys"
        email.value = "Boys.mtv@gmail.com"
        phone.value = "081234567890"
        password.value = "Mbi123456."
    }

    fun validate(): Boolean {
        nameError.value = name.value.isBlank()
        emailError.value = !email.value.contains("@")
        phoneError.value = phone.value.length < 8
        passwordError.value = password.value.length < 6

        return !(nameError.value || emailError.value || phoneError.value || passwordError.value)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        if (showSuccessDialog.value) {
            DialogCenterV1(
                state = ErrorDialogStateV1(
                    title = "Success",
                    message = "Register Successfully",
                    primaryButtonText = "OK"
                ),
                onDismiss = {
                    showSuccessDialog.value = false
                    onRegisterSuccess()
                }
            )
        }

        Text(
            text = "Register",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = name.value,
            onValueChange = {
                nameError.value = false
                name.value = it
            },
            label = { Text("Name") },
            isError = nameError.value,
            modifier = Modifier.fillMaxWidth()
        )

        if (nameError.value) {
            Text("Name cannot be empty", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // EMAIL
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                emailError.value = false
                email.value = it
            },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = emailError.value,
            modifier = Modifier.fillMaxWidth()
        )

        if (emailError.value) {
            Text("Invalid email", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // PHONE
        OutlinedTextField(
            value = phone.value,
            onValueChange = {
                phoneError.value = false
                phone.value = it
            },
            label = { Text("Phone") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isError = phoneError.value,
            modifier = Modifier.fillMaxWidth()
        )

        if (phoneError.value) {
            Text("Phone must be at least 8 digits", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // PASSWORD
        OutlinedTextField(
            value = password.value,
            onValueChange = {
                passwordError.value = false
                password.value = it
            },
            label = { Text("Password") },
            visualTransformation = if (showPassword.value)
                VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { showPassword.value = !showPassword.value }) {
                    Icon(
                        imageVector = if (showPassword.value)
                            Icons.Default.VisibilityOff
                        else Icons.Default.Visibility,
                        contentDescription = null
                    )
                }
            },
            isError = passwordError.value,
            modifier = Modifier.fillMaxWidth()
        )

        if (passwordError.value) {
            Text("Password must be at least 6 characters", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (validate()) {
                    onRegisterClick(name.value, email.value, phone.value, password.value)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("Register")
        }

    }
}
