package com.example.marcosduran_p2_ap2.presentation.viajes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ViajeScreen(
    viewModel: ViajeViewModel = hiltViewModel(),
    viajeId: Int?,
    goBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val viaje = state.viaje

    val isEditing = viajeId != null && viajeId > 0

    LaunchedEffect (viajeId) {
        if (isEditing) {
            viewModel.onEvent(ViajeEvent.FindById(viajeId!!))
        }
    }

    LaunchedEffect(state.successMessage) {
        if (state.successMessage != null) {
            kotlinx.coroutines.delay(1500)
            goBack()
        }
    }

    Card  (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Column  (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = goBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver"
                    )
                }

                Text(
                    text = if (isEditing) "Editar viaje" else "Registro de viajes",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(48.dp))
            }

            state.successMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(8.dp)
                )
            }

            state.errorMessage?.let { errorMsg ->
                if (isEditing) {
                    Text(
                        text = errorMsg,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            OutlinedTextField(
                value = viaje.millas?.takeIf { it != 0 }?.toString() ?: "",
                onValueChange = {
                    it.toIntOrNull()?.let { idCuenta ->
                        viewModel.onEvent(ViajeEvent.millasChange(millas)
                    }
                },
                label = { Text(text = "Millas: ") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
            )

            ViajeDateTimeDisplay(
                viajeDate = viaje.fecha ?: getCurrentDateISO()
            )

            OutlinedTextField(
                value = viaje.observacion,
                onValueChange = { viewModel.onEvent(ViajeEvent.ObsercacionesChange(it)) },
                label = { Text(text = "Observacion") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),

                )

            OutlinedTextField(
                value = viaje.monto.toString(),
                onValueChange = {
                    it.toDoubleOrNull()?.let { monto ->
                        viewModel.onEvent(ViajeEvent().MontoChange(monto))
                    }
                },
                label = { Text(text = "Monto") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
            )

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp, 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button (
                    onClick = {
                        viewModel.onEvent(ViajeEvent().Save)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (isEditing) Icons.Default.Edit else Icons.Default.Add,
                            contentDescription = if (isEditing) "Actualizar" else "Guardar"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(if (isEditing) "Actualizar" else "Guardar")
                    }
                }

                Button (
                    onClick = {
                        viewModel.onEvent(ViajeEvent.New)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Limpiar")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Limpiar")
                    }
                }
            }
        }
    }
}
