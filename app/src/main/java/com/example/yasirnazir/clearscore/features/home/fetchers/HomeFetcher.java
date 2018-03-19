package com.example.yasirnazir.clearscore.features.home.fetchers;


import com.example.yasirnazir.clearscore.base_classes.Fetcher;
import com.example.yasirnazir.clearscore.models.ApiError;
import com.example.yasirnazir.clearscore.models.ClearScoreApiEndPoints;
import com.example.yasirnazir.clearscore.models.Response;
import com.example.yasirnazir.clearscore.networking.NetworkService;

/**
 * Created by yasirnazir on 3/18/18.
 */


public class HomeFetcher extends Fetcher<Response> {
    public HomeFetcher(NetworkService networkService) {
        super(() -> networkService.getCreditValues()
                .doOnNext(HomeFetcher::checkIfErrorExists), ClearScoreApiEndPoints.CREDIT_VALUES);
    }

    private static void checkIfErrorExists(Response response) {
        if (response == null) {
            throw new ApiError();
        }
    }
}
