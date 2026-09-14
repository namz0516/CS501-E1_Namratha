package com.namratha.mobilitylens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.namratha.mobilitylens.ui.theme.MobilityLensTheme

//Libs added
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.CardDefaults
import com.namratha.mobilitylens.ui.theme.AndroidGreen


//DEFINED LAYOUT PROPERTIES
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobilityLensTheme {
                MobilityLensApp()
            }
        }
    }
}




//ADDED
@Composable
fun MobilityLensApp(){
    var currentDimensionIndex by remember{
        mutableIntStateOf(0)
    }
    var applicationName by remember{
        mutableStateOf("")
    }
    var feedbackMessage by remember{
        mutableStateOf("")
    }
    var hasInputError by remember{
        mutableStateOf(false)
    }
    val currentDimension=mobDim[currentDimensionIndex]
    val successMessage = stringResource(
        R.string.success_message,
        applicationName.trim(),
        stringResource(currentDimension.name)
    )

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.app_title),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.introduction),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(
                    R.string.dimension_counter,
                    currentDimensionIndex + 1,
                    mobDim.size
                ),
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Text(
                        text = stringResource(currentDimension.name),
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.constraint_label),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = stringResource(currentDimension.desc),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.implication_label),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = stringResource(currentDimension.implic),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = {
                        currentDimensionIndex--
                        feedbackMessage = ""
                    },
                    enabled = currentDimensionIndex > 0
                ) {
                    Text(stringResource(R.string.previous))
                }

                Button(
                    onClick = {
                        currentDimensionIndex++
                        feedbackMessage = ""
                    },
                    enabled = currentDimensionIndex < mobDim.lastIndex
                ) {
                    Text(stringResource(R.string.next))
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            OutlinedTextField(
                value = applicationName,
                onValueChange = {
                    applicationName = it
                    hasInputError = false
                    feedbackMessage = ""
                },
                label = {
                    Text(stringResource(R.string.application_name_label))
                },
                supportingText = {
                    if (hasInputError) {
                        Text(stringResource(R.string.blank_input_error))
                    }
                },
                isError = hasInputError,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (applicationName.isBlank()) {
                        hasInputError = true
                        feedbackMessage = ""
                    } else {
                        hasInputError = false

                        feedbackMessage = successMessage
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.check_design))
            }

            if (feedbackMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = feedbackMessage,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}



/*@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}*/

@Preview(showBackground = true)
@Composable
fun MobilityLensPreview() {
    MobilityLensTheme {
        MobilityLensApp()
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun DarkPreview() {
    MobilityLensTheme(darkTheme = true) {
        MobilityLensApp()
    }
}

//ADDED

data class MobDim(@StringRes val name: Int, @StringRes val desc: Int, @StringRes val implic: Int)

//ADDED
private val mobDim=listOf(MobDim(R.string.dimension_input,R.string.input_description,R.string.input_implication),
    MobDim(R.string.dimension_screen,R.string.screen_description,R.string.screen_implication),
        MobDim(R.string.dimension_lifecycle, R.string.lifecycle_description, R.string.lifecycle_implication),
        MobDim(R.string.dimension_context, R.string.context_description, R.string.context_implication),
        MobDim(R.string.dimension_usage, R.string.usage_description, R.string.usage_implication),
        MobDim(R.string.dimension_security, R.string.security_description, R.string.security_implication))

