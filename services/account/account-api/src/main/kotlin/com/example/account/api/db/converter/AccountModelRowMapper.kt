package com.example.account.api.db.converter

import com.example.account.api.core.model.AccountModel
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.util.*

class AccountModelRowMapper: RowMapper<AccountModel> {
    override fun mapRow(rs: ResultSet, rowNum: Int): AccountModel? {
        return AccountModel(
                id = rs.getObject("id", UUID::class.java),
                displayName = rs.getString("display_name"),
                loginName = rs.getString("login_name"),
                password = rs.getString("password"),
                email = rs.getString("email"),
                active = rs.getBoolean("active"))
    }
}