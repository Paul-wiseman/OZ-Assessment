package com.example.ozeassessment.data.database.entity.mapper

import com.example.ozeassessment.data.database.entity.User
import com.example.ozeassessment.models.Item

    fun mapToUser(response: Item): User {
        return with(response) {
            User(
                0,
                userId = this.id,
                avatar_url = this.avatar_url,
                events_url = this.events_url,
                followers_url = this.followers_url,
                following_url = this.following_url,
                gists_url = this.gists_url,
                gravatar_id = this.gravatar_id,
                html_url = this.html_url,
                login = this.login,
                node_id = this.node_id,
                organizations_url = this.organizations_url,
                received_events_url = this.received_events_url,
                repos_url = this.repos_url,
                score = this.score,
                site_admin = this.site_admin,
                starred_url = this.starred_url,
                subscriptions_url = this.subscriptions_url,
                type = this.type,
                url = this.url
            )
        }
    }

fun mapToItem(response: User): Item {
        return with(response) {
            Item(
                id=this.userId,
                avatar_url = this.avatar_url,
                events_url = this.events_url,
                followers_url = this.followers_url,
                following_url = this.following_url,
                gists_url = this.gists_url,
                gravatar_id = this.gravatar_id,
                html_url = this.html_url,
                login = this.login,
                node_id = this.node_id,
                organizations_url = this.organizations_url,
                received_events_url = this.received_events_url,
                repos_url = this.repos_url,
                score = this.score,
                site_admin = this.site_admin,
                starred_url = this.starred_url,
                subscriptions_url = this.subscriptions_url,
                type = this.type,
                url = this.url
            )
        }
    }
