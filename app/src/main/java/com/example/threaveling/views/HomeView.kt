package com.example.threaveling.views

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.cloudinary.transformation.Format
import com.cloudinary.transformation.RoundCorners
import com.cloudinary.transformation.delivery.Delivery
import com.cloudinary.transformation.resize.Resize
import com.example.threaveling.firebaseAuthentication.FirebaseAuthentication
import com.example.threaveling.R
import com.example.threaveling.cache.GeneralLoad
import com.example.threaveling.cache.Post
import com.example.threaveling.cloudinary.CloudinaryConfig.cloudinary
import com.example.threaveling.components.BottomBarApp
import com.example.threaveling.components.PostPreview
import com.example.threaveling.components.TopBarApp
import com.example.threaveling.models.CarouselItem
import com.example.threaveling.models.MenuItem
import com.example.threaveling.repositories.ThreavelRepository
import com.example.threaveling.ui.theme.LIGHT_BLUE
import com.example.threaveling.ui.theme.WHITE

const val TAG = "HomeView"

@Composable
fun HomeView(navController:NavController){
    val imageCache = GeneralLoad
    val state = remember { mutableStateOf(false)  }
    val scope = rememberCoroutineScope()
    val items = listOf(
        MenuItem(
            title = "Logout",
            onClick = {
                FirebaseAuthentication.out()
                navController.navigate("Login")

            }
        )
    )

    Scaffold(
        containerColor = WHITE,
        topBar = {
            TopBarApp(title = "Threaveling",
                hasBack = false,
                hasOption = true,
                hasOptionIcon = @Composable {
                    AsyncImage(
                        model = imageCache.getProfilePic(),
                        contentDescription = "Imagem de perfil",
                    )
                },
                isExpanded = state,
                menuItems = items
                )},
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .padding(10.dp),
                onClick = { navController.navigate("SelectTravel") },
                containerColor = LIGHT_BLUE,
                contentColor = WHITE
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_create_24),
                    contentDescription = "Create new post"
                )
            }
        },
        bottomBar = {
        BottomBarApp(focusHome = LIGHT_BLUE,
            onClickHome = { navController.navigate("Home") })
        }
    ) { innerpadding ->
        Column(
            Modifier
                .padding(innerpadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())) {

                ShowPosts(navController)
        }

    }
}

@Composable
private fun ShowPosts(navController: NavController){
    val postsRepository = ThreavelRepository()
    val  postsFlow = remember{postsRepository.getRecentPosts()}
    val postsList by postsFlow.collectAsState(initial = emptyList())

    if(postsList.isNotEmpty()){
        postsList.forEach { post ->
            val carouselItems: MutableList<CarouselItem> = mutableListOf()
            if (post.picsNumber > 0) {
                for (i in 0..post.picsNumber) {
                    carouselItems += CarouselItem(cloudinary.image {
                        publicId("${post.userId}/${post.id}_${i}")
                        resize(Resize.thumbnail() {
                            width(500)
                            height(221)
                        })
                        roundCorners(RoundCorners.byRadius(5))
                        delivery(
                            Delivery.format(
                                Format.jpg()
                            )
                        )
                    }.generate(), "Imagem ${i + 1} da viagem")
                }
            }

            PostPreview(
                {
                    if(post.id != Post.getPost().id){
                    Post.setPost(post)
                    }
                    navController.navigate("ExpandedPostView")
                },
                items = carouselItems,
                post = post)
            Log.d(TAG, "Posts: $post")
        }
    }
}

@Composable
@Preview
private fun FeedViewPreview(){
    val navController = rememberNavController()
    HomeView(navController)
}