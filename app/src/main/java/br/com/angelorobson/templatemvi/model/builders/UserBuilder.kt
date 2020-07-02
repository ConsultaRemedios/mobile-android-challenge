package br.com.angelorobson.templatemvi.model.builders

import br.com.angelorobson.templatemvi.model.domains.User

class UserBuilder {

    data class Builder(
            private var login: String = "",
            private var avatarUrl: String = ""
    ) {

        fun login(login: String) =
                apply { this.login = login }

        fun avatarUrl(avatarUrl: String) = apply { this.avatarUrl = avatarUrl }

        fun oneUser() = apply {
            login = "angelorobsonmelo"
            avatarUrl = "Brazil"
        }

        fun build() = User(
                login = login,
                avatarUrl = avatarUrl
        )
    }
}