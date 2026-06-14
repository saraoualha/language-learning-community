package com.example.tandem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.example.tandem.domain.Member

/**
 * Main screen that displays the paginated list of community members.
 */
@Composable
fun CommunityScreen(viewModel: CommunityViewModel) {
    val members = viewModel.members.collectAsLazyPagingItems()

    Column(modifier = Modifier.Companion.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Community",
            fontSize = 24.sp,
            fontWeight = FontWeight.Companion.Bold,
            modifier = Modifier.Companion.padding(bottom = 16.dp)
        )
        LazyColumn {
            items(
                count = members.itemCount,
                key = members.itemKey { it.id }
            ) { index ->
                val member = members[index]
                if (member != null) {
                    MemberCard(
                        member = member,
                        onLikeClicked = { viewModel.onLikeClicked(member.id) }
                    )
                }
            }
        }
    }
}

/**
 * A single community member card showing profile info and like button.
 */
@Composable
fun MemberCard(
    member: Member,
    onLikeClicked: () -> Unit
) {
    Card(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Row(modifier = Modifier.Companion.padding(12.dp)) {

            // profile picture
            AsyncImage(
                model = member.pictureUrl,
                contentDescription = "${member.firstName}'s profile picture",
                modifier = Modifier.Companion
                    .size(90.dp)
                    .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.Companion.width(12.dp))

            Column(modifier = Modifier.Companion.weight(1f)) {

                // name + badge row
                Row(
                    modifier = Modifier.Companion.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Companion.CenterVertically
                ) {
                    Text(
                        text = member.firstName,
                        fontWeight = FontWeight.Companion.Bold,
                        fontSize = 16.sp
                    )
                    if (member.isNew) {
                        NewBadge()
                    } else {
                        Text(
                            text = member.referenceCnt.toString(),
                            fontSize = 14.sp,
                            color = Color.Companion.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.Companion.height(4.dp))

                // topic
                Text(
                    text = member.topic,
                    fontSize = 13.sp,
                    color = Color.Companion.DarkGray
                )

                Spacer(modifier = Modifier.Companion.height(8.dp))

                // languages + like button
                Row(
                    modifier = Modifier.Companion.fillMaxWidth(),
                    verticalAlignment = Alignment.Companion.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        LanguageChip(label = "NATIVE", codes = member.natives)
                        Spacer(modifier = Modifier.Companion.width(8.dp))
                        LanguageChip(label = "LEARNS", codes = member.learns)
                    }
                    IconButton(onClick = onLikeClicked) {
                        Text(
                            text = if (member.isLiked) "👍🎉" else "👍",
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}

/**
 * The NEW badge shown for members with no references yet.
 */
@Composable
fun NewBadge() {
    Surface(
        shape = androidx.compose.foundation.shape.RoundedCornerShape(50),
        color = Color(0xFF4DD0C4)
    ) {
        Text(
            text = "NEW",
            modifier = Modifier.Companion.padding(horizontal = 10.dp, vertical = 4.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Companion.Bold,
            color = Color.Companion.White
        )
    }
}

/**
 * Displays a language label and its associated language codes.
 */
@Composable
fun LanguageChip(label: String, codes: List<String>) {
    Row(verticalAlignment = Alignment.Companion.CenterVertically) {
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Companion.Bold,
            color = Color.Companion.Gray
        )
        Spacer(modifier = Modifier.Companion.width(4.dp))
        Text(
            text = codes.joinToString(" ").uppercase(),
            fontSize = 10.sp,
            color = Color.Companion.Gray
        )
    }
}