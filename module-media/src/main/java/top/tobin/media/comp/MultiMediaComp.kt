package top.tobin.media.comp

import top.tobin.component.service.IMediaComp

class MultiMediaComp : IMediaComp {
    override fun getMediaCompName(): String? {
        return null
    }

    override fun getMediaInfo(): IMediaComp.MediaInfo? {
        return null
    }

    override fun jumpToMedia(): Int {
        return 0
    }

    override fun play(): Int {
        return 0
    }

    override fun pause(): Int {
        return 0
    }

    override fun next(): Int {
        return 0
    }

    override fun addFavorite(): Int {
        return 0
    }

    override fun removeFavorite(): Int {
        return 0
    }

}