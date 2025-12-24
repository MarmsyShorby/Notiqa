package com.example.helloworld


import android.R
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

data class Note(
    val title: String,
    val content: String
)

@Composable
fun NoteTaking(){
    val notes = remember { mutableStateListOf(
        Note("", ""),
        Note("", "")
    ) }
    var noteIndexCurrent by remember { mutableIntStateOf(0) }

    val saveState = remember { mutableStateOf(SaveStatus.NOT_SAVED) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(//Title
            modifier = Modifier
                .fillMaxWidth()
        ){
            if(notes[noteIndexCurrent].title.isEmpty()){
                Text("Write Title", color = Color.LightGray, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }


            BasicTextField(
                value = notes[noteIndexCurrent].title,
                onValueChange = {
                    notes[noteIndexCurrent] = notes[noteIndexCurrent].copy(title = it)
                },
                textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
            )
        }


        Spacer(modifier = Modifier.height(16.dp))



        Box(//Content/Body
            modifier = Modifier
                .fillMaxWidth()
        ){
            if(notes[noteIndexCurrent].content.isEmpty()){
                Text("Write Notes", color = Color.LightGray)
            }


            BasicTextField(
                value = notes[noteIndexCurrent].content,
                onValueChange = {
                    notes[noteIndexCurrent] = notes[noteIndexCurrent].copy(content = it)
                }
            )
        }


        Spacer(modifier = Modifier.height(16.dp))


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