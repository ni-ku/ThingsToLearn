package de.niku.ttl.data.fs

import android.content.Context
import android.os.Environment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.gson.Gson
import de.niku.ttl.model.CardSet
import io.reactivex.Observable
import java.io.File
import java.io.FileWriter

class FsRepoImpl(var context: Context?) : FsRepo, LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        context = null
    }

    override fun writeCardSetsToFile(list: List<CardSet>) : Observable<String> {
        return Observable.create<String> {
            try {
                val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath
                val gson = Gson()
                val json = gson.toJson(list)
                val fileName = "CardSet-Export.json"
                val f = File(dir, fileName)
                val fw = FileWriter(f)
                fw.write(json)
                fw.flush()
                fw.close()
                it.onNext("$dir/$fileName")
            } catch (e: FileSystemException) {
                it.onError(Throwable("Error writing file"))
            }
        }
    }
}