package com.example.marcosduran_p2_ap2.presentation.viajes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.marcosduran_p2_ap2.data.remote.dto.ViajeDto

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ViajeListScreen(
    viewModel: ViajeViewModel = hiltViewModel(),
    goToViaje: (Int) -> Unit,
    createViaje: () -> Unit
){
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect (Unit) {
        viewModel.onEvent(ViajeEvent.LoadViajes)
    }

    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage != null) {
            kotlinx.coroutines.delay(500)
            viewModel.onEvent(ViajeEvent.LoadViajes)
        }
    }

    ViajeListBodyScreen(
        uiState = uiState,
        goToViaje = goToViaje,
        createViaje = createViaje
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViajeListBodyScreen(
    viewModel: ViajeViewModel = hiltViewModel(),
    uiState: ViajeUiState,
    goToViaje: (Int) -> Unit,
    createViaje: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Lista de Viajes",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                actions = {
                    IconButton (
                        onClick = {
                            viewModel.onEvent(ViajeEvent.LoadViajes)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Actualizar lista"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton (onClick = createViaje) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar nuevo viaje")
            }
        }
    ) { padding ->

        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.viajes.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No hay viajes registrados",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Toca el botón + para agregar un nuevo viaje",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        } else {
            val lazyListState = rememberLazyListState()

            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn (
                    state = lazyListState,
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = padding.calculateTopPadding() + 16.dp,
                        bottom = padding.calculateBottomPadding() + 80.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        uiState.errorMessage?.let { error ->
                            Card (
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.errorContainer
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = error,
                                    modifier = Modifier.padding(16.dp),
                                    color = MaterialTheme.colorScheme.onErrorContainer
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

                    items(
                        item    = uiState.viajes,
                        key = { viajes -> viajes.idViaje ?: 0 }
                    ) { viaje ->
                        ViajeRow(
                            viaje = viaje,
                            onEdit = { it -> // Recibe el viajedto completo
                                goToViaje(it.idViaje ?: 0) // Extrae el ID aquí
                            },
                            onDelete = {
                                viewModel.onEvent(ViajeEvent().Delete(viaje))
                            }
                        )
                    }
                }

                LazyColumnScrollbar(
                    listState = lazyListState,
                    modifier = Modifier
                        .fillMaxHeight()
                        .align(Alignment.CenterEnd)
                )
            }
        }
    }
}

@Composable
fun LazyColumnScrollbar(
    listState: LazyListState,
    modifier: Modifier = Modifier,
    thumbColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
    trackColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
    thumbWidth: Dp = 4.dp,
    trackWidth: Dp = 2.dp
) {
    val firstVisibleItemIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val layoutInfo by remember { derivedStateOf { listState.layoutInfo } }

    if (layoutInfo.totalItemsCount == 0) return

    val totalItems = layoutInfo.totalItemsCount
    val visibleItems = layoutInfo.visibleItemsInfo.size

    if (totalItems <= visibleItems) return

    Box(
        modifier = modifier
            .width(8.dp)
            .padding(end = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(trackWidth)
                .background(
                    color = trackColor,
                    shape = RoundedCornerShape(trackWidth / 2)
                )
                .align(Alignment.Center)
        )

        val thumbHeight = maxOf(
            (visibleItems.toFloat() / totalItems) * 0.8f,
            0.1f
        )

        val thumbPosition = (firstVisibleItemIndex.toFloat() / (totalItems - visibleItems).coerceAtLeast(1)) * (1f - thumbHeight)

        val offsetY = remember(thumbPosition) {
            (thumbPosition * 400).dp
        }

        Box(
            modifier = Modifier
                .fillMaxHeight(thumbHeight)
                .width(thumbWidth)
                .offset(y = offsetY)
                .background(
                    color = thumbColor,
                    shape = RoundedCornerShape(thumbWidth / 2)
                )
                .align(Alignment.TopCenter)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ViajeRow(
    viaje: ViajeDto,
    onEdit: (ViajeDto) -> Unit,
    onDelete: (ViajeDto) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row  (verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "ID: ${viaje.idViaje}",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.weight(1f)
                )
                viaje.fecha?.let {
                    ViajeDateCompact(depositDate = it.toString())
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Cuenta: ${viaje.millas}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Observacion: ${viaje.observaciones}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Monto: $${String.format("%.2f", viaje.monto)}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { onEdit(viaje) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar Depósito",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(onClick = {
                    onDelete(viaje)
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar viaje",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

