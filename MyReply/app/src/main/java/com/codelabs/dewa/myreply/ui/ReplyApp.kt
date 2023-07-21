package com.codelabs.dewa.myreply.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codelabs.dewa.myreply.data.Email
import com.codelabs.dewa.myreply.data.MailboxType
import com.codelabs.dewa.myreply.ui.utils.ReplyNavigationType

@Composable
fun ReplyApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    val navigationType: ReplyNavigationType
    val viewModel: ReplyViewModel = viewModel()
    val replyUiState = viewModel.uiState.collectAsState().value

    when (windowSize){
        WindowWidthSizeClass.Compact -> {
            navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = ReplyNavigationType.NAVIGATION_RAIL
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
        }
        else -> {
            navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
        }
    }

    ReplyHomeScreen(
        navigationType = navigationType,
        replyUiState = replyUiState,
        onTabPressed = { mailboxType: MailboxType ->
            viewModel.updateCurrentMailbox(mailboxType = mailboxType)
            viewModel.resetHomeScreenStates()
        },
        onEmailCardPressed = { email: Email ->
            viewModel.updateDetailsScreenStates(
                email = email
            )
        },
        onDetailScreenBackPressed = {
            viewModel.resetHomeScreenStates()
        },
        modifier = modifier
    )
}