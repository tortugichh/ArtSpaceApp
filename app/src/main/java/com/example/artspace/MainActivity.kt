package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ArtSpaceScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ArtSpaceScreen(modifier: Modifier = Modifier) {
    var artworkIndex by rememberSaveable { mutableIntStateOf(1) }
    val artworkImage = when (artworkIndex) {
        1 -> R.drawable.art1
        2 -> R.drawable.art2
        else -> R.drawable.art3
    }
    val artworkTitle = when (artworkIndex) {
        1 -> R.string.art1_title
        2 -> R.string.art2_title
        else -> R.string.art3_title
    }
    val artworkDescription = when (artworkIndex) {
        1 -> R.string.art1_description
        2 -> R.string.art2_description
        else -> R.string.art3_description
    }

    BoxWithConstraints(modifier = modifier.fillMaxSize().padding(16.dp)) {
        if (maxWidth > maxHeight) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ArtworkFrame(
                    imageRes = artworkImage,
                    description = stringResource(artworkDescription),
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
                Spacer(modifier = Modifier.width(24.dp))
                ArtworkPanel(
                    title = stringResource(artworkTitle),
                    onPrevious = { artworkIndex = if (artworkIndex == 1) 3 else artworkIndex - 1 },
                    onNext = { artworkIndex = if (artworkIndex == 3) 1 else artworkIndex + 1 },
                    modifier = Modifier.weight(1f)
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ArtworkFrame(
                    imageRes = artworkImage,
                    description = stringResource(artworkDescription),
                    modifier = Modifier.weight(1f).fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
                ArtworkPanel(
                    title = stringResource(artworkTitle),
                    onPrevious = { artworkIndex = if (artworkIndex == 1) 3 else artworkIndex - 1 },
                    onNext = { artworkIndex = if (artworkIndex == 3) 1 else artworkIndex + 1 }
                )
            }
        }
    }
}

@Composable
fun ArtworkFrame(imageRes: Int, description: String, modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.Center) {
        val artworkSize = (minOf(maxWidth, maxHeight) - 32.dp).coerceAtLeast(0.dp)
        Surface(
            modifier = Modifier.size(artworkSize),
            shadowElevation = 8.dp,
            color = Color.White
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = description,
                modifier = Modifier.fillMaxSize().padding(12.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun ArtworkPanel(
    title: String,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        ArtworkDetails(
            title = title,
            artist = stringResource(R.string.unknown_artist),
            year = stringResource(R.string.unknown_year)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = onPrevious, modifier = Modifier.weight(1f)) {
                Text(stringResource(R.string.previous))
            }
            Spacer(modifier = Modifier.width(24.dp))
            Button(onClick = onNext, modifier = Modifier.weight(1f)) {
                Text(stringResource(R.string.next))
            }
        }
    }
}

@Composable
fun ArtworkDetails(title: String, artist: String, year: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.headlineSmall)
            Text(text = "$artist · $year", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme {
        ArtSpaceScreen()
    }
}
