package com.outivox.myabc.ui.presentation.setting.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.outivox.myabc.R
import com.outivox.myabc.ui.organisms.HeaderPage
import com.outivox.myabc.ui.organisms.PrimaryButton

// =============================================================================
// TODO (Step 7.4): [SettingDetailScreen Composable Implementation]
// 1. Observe StateFlow from SettingDetailViewModel using `collectAsStateWithLifecycle()`.
// 2. Dispatch `LaunchedEffect(settingTitle)` to load detail data on entry.
// 3. Handle UDF: pass state down to text fields, emit `SettingDetailEvent.UpdateField` up.
// 4. Show feedback snackbar using `SnackbarHost(snackbarHostState)` and `LaunchedEffect(state.userMessage)`.
// =============================================================================
@Composable
fun SettingDetailScreen(
    settingTitle: String,
    onBackPressed: () -> Unit = {},
    viewModel: SettingDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(settingTitle) {
        viewModel.onEvent(SettingDetailEvent.LoadDetail(settingTitle))
    }

    LaunchedEffect(state.userMessage) {
        state.userMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.onEvent(SettingDetailEvent.ClearMessage)
        }
    }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        containerColor = Color.White,
        topBar = {
            HeaderPage(
                title = state.title.ifEmpty { settingTitle },
                leadingIconRes = R.drawable.ic_arrow_left,
                onClickLeadingIcon = onBackPressed,
            )
        },
        bottomBar = {
            PrimaryButton(
                modifier = Modifier.padding(16.dp),
                text = "Save Changes",
                onClick = {
                    viewModel.onEvent(SettingDetailEvent.SaveChanges)
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    if (state.description.isNotEmpty()) {
                        Text(
                            text = state.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    state.fields.forEach { (key, value) ->
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = value,
                            onValueChange = { updated ->
                                viewModel.onEvent(SettingDetailEvent.UpdateField(key, updated))
                            },
                            label = { Text(key) },
                            singleLine = true,
                        )
                    }
                }
            }
        }
    }

    BackHandler {
        onBackPressed()
    }
}

@Preview
@Composable
private fun SettingDetailScreenPreview() {
    SettingDetailScreen(
        settingTitle = "Contact Details",
    )
}
