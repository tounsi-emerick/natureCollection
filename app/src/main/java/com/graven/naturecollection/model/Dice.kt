package com.graven.naturecollection.model
class Dice {
    var sides = 6

    fun roll(): Int {
        return (1..sides).random()
    }
}