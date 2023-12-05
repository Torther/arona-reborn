package com.diyigemt.arona.database.permission

import com.diyigemt.arona.database.*
import com.diyigemt.arona.database.AronaDatabase
import com.diyigemt.arona.database.DatabaseProvider.sqlDbQuery
import com.diyigemt.arona.database.DocumentCompanionObject
import com.diyigemt.arona.database.SystemPropertiesSchema
import com.diyigemt.arona.utils.currentDateTime
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.client.result.UpdateResult
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

private const val BASE_ID_KEY = "BASE_ID"
private val BASE_ID: String
  @Synchronized
  get() {
    return sqlDbQuery {
      val id = SystemPropertiesSchema.findById(BASE_ID_KEY)
      return@sqlDbQuery if (id == null) {
        SystemPropertiesSchema.new(BASE_ID_KEY) {
          value = (1000000L + 1).toString()
        }.value
      } else {
        (id.value.toLong() + 1).let {
          id.value = it.toString()
          id.value
        }
      }
    }
  }

@AronaDatabase
internal object UserTable : IdTable<String>(name = "User") {
  override val id: Column<EntityID<String>> = text("id").entityId() // 藤子给定的id
  val username = text("username").clientDefault { "老师" } // 用户自定义的昵称
  val from = text("from") // 来源的环境id
  val uid = text("uid") // 对应的自己定义的唯一id 和 UserDocument 的 id 关联
  val registerTime = char("register_time", length = 25).clientDefault { currentDateTime() } // 注册时间
  override val primaryKey: PrimaryKey = PrimaryKey(id)
}

internal class UserSchema(id: EntityID<String>) : Entity<String>(id) {
  companion object : EntityClass<String, UserSchema>(UserTable)

  var username by UserTable.username
  var from by UserTable.from
  var uid by UserTable.uid
  val registerTime by UserTable.registerTime
}

@Serializable
internal data class UserDocument(
  @BsonId
  val id: String, // 自己定义的唯一id
  val username: String = "Arona用户$id", // 显示在前端的用户名
  val unionOpenId: String = "", // 藤子定义的唯一id
  val qq: Long = 0L, // 用户绑定的qq号
  val uid: List<String> = listOf(), // 藤子给定的不同聊天环境下的id
  val contacts: List<String> = listOf(), // 存在的不同的群/频道的id
  val policies: List<Policy> = listOf(), // 用户自定义的规则
) {
  companion object : DocumentCompanionObject {
    override val documentName = "User"
    suspend fun createUserDocument(uid: String, contactId: String) = UserDocument(
      BASE_ID,
      uid = mutableListOf(uid),
      contacts = mutableListOf(contactId)
    ).also {
      withCollection {
        insertOne(it)
      }
    }

    suspend fun findUserDocumentByUidOrNull(uid: String): UserDocument? = withCollection {
      find(uidFilter(uid)).limit(1).firstOrNull()
    }

    suspend fun UserDocument.updateUserContact(contactId: String) = withCollection<UserDocument, UpdateResult> {
      updateOne(
        filter = idFilter(id),
        update = Updates.addToSet("contacts", contactId)
      )
    }
  }
}

internal fun uidFilter(uid: String) = Filters.elemMatch("uid", Filters.eq(uid))