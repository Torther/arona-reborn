@file:Suppress("unused")
package com.diyigemt.arona.webui.endpoints.contact

import com.diyigemt.arona.database.DatabaseProvider
import com.diyigemt.arona.database.permission.ContactDocument
import com.diyigemt.arona.database.permission.ContactMember
import com.diyigemt.arona.database.withCollection
import com.diyigemt.arona.utils.success
import com.diyigemt.arona.webui.endpoints.AronaBackendEndpoint
import com.diyigemt.arona.webui.endpoints.AronaBackendEndpointGet
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Projections
import io.ktor.server.application.*
import io.ktor.util.pipeline.*
import kotlinx.coroutines.flow.toList
import org.bson.Document

@AronaBackendEndpoint("/contact")
object ContactEndpoint {
  /**
   * 获取用户所有管理的群/频道列表
   */
  @AronaBackendEndpointGet("/contacts")
  suspend fun PipelineContext<Unit, ApplicationCall>.contacts() {
    val manageContacts = ContactDocument.withCollection<ContactDocument, List<ContactDocument.ContactDocumentWithName>> {
      find<ContactDocument.ContactDocumentWithName>(
        Document(ContactDocument::members.name, Document("\$elemMatch", Document(ContactMember::roles.name, "role.admin")))
      ).projection(
        Projections.include(ContactDocument::contactName.name)
      ).toList()
    }
    success(manageContacts)
  }
}
