package mazer.arthur.gamingshop.utils.listeners

import mazer.arthur.gamingshop.domain.models.Banner

interface BannerClickListener {
    fun onBannerClicked(banner: Banner)
}