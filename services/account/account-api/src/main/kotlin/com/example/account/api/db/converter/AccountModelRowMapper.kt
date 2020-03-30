package com.example.account.api.db.converter

import com.example.account.api.core.model.AccountModel
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class AccountModelRowMapper: RowMapper<AccountModel> {
    override fun mapRow(rs: ResultSet, rowNum: Int): AccountModel? {
        return AccountModel(id = rs.getString("id"),
                displayName = rs.getString("display_name"),
                loginName = rs.getString("login_name"),
                password = rs.getString("password"),
                rainbow = rs.getString("rainbow"),
                email = rs.getString("email"),
                active = rs.getBoolean("active"))
    }
}