package br.com.angelorobson.templatemvi.view.pullrequest

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import br.com.angelorobson.templatemvi.model.repositories.PullRequestRepository
import br.com.angelorobson.templatemvi.view.utils.ActivityService
import br.com.angelorobson.templatemvi.view.utils.HandlerErrorRemoteDataSource.validateStatusCode
import br.com.angelorobson.templatemvi.view.utils.IdlingResource
import br.com.angelorobson.templatemvi.view.utils.MobiusVM
import br.com.angelorobson.templatemvi.view.utils.toast
import com.spotify.mobius.Next
import com.spotify.mobius.Next.dispatch
import com.spotify.mobius.Next.next
import com.spotify.mobius.Update
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


fun pullRequestUpdate(
        model: PullRequestModel,
        event: PullRequestEvent
): Next<PullRequestModel, PullRequestEffect> {
    return when (event) {
        is InitialEvent -> dispatch(setOf(ObservePullRequestEffect(event.repository)))
        is PullRequestLoadedEvent -> next(model.copy(pullRequestResult = PullRequestResult.PullRequestLoaded(event.pullRequests)))
        is PullRequestExceptionEvent -> next(model.copy(pullRequestResult = PullRequestResult.Error(event.errorMessage)))
        is PullRequestClickedEvent -> dispatch(setOf(PullRequestClickedEffect(event.pullRequest)))
    }
}

class PullRequestViewModel @Inject constructor(
        repository: PullRequestRepository,
        activityService: ActivityService,
        idlingResource: IdlingResource
) : MobiusVM<PullRequestModel, PullRequestEvent, PullRequestEffect>(
        "PullRequestViewModel",
        Update(::pullRequestUpdate),
        PullRequestModel(),
        RxMobius.subtypeEffectHandler<PullRequestEffect, PullRequestEvent>()
                .addTransformer(ObservePullRequestEffect::class.java) { upstream ->
                    idlingResource.increment()
                    upstream.switchMap {
                        val owner = it.repository.user.login
                        val repositoryName = it.repository.name
                        repository.getAll(owner, repositoryName)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .map {
                                    idlingResource.decrement()
                                    PullRequestLoadedEvent(it) as PullRequestEvent
                                }
                                .onErrorReturn {
                                    val errorMessage = validateStatusCode(it)
                                    activityService.activity.toast(errorMessage, Toast.LENGTH_LONG)
                                    PullRequestExceptionEvent(errorMessage)
                                }
                    }
                }
                .addConsumer(PullRequestClickedEffect::class.java) { consumer ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(consumer.pullRequest.htmlUrl))
                    activityService.activity.startActivity(intent)
                }
                .build()
)