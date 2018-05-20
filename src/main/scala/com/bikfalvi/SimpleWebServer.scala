/*
 * Copyright (c) 2018 Alex Bikfalvi
 */

package com.bikfalvi

import com.bikfalvi.controllers.LoginController
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter

object SimpleWebServer extends HttpServer {
  override def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[CommonFilters]
      .add[LoginController]
  }
}