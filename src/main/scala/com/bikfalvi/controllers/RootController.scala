package com.bikfalvi.controllers

import com.twitter.finagle.http.{Request, Status}
import com.twitter.finatra.http.Controller

class RootController extends Controller {

  options("/:*") { _: Request =>
    Status.Ok
  }
}
