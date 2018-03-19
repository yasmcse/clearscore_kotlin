package com.example.yasirnazir.clearscore.models

import com.google.gson.annotations.SerializedName

/**
 * Created by yasirnazir on 3/14/18.
 */


class Response : GenericModel {
    var creditReportInfo: CreditReportInfo? = null

    constructor(creditReportInfo: CreditReportInfo) {
        this.creditReportInfo = creditReportInfo
    }

    constructor() {}

}