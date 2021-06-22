package com.moworkspace.myfinal

import com.google.firebase.database.Exclude

data class comment (
    var objectId : String,
    var author : String,
    var time : String,
    var contents : String,
    var timestamp: Long =0
){
    @Exclude
    fun toMap(): HashMap<String, Any>{
        val result: HashMap<String, Any> = HashMap()
        result["objectID"] = objectId
        result["author"] = author
        result["time"]=time
        result["contents"]=contents
        result["timestamp"]=timestamp
        return result
    }
}