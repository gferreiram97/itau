package com.itau.pix.domain.entities

import com.itau.pix.domain.enums.KeyType
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.time.LocalDateTime
import java.util.*
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "keys_pix")
class PixKey(

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "char(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(cascade = [CascadeType.ALL])
    var account: Account,

    @Enumerated(EnumType.STRING)
    val keyType: KeyType,

    @Column(unique = true, nullable = false)
    val keyValue: String,

    var inactive: Boolean,

    var dateInactive: LocalDateTime? = null,

    ) : BaseEntity() {

    fun inactivate() {
        inactive = true
        dateInactive = LocalDateTime.now()
    }

    fun isInactive(): Boolean {
        return inactive
    }

}
