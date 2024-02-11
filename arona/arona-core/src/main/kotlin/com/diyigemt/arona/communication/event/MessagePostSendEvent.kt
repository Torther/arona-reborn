package com.diyigemt.arona.communication.event

import com.diyigemt.arona.communication.TencentApiErrorException
import com.diyigemt.arona.communication.contact.*
import com.diyigemt.arona.communication.message.MessageChain
import com.diyigemt.arona.communication.message.MessageReceipt

sealed class MessagePostSendEvent<C: Contact> : TencentBotEvent, AbstractEvent() {
  abstract val target: C
  final override val bot get() = target.bot
  abstract val message: MessageChain
  abstract val exception: Throwable?
  abstract val receipt: MessageReceipt?
}

@get:JvmSynthetic
inline val MessagePostSendEvent<*>.isSuccess: Boolean
  get() = exception == null

@get:JvmSynthetic
inline val MessagePostSendEvent<*>.isFailure: Boolean
  get() = exception != null

@get:JvmSynthetic
inline val MessagePostSendEvent<*>.isTencentError: Boolean
  get() = exception is TencentApiErrorException

data class GroupMessagePostSendEvent(
  override val target: Group,
  override val message: MessageChain,
  override val exception: Throwable?,
  override val receipt: MessageReceipt?
) : MessagePostSendEvent<Group>()

data class FriendMessagePostSendEvent(
  override val target: FriendUser,
  override val message: MessageChain,
  override val exception: Throwable?,
  override val receipt: MessageReceipt?
) : MessagePostSendEvent<FriendUser>()

data class GuildMessagePostSendEvent(
  override val target: Guild,
  override val message: MessageChain,
  override val exception: Throwable?,
  override val receipt: MessageReceipt?
) : MessagePostSendEvent<Guild>()

data class ChannelMessagePostSendEvent(
  override val target: Channel,
  override val message: MessageChain,
  override val exception: Throwable?,
  override val receipt: MessageReceipt?
) : MessagePostSendEvent<Channel>()
