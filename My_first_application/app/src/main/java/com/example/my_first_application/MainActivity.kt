package com.example.my_first_application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.my_first_application.ui.theme.My_first_applicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageCard(Message("Android", "Jetpack Compose"))
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    // Add padding around our message
    Row (modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.img_0089),
            contentDescription = "Contact profile Picture",
            modifier = Modifier
                // Set image size to 40dp
                // Clip image to be shaped as a chircle
                .size(60.dp)
                // Clip image to be shaped as a cirlce
                .clip(CircleShape)
        )

        // Add horizontal space between the image and the cloumn
        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(text = msg.author)
            //Add a vertical Space between the author and message texts
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = msg.body)
        }
    }
}

@Preview
@Composable
fun PreviewMessageCard() {
    MessageCard(msg = Message("Colleque", "Hey, whats up"))
}

