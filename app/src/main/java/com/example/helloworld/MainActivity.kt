package com.example.helloworld


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NoteTaking()
        }
    }
}

enum class SaveStatus {
    NOT_SAVED, SAVING, SAVED
}

@Composable
fun NoteTaking(){
    val notes = remember { mutableStateListOf("", "") }
    var noteIndexCurrent by remember { mutableIntStateOf(0) }

    var saveState = remember { mutableStateOf(SaveStatus.NOT_SAVED) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BasicTextField(
            value = notes[noteIndexCurrent],
            onValueChange = {
                notes[noteIndexCurrent] = it
            }
        )

        LaunchedEffect(notes[noteIndexCurrent]) {
            saveState.value = SaveStatus.SAVING
            delay(500)
            saveState.value = SaveStatus.SAVED
        }

        Text(
            text = "Status: " + when(saveState.value){
                SaveStatus.NOT_SAVED -> "Not Saved!"
                SaveStatus.SAVING -> "Saving"
                SaveStatus.SAVED -> "Saved."
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            noteIndexCurrent = 0
        }) { Text("Note 1")}

        Button(onClick = {
            noteIndexCurrent = 1
        }) { Text("Note 2")}
    }


}