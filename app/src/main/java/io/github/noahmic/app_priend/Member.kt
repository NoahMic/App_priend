package io.github.noahmic.app_priend

data class Member(
    val group: List<Group>,
    val manito: List<Manito>,
    val name: String,
    val uid: String
)