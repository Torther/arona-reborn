@file:OptIn(ExperimentalSerializationApi::class)

package com.diyigemt.arona.communication.message

import com.diyigemt.arona.communication.command.UserCommandSender
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
sealed class TencentKeyboard

@Serializable
data class TencentTempleKeyboard(
  val id: String,
) : Message, TencentKeyboard() { // TODO custom keyboard
  override fun serialization(): String {
    TODO("Not yet implemented")
  }
}

@Serializable
data class TencentCustomKeyboard(
  val content: TencentCustomKeyboard0,
) : Message, TencentKeyboard() {
  override fun serialization(): String {
    TODO("Not yet implemented")
  }
}

@Serializable
data class TencentCustomKeyboard0 constructor(
  val rows: List<TencentCustomKeyboard1>,
  @SerialName("bot_appid")
  @EncodeDefault
  var botAppid: String? = null
)

@Serializable
data class TencentCustomKeyboard1(
  val buttons: List<TencentKeyboardButton>,
)

@Serializable
data class TencentKeyboardButton(
  @EncodeDefault
  var id: String, // 按钮ID：在一个keyboard消息内设置唯一
  @SerialName("render_data")
  @EncodeDefault
  var renderData: TencentKeyboardButtonRenderData,
  @EncodeDefault
  var action: TencentKeyboardButtonActionData,
)

@Serializable
data class TencentKeyboardButtonActionData(
  @EncodeDefault
  var type: TencentKeyboardButtonActionType = TencentKeyboardButtonActionType.COMMAND,
  @EncodeDefault
  var data: String = "", // 操作相关的数据
  @EncodeDefault
  var reply: Boolean? = null, // 指令按钮可用，指令是否带引用回复本消息，默认 false。支持版本 8983
  @EncodeDefault
  var enter: Boolean? = null, // 指令按钮可用，点击按钮后直接自动发送 data，默认 false。支持版本 8983
  /**
   * 本字段仅在指令按钮下有效，设置后后会忽略 action.enter 配置。
   * 设置为 1 时 ，点击按钮自动唤起启手Q选图器，其他值暂无效果。
   * （仅支持手机端版本 8983+ 的单聊场景，桌面端不支持）
   */
  @EncodeDefault
  var anchor: Int? = null,
  @SerialName("click_limit")
  @EncodeDefault
  var clickLimit: Int? = null, // 【已弃用】可操作点击的次数，默认不限
  @SerialName("at_bot_show_channel_list")
  @EncodeDefault
  var atBotShowChannelList: Boolean? = null, // 【已弃用】指令按钮可用，弹出子频道选择器，默认 false
  @SerialName("unsupport_tips")
  @EncodeDefault
  var unsupportTips: String? = null, // 客户端不支持本action的时候，弹出的toast文案
  @EncodeDefault
  var permission: TencentKeyboardButtonActionPermissionData = TencentKeyboardButtonActionPermissionData(),
)

@Serializable
data class TencentKeyboardButtonActionPermissionData(
  @EncodeDefault
  var type: TencentKeyboardButtonActionDataType = TencentKeyboardButtonActionDataType.ANY_ONE,
  @SerialName("specify_user_ids") // 有权限的用户 id 的列表
  @EncodeDefault
  var specifyUserIds: List<String>? = null,
  @SerialName("specify_role_ids") // 有权限的身份组 id 的列表（仅频道可用）
  @EncodeDefault
  val specifyRoleIds: List<String>? = null,
)

@Serializable(with = TencentKeyboardButtonActionType.Companion::class)
enum class TencentKeyboardButtonActionType(val id: Int) {
  JUMP(0), // 跳转按钮：http 或 小程序 客户端识别 scheme
  CALLBACK(1), // 回调按钮：回调后台接口, data 传给后台
  COMMAND(2); // 指令按钮：自动在输入框插入 @bot data

