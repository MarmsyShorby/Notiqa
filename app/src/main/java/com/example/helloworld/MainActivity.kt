package com.example.helloworld


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
    var contentText by remember { mutableStateOf("") }
    var titleText by remember { mutableStateOf("") }
    var savedText by remember { mutableStateOf("") }
    var savedTitle by remember { mutableStateOf("") }


    var saveState by remember { mutableStateOf(SaveStatus.NOT_SAVED) }


    //var noteIndexCurrent by remember {mutableStateOf(0)}
    //val notes = remember { mutableListOf("","") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {

        //tite text lmao
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ){
            if (titleText.isEmpty()){
                Text(
                    text = "Title...",
                    color = Color.Gray,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            BasicTextField(
                value = titleText,
                onValueChange = { titleText = it },
                textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                modifier = Modifier.fillMaxWidth(),
            )
        }

        //Main content text thing
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ){
            if (contentText.isEmpty()){
                Text(
                    text = "Enter Notes...",
                    color = Color.Gray
                )
            }

            BasicTextField(
                value = contentText,
                onValueChange = {
                    contentText = it
                    saveState = SaveStatus.SAVING },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        //Auto-Save stuff
        LaunchedEffect(titleText, contentText) {
            delay(500)
            savedText = contentText
            savedTitle = titleText
            saveState = SaveStatus.SAVED
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Status: " + when (saveState){
                SaveStatus.SAVED -> "Saved!"
                SaveStatus.SAVING -> "Saving..."
                SaveStatus.NOT_SAVED -> "Not Saved!"
            }
        )


        Spacer(modifier = Modifier.height(16.dp))

        //************DEBUGGING************
        Text(text = "Title: $savedTitle\n" +
                "Content: $savedText")
        //************DEBUGGING************

        Spacer(modifier = Modifier.height(16.dp))

    }
}