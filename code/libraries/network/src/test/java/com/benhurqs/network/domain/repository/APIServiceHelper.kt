package com.benhurqs.network.domain.repository

import com.benhurqs.network.data.ChallengeService
import com.benhurqs.network.entities.Banner
import com.benhurqs.network.entities.Spotlight
import com.benhurqs.network.entities.Suggestion
import io.reactivex.Observable

class APIServiceHelper : ChallengeService(){

    override fun getBanners(): Observable<List<Banner>?> {
        return Observable.just(ArrayList<Banner>().apply {
            add(Banner())
        })
    }

    override fun getDetail(id: Int?): Observable<Spotlight?> {
        return Observable.just(Spotlight())
    }

    override fun getSearchSuggestions(query: String?): Observable<List<Suggestion>?> {
        return Observable.just(ArrayList<Suggestion>().apply {
            add(Suggestion())
        })
    }

    override fun getSpotlights(): Observable<List<Spotlight>?> {
        return Observable.just(ArrayList<Spotlight>().apply {
            add(Spotlight())
        })
    }

}