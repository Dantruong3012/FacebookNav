package com.dantruong.facebooknavpractice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dantruong.facebooknavpractice.data.Person

@Composable
fun NotificationScreen(navController: NavController) {
    val notifications = remember { Person.getPeople() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Tiêu đề
        Text(
            text = "Notifications",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            color = Color.Black
        )

        // Danh sách thông báo
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(notifications) { person ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Chạm vào thông báo sẽ có hiệu ứng nhấp nháy (ripple effect)
                        }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Avatar người dùng
                    Image(
                        painter = painterResource(id = person.resId),
                        contentDescription = person.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(68.dp)
                            .clip(CircleShape)
                    )
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    // Nội dung thông báo
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append(person.name)
                                }
                                append(" ")
                                append(person.text)
                            },
                            fontSize = 15.sp,
                            color = Color.Black,
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "A few moments ago", // Thời gian giả lập
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }
                    
                    // Nút 3 chấm (Options)
                    IconButton(onClick = { /* Không làm gì cả */ }) {
                        Icon(
                            imageVector = Icons.Default.MoreHoriz,
                            contentDescription = "Options",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }
    }
}