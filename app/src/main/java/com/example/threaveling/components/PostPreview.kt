package com.example.threaveling.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.threaveling.datasource.UserDataSource
import com.example.threaveling.datasource.collectPerfilPics
import com.example.threaveling.models.CarouselItem
import com.example.threaveling.models.Threavel
import com.example.threaveling.ui.theme.WHITE
import kotlinx.coroutines.runBlocking


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostPreview(navControllerPost : () -> Unit, items: List<CarouselItem> = emptyList(), navControllerImages :() -> Unit = {}, post: Threavel?){
    val userDB = UserDataSource()
    val imageCache = mutableMapOf<String, String?>()
    if(post!!.destiny != ""){
        val perfilImage = collectPerfilPics(post.userId)

        Box(
            modifier = Modifier
                .background(color = WHITE)
                .fillMaxWidth()
                .clickable(
                    onClick = {
                        navControllerPost()
                    }
                )
        ) {
            AsyncImage(
                model = if(imageCache.containsKey(post.userId)) imageCache[post.userId] else {
                    imageCache[post.userId] = perfilImage
                    perfilImage
                },
                contentDescription = "Imagem de perfil",
                modifier = Modifier
                    .size(40.dp, 40.dp)
                    .padding(5.dp)
                    //.align(Alignment.Top)
            )
            Column{
                Text(text = runBlocking{userDB.getUserById(post.userId)!!.getUsername()},
                    modifier = Modifier
                        .padding(start = 50.dp,top = 5.dp),
                    fontSize = TextUnit(20F, TextUnitType.Sp),
                    fontWeight = FontWeight.Bold)
                Text(
                    text = "Minha viagem para ${post.destiny}",
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .padding(start = 50.dp, bottom = 10.dp),
                    fontSize = TextUnit(20F, TextUnitType.Sp),
                    fontWeight = FontWeight.Bold
                )
            }

            Column(modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
                ) {

                Row {
                    Text(
                        text = post.introduction,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(top = 35.dp),
                        fontSize = TextUnit(20F, TextUnitType.Sp)
                    )
                }
                if (items.isNotEmpty()) {
                    HorizontalMultiBrowseCarousel(
                        state = rememberCarouselState { items.count() },
                        modifier = Modifier
                            .width(500.dp)
                            .height(221.dp),
                        preferredItemWidth = 186.dp,
                        itemSpacing = 8.dp,
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) { index ->

                        val item = items[index]
                        AsyncImage(modifier = Modifier
                            .height(205.dp)
                            .maskClip(MaterialTheme.shapes.extraLarge)
                            .clickable(
                                onClick = {
                                    navControllerImages()
                                }
                            ),
                            model = item.imageUrl,
                            contentDescription = item.contentDescriptionResId,
                            contentScale = ContentScale.Crop
                        )
                    }

                }

            }
            HorizontalDivider()
        }
    }
}