package services.picker

import models.Starship

class StarshipPicker {
    fun pick(list: List<Starship>, id: String): Pair<List<Starship>, Starship> =
        list.filter { it.getId() != id } to (list.find { it.getId() == id }!!)
}
