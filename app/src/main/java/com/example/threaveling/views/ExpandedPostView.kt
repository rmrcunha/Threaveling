package com.example.threaveling.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.cloudinary.transformation.Format
import com.cloudinary.transformation.RoundCorners
import com.cloudinary.transformation.delivery.Delivery
import com.example.threaveling.cache.Post
import com.example.threaveling.cloudinary.CloudinaryConfig.cloudinary
import com.example.threaveling.components.TopBarApp
import com.example.threaveling.datasource.UserDataSource
import com.example.threaveling.datasource.collectPerfilPics
import com.example.threaveling.models.CarouselItem
import com.example.threaveling.ui.theme.BLACK
import com.example.threaveling.ui.theme.WHITE
import kotlinx.coroutines.runBlocking


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandedPostView(navController:NavController){
    val post = Post.getPost()
    val userDB = UserDataSource()
    val carouselItems: MutableList<CarouselItem> = mutableListOf()
    Scaffold(
        containerColor = WHITE,
        topBar = {
            TopBarApp(title = "My travel to ${post.destiny}",
                hasBack = true,
                onClickBack = {navController.navigate("Home")},
                hasOption = false
            )
        }

    ) { innerpadding ->
        Column(modifier = Modifier
            .padding(innerpadding)
            .verticalScroll(rememberScrollState())){
            Row(modifier = Modifier.padding(10.dp)){
                AsyncImage(
                    model = collectPerfilPics(post.userId),
                    contentDescription = "Imagem de perfil",
                    modifier = Modifier
                        .size(45.dp)
                )
                Text(
                    text = runBlocking{ userDB.getUserById(post.userId)!!.getUsername() },
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(10.dp),
                    fontSize = TextUnit(18F, TextUnitType.Sp)
                )
            }
            Text(
                text = post.introduction,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 5.dp),
                fontSize = TextUnit(15F, TextUnitType.Sp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = post.detailedDescription,
                modifier = Modifier
                    .padding(10.dp),
                fontSize = TextUnit(15F, TextUnitType.Sp)
            )
            if(post.picsNumber > 0){

                for (i in 0..<post.picsNumber) {
                    carouselItems += CarouselItem(cloudinary.image {
                        publicId("${post.userId}/${post.id}_${i}")
                        roundCorners(RoundCorners.byRadius(5))
                        delivery(
                            Delivery.format(
                                Format.jpg()
                            )
                        )
                    }.generate(), "Imagem ${i + 1} da viagem")
                }

                HorizontalMultiBrowseCarousel(
                    state = rememberCarouselState { carouselItems.count() },
                    modifier = Modifier
                        .height(720.dp),
                    preferredItemWidth = 480.dp,
                    itemSpacing = 8.dp,
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) { index ->

                    val item = carouselItems[index]
                    AsyncImage(modifier = Modifier
                        .height(720.dp)
                        .width(1280.dp)
                        .maskClip(MaterialTheme.shapes.extraLarge)
                        .clickable(
                            onClick = {}
                        ),
                        model = item.imageUrl,
                        contentDescription = item.contentDescriptionResId,
                        contentScale = ContentScale.Crop
                    )
                }
            }
            HorizontalDivider(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))

            Text(text = "Estadia",
                modifier = Modifier
                    .padding(start = 10.dp, bottom = 5.dp)
                    .align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold)

            Text(text = post.stayDescription,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp))

            HorizontalDivider(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))

            Row(){
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 10.dp),
                    colors = IconButtonColors(
                        containerColor = WHITE, contentColor = Color.Red,
                        disabledContentColor = BLACK, disabledContainerColor = WHITE
                    )
                ) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Like")
                }
            }

        }

    }
}