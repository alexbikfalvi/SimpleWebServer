package com.bikfalvi.controllers

import scala.util.Random

import com.bikfalvi.controllers.GeoController.Camera
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class GeoController extends Controller {

  private val random = new Random()

  get("/cameras") { request: Request =>
    val count = request.getIntParam("count", 1024 * 1024)
    val response = for (_ <- 0 until count) yield {
      val id = random.nextInt()
      val longitude = 360 * random.nextDouble() - 180
      val latitude = 180 * random.nextDouble() - 90
      val online = random.nextBoolean()
      Camera("camera-%08x".format(id), longitude, latitude, online)
    }
    response.toArray
  }
}

object GeoController {
  case class Camera(name: String, longitude: Double, latitude: Double, online: Boolean)
}
