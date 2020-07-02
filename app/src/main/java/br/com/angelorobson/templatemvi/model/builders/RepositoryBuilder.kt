package br.com.angelorobson.templatemvi.model.builders

import br.com.angelorobson.templatemvi.model.domains.Repository
import br.com.angelorobson.templatemvi.model.domains.User

class RepositoryBuilder {

    data class Builder(
            private var id: Int = 1,
            private var name: String = "",
            private var description: String = "",
            private var forksCount: Int = 12,
            private var stargazersCount: Int = 12,
            private var user: User = User()
    ) {

        fun id(id: Int) =
                apply { this.id = id }

        fun name(name: String) = apply { this.name = name }
        fun description(description: String) = apply { this.description = description }
        fun forksCount(forksCount: Int) =
                apply { this.forksCount = forksCount }

        fun stargazersCount(stargazersCount: Int) =
                apply { this.stargazersCount = stargazersCount }

        fun oneRepository() = apply {
            id = 1
            name = "Ângelo Melo"
            description = "description ok"
            forksCount = 111
            stargazersCount = 253
            user = UserBuilder.Builder().oneUser().build()
        }

        fun build() = Repository(
                id = id,
                description = description,
                stargazersCount = stargazersCount,
                forksCount = forksCount,
                name = name,
                user = user
        )
    }

}