package com.codelabs.dewa.myreply.data

data class Email(
    val id: Long,
    val sender: Account,
    val recipients: List<Account> = emptyList(),
    val subject: Int = -1,
    val body: Int = -1,
    var mailbox: MailboxType = MailboxType.Inbox,
    var createdAt: Int = -1
)