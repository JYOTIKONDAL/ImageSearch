package com.damcoassignment.model

import com.damcoassignment.extensions.empty


class ColorsListModel {

    val numHearts: String = String.empty

    val badgeUrl: String = String.empty

    val numVotes: String = String.empty

    val description: String = String.empty

    val hsv: Hsv = Hsv()

    val title: String = String.empty

    val userName: String = String.empty

    val rgb: Rgb = Rgb()

    val url: String = String.empty

    val numViews: String = String.empty

    val numComments: String = String.empty

    val dateCreated: String = String.empty

    val apiUrl: String = String.empty

    val imageUrl: String = String.empty

    val rank: String = String.empty

    val hex: String = String.empty

    val id: String = String.empty

    var isLiked: Boolean = false

    class Rgb {
        val red: String = String.empty

        val green: String = String.empty

        val blue: String = String.empty
    }

    class Hsv {
        val saturation: String = String.empty

        val hue: String = String.empty

        val value: String = String.empty
    }
}