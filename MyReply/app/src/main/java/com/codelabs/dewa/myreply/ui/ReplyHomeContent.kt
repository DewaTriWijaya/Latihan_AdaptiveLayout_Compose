package com.codelabs.dewa.myreply.ui

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.codelabs.dewa.myreply.Greeting
import com.codelabs.dewa.myreply.R
import com.codelabs.dewa.myreply.data.Email
import com.codelabs.dewa.myreply.data.local.LocalAccountsDataProvider
import com.codelabs.dewa.myreply.ui.theme.MyReplyTheme


@Composable
fun ReplyListOnlyContent(
    replyUiState: ReplyUiState,
    onEmailCardPressed: (Email) -> Unit,
    modifier: Modifier = Modifier
) {
    val emails = replyUiState.currentMailboxEmails

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.email_list_item_vertical_spacing)
        )
    ) {
        item {
            ReplyHomeTopBar(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensionResource(id = R.dimen.topbar_padding_vertical))
            )
        }
        items(emails, key = { email -> email.id }) { email ->
            ReplyEmailListItem(
                email = email,
                selected = false,
                onCardClick = { onEmailCardPressed(email) }
            )
        }
    }
}

@Composable
fun ReplyListAndDetailContent(
    replyUiState: ReplyUiState,
    onEmailCardPressed: (Email) -> Unit,
    modifier: Modifier = Modifier
) {
    val emails = replyUiState.currentMailboxEmails
    Row(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(
                    end = dimensionResource(id = R.dimen.list_and_detail_list_padding_end),
                    top = dimensionResource(id = R.dimen.list_and_detail_list_padding_top)
                ),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.email_list_item_vertical_spacing)
            )
        ) {
            items(emails, key = { email -> email.id }) { email ->
                ReplyEmailListItem(
                    email = email,
                    selected = replyUiState.currentSelectedEmail.id == email.id,
                    onCardClick = { onEmailCardPressed(email) }
                )
            }
        }
        val activity = LocalContext.current as Activity
        ReplyDetailsScreen(
            replyUiState = replyUiState,
            modifier = Modifier.weight(1f),
            onBackPressed = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReplyEmailListItem(
    email: Email,
    selected: Boolean,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (selected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.secondaryContainer
        ),
        onClick = onCardClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.email_list_item_inner_padding))
        ) {
            ReplyEmailItemHeader(
                email = email,
                Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(id = email.subject),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(
                    top = dimensionResource(id = R.dimen.email_list_item_header_subject_spacing),
                    bottom = dimensionResource(id = R.dimen.email_list_item_subject_body_spacing)
                ),
            )
            Text(
                text = stringResource(id = email.body),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun ReplyEmailItemHeader(email: Email, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        ReplyProfileImage(
            drawableResource = email.sender.avatar,
            description = email.sender.fullName,
            modifier = Modifier.size(dimensionResource(R.dimen.email_header_profile_size))
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    horizontal = dimensionResource(id = R.dimen.email_header_content_padding_horizontal),
                    vertical = dimensionResource(id = R.dimen.email_header_content_padding_vertical)
                ),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = email.sender.firstName),
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = stringResource(id = email.createdAt),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }

    }
}

@Composable
fun ReplyProfileImage(
    @DrawableRes drawableResource: Int,
    description: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Image(
            modifier = Modifier.clip(CircleShape),
            painter = painterResource(id = drawableResource),
            contentDescription = description
        )
    }
}

@Composable
fun ReplyLogo(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = stringResource(id = R.string.logo),
        colorFilter = ColorFilter.tint(color),
        modifier = modifier
    )
}

@Composable
private fun ReplyHomeTopBar(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        ReplyLogo(
            modifier = Modifier
                .size(dimensionResource(id = R.dimen.topbar_logo_size))
                .padding(start = dimensionResource(id = R.dimen.topbar_logo_padding_start))
        )
        ReplyProfileImage(
            drawableResource = LocalAccountsDataProvider.defaultAccount.avatar,
            description = stringResource(id = R.string.profile),
            modifier = Modifier
                .padding(end = dimensionResource(id = R.dimen.topbar_logo_size))
                .size(dimensionResource(id = R.dimen.topbar_profile_image_size))
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyReplyTheme {

    }
}