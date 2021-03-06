/*
 * Copyright (c) 2018 Alex Bikfalvi
 */

package com.bikfalvi

import com.bikfalvi.controllers.{GeoController, LoginController, RootController}
import com.twitter.finagle.http.filter.Cors
import com.twitter.finagle.http.filter.Cors.HttpFilter
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter

object SimpleWebServer extends HttpServer {
  override def configureHttp(router: HttpRouter): Unit = {
    router
        .filter[CommonFilters]
        .filter(new HttpFilter(Cors.UnsafePermissivePolicy))
        .add[RootController]
        .add[LoginController]
        .add[GeoController]
  }
}