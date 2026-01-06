package com.mtv.app.movie.feature.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mtv.app.core.provider.based.BaseUiState
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
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    )
}

@Composable
fun RegisterScreen(
    registerState: Resource<Any>,
    onRegisterClick: (String, String, String, String) -> Unit,
    onRegisterSuccess: () -> Unit,
) {
    val context = LocalContext.current

    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val showSuccessDialog = remember { mutableStateOf(false) }


    LaunchedEffect(registerState) {
        if (registerState is Resource.Success) {
            showSuccessDialog.value = true
        }
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
                    primaryButtonText = "OK",
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
            onValueChange = { name.value = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = phone.value,
            onValueChange = { phone.value = it },
            label = { Text("Phone") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onRegisterClick(name.value, email.value, phone.value, password.value)
            }
        ) { }
    }
}