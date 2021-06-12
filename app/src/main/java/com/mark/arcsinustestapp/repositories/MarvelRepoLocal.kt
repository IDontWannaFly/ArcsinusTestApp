package com.mark.arcsinustestapp.repositories

import com.mark.arcsinustestapp.models.Character
import io.realm.Realm

class MarvelRepoLocal {

    fun getCharacters(name: String, page: Int) : List<Character>{
        val realmQuery = Realm.getDefaultInstance().where(Character::class.java)
        if(name.isNotEmpty() && name.length >= 3)
            realmQuery.beginsWith("name", name)
        val result = realmQuery.sort("name").limit(page * 10L).findAll()
        return result.subList((page - 1) * 10, result.size)
    }

    fun saveCharacters(items: List<Character>){
        Realm.getDefaultInstance().executeTransaction {
            it.insertOrUpdate(items)
        }
    }

    fun saveCharacter(item: Character){
        Realm.getDefaultInstance().executeTransaction {
            it.insertOrUpdate(item)
        }
    }

}