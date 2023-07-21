package com.codelabs.dewa.myreply.ui

import com.codelabs.dewa.myreply.data.Email
import com.codelabs.dewa.myreply.data.MailboxType
import com.codelabs.dewa.myreply.data.local.LocalEmailsDataProvider

data class ReplyUiState(
    val mailboxes: Map<MailboxType, List<Email>> = emptyMap(),
    val currentMailbox: MailboxType = MailboxType.Inbox,
    val currentSelectedEmail: Email = LocalEmailsDataProvider.defaultEmail,
    val isShowingHomePage: Boolean = true
) {
    val currentMailboxEmails: List<Email> by lazy { mailboxes[currentMailbox]!! }
}