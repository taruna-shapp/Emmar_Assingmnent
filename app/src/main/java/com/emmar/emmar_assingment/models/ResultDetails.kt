package com.emmar.emmar_assingment.models

import java.util.Date

data class ResultDetails (
    var name: Name? = null,
    var location: Location? = null,
    var email: String? = null,
    var dob: Dob? = null,
    var registered: Registered? = null,
    var picture: Picture? = null,
)
data class Dob (
    var date: Date? = null,
    var age: Int = 0
)
data class Location (
    var city: String? = null,
    var state: String? = null,
    var country: String? = null,
    var postcode: String? = null
)

data class Name(
    var first: String? = null
)

data class Picture(
    var large: String? = null
)

data class Registered(
    var date: Date? = null
)