  companion object : KSerializer<TencentKeyboardButtonActionType> {
    private val map = entries.associateBy { it.id }
    override fun deserialize(decoder: Decoder) = map.getOrDefault(decoder.decodeInt(), COMMAND)
    override val descriptor = PrimitiveSerialDescriptor("TencentKeyboardButtonActionType", PrimitiveKind.INT)
    override fun serialize(encoder: Encoder, value: TencentKeyboardButtonActionType) = encoder.encodeInt(value.id)
  }
}

@Serializable(with = TencentKeyboardButtonActionDataType.Companion::class)
enum class TencentKeyboardButtonActionDataType(val id: Int) {
  SPECIFY(0), // 指定用户可操作
  MANAGER(1), // 仅管理者可操作
  ANY_ONE(2), // 所有人可操作
  SPECIFIC_ROLE(3); // 指定身份组可操作（仅频道可用）

  companion object : KSerializer<TencentKeyboardButtonActionDataType> {
    private val map = entries.associateBy { it.id }
    override fun deserialize(decoder: Decoder) = map.getOrDefault(decoder.decodeInt(), ANY_ONE)
    override val descriptor = PrimitiveSerialDescriptor("TencentKeyboardButtonActionDataType", PrimitiveKind.INT)
    override fun serialize(encoder: Encoder, value: TencentKeyboardButtonActionDataType) = encoder.encodeInt(value.id)
  }
}

@Serializable
data class TencentKeyboardButtonRenderData(
  @EncodeDefault
  var label: String = "", // 按钮上的文字
  @SerialName("visited_label")
  var visitedLabel: String? = null, // 点击后按钮的上文字
  @EncodeDefault
  var style: TencentKeyboardButtonRenderDataStyle = TencentKeyboardButtonRenderDataStyle.Blue, // 按钮样式
)

@Serializable(with = TencentKeyboardButtonRenderDataStyle.Companion::class)
enum class TencentKeyboardButtonRenderDataStyle(val id: Int) {
  Gray(0), // 灰色线框
  Blue(1); // 蓝色线框

  companion object : KSerializer<TencentKeyboardButtonRenderDataStyle> {
    private val map = entries.associateBy { it.id }
    override fun deserialize(decoder: Decoder) = map.getOrDefault(decoder.decodeInt(), Blue)
    override val descriptor = PrimitiveSerialDescriptor("TencentKeyboardButtonRenderDataStyle", PrimitiveKind.INT)
    override fun serialize(encoder: Encoder, value: TencentKeyboardButtonRenderDataStyle) = encoder.encodeInt(value.id)
  }
}

@DslMarker
annotation class KeyboardDsl

class TencentCustomKeyboardBuilder internal constructor() {
  private val rows: MutableList<TencentCustomKeyboardRowBuilder> = mutableListOf()
  private var userId = ""

  @KeyboardDsl
  fun row(init: TencentCustomKeyboardRowBuilder.() -> Unit) {
    rows.add(
      TencentCustomKeyboardRowBuilder().also(init)
    )
  }

  fun UserCommandSender.specifyUser() {
    userId = user.unionOpenidOrId
  }

  fun build(botAppId: String): TencentCustomKeyboard {
    return TencentCustomKeyboard(
      TencentCustomKeyboard0(
        rows.map { it.build() },
        botAppId
      ).also {
        if (userId.isNotEmpty()) {
          it.rows.forEach { r ->
            r.buttons.forEach { b ->
              b.action.permission.specifyUserIds = listOf(userId)
              b.action.permission.type = TencentKeyboardButtonActionDataType.SPECIFY
            }
          }
        }
      }
    )
  }
}

@KeyboardDsl
fun TencentCustomKeyboardRowBuilder.button(id: String, init: TencentCustomKeyboardButtonBuilder.() -> Unit) {
  buttons.add(
    TencentCustomKeyboardButtonBuilder().apply {
      this.id = id
    }.also(init)
  )
}

class TencentCustomKeyboardRowBuilder internal constructor() {
  val buttons: MutableList<TencentCustomKeyboardButtonBuilder> = mutableListOf()
  private var userId = ""

  fun UserCommandSender.specifyUser() {
    userId = user.unionOpenidOrId
  }

  fun build(): TencentCustomKeyboard1 {
    return TencentCustomKeyboard1(
      buttons.map { it.build() }
    ).also {
      if (userId.isNotEmpty()) {
        it.buttons.forEach { b ->
          b.action.permission.specifyUserIds = listOf(userId)
          b.action.permission.type = TencentKeyboardButtonActionDataType.SPECIFY
        }
      }
    }
  }
}

class TencentCustomKeyboardButtonBuilder internal constructor() {
  var id = ""
  var renderData = TencentKeyboardButtonRenderData()
  var actionData = TencentKeyboardButtonActionData()

  private var userId = ""

  fun UserCommandSender.specifyUser() {
    userId = user.unionOpenidOrId
  }

  @KeyboardDsl
  fun render(init: TencentKeyboardButtonRenderData.() -> Unit) {
    renderData.init()
    if (renderData.visitedLabel.isNullOrEmpty()) {
      renderData.visitedLabel = renderData.label
    }
  }

  @KeyboardDsl
  fun action(init: TencentKeyboardButtonActionData.() -> Unit) {
    actionData.init()
  }

  fun build(): TencentKeyboardButton {
    return TencentKeyboardButton(id, renderData, actionData).also {
      if (userId.isNotEmpty()) {
        actionData.permission.specifyUserIds = listOf(userId)
        actionData.permission.type = TencentKeyboardButtonActionDataType.SPECIFY
      }
    }
  }
}

/**
 * {
 *   "rows": [
 *     {
 *       "buttons": [
 *         {
 *           "id": "1",
 *           "render_data": {
 *             "label": "⬅️上一页",
 *             "visited_label": "⬅️上一页"
 *           },
 *           "action": {
 *             "type": 1,
 *             "permission": {
 *               "type": 2
 *             },
 *             "data": "data"
 *           }
 *         },
 *         {
 *           "id": "2",
 *           "render_data": {
 *             "label": "➡️下一页",
 *             "visited_label": "➡️下一页"
 *           },
 *           "action": {
 *             "type": 1,
 *             "permission": {
 *               "type": 2
 *             },
 *             "data": "data"
 *           }
 *         }
 *       ]
 *     },
 *     {
 *       "buttons": [
 *         {
 *           "id": "3",
 *           "render_data": {
 *             "label": "📅 打卡（5）",
 *             "visited_label": "📅 打卡（5）"
 *           },
 *           "action": {
 *             "type": 1,
 *             "permission": {
 *               "type": 2
 *             },
 *             "data": "data"
 *           }
 *         }
 *       ]
 *     }
 *   ]
 * }
 * val button = tencentCustomKeyboard {
 *   row {
 *     button("1") {
 *       render {
 *         label = "⬅\uFE0F上一页"
 *         visitedLabel = "⬅\uFE0F上一页"
 *       }
 *       action {
 *         type = TencentKeyboardButtonActionType.CALLBACK
 *         data = ""
 *       }
 *     }
 *     button("2") {
 *       render {
 *         label = "➡\uFE0F下一页"
 *         visitedLabel = "➡\uFE0F下一页"
 *       }
 *       action {
 *         type = TencentKeyboardButtonActionType.CALLBACK
 *         data = ""
 *       }
 *     }
 *   }
 *   row {
 *     button("3") {
 *       render {
 *         label = "\uD83D\uDCC5 打卡（5）"
 *         visitedLabel = "\uD83D\uDCC5 打卡（5）"
 *       }
 *       action {
 *         type = TencentKeyboardButtonActionType.CALLBACK
 *         data = ""
 *       }
 *     }
 *   }
 * }
 *
 */
fun tencentCustomKeyboard(botAppId: String, init: TencentCustomKeyboardBuilder.() -> Unit): TencentCustomKeyboard {
  // TODO id去重 按钮上限
  return TencentCustomKeyboardBuilder().also(init).build(botAppId)
}
