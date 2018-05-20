/*
 * Copyright (c) 2018 Alex Bikfalvi
 */

package com.bikfalvi.controllers

import java.nio.charset.StandardCharsets
import java.util.Base64

import scala.collection.mutable
import scala.util.Random

import com.bikfalvi.controllers.LoginController._
import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.finatra.http.Controller

class LoginController extends Controller {

  private val random = new Random()
  private val challenges = mutable.HashMap[String, String]()
  private val sessions = mutable.HashSet[String]()

  post("/login") { request: LoginRequest =>
    request match {
      case LoginRequest(LoginController.User) =>
        val challenge = new String(Base64.getEncoder.encode(random.nextString(64).getBytes(StandardCharsets.UTF_8)))
        val response = challenge.reverse
        challenges += challenge -> response
        LoginResponse(LoginController.User, challenge)
      case _ =>
        Response(Status.NotFound)
    }
  }

  post("/authorize") { request: AuthorizeRequest =>
    request match {
      case AuthorizeRequest(LoginController.User, challenge, response) =>
        challenges.get(challenge) match {
          case Some(r) if r == response =>
            val session = new String(Base64.getEncoder.encode(random.nextString(64).getBytes(StandardCharsets.UTF_8)))
            sessions += session
            AuthorizeResponse(LoginController.User, session)
          case _ =>
            Response(Status.Unauthorized)
        }
      case _ =>
        Response(Status.Unauthorized)
    }
  }

  get("/devices") { request: Request =>
    Array(Device("camera01", "Front store camera", random.nextInt(100), random.nextInt(100)),
          Device("camera02", "Back store camera", random.nextInt(100), random.nextInt(100)))
  }
}

object LoginController {

  final val User = "interview@midokura.com"

  case class LoginRequest(user: String)
  case class LoginResponse(user: String, challenge: String)
  case class AuthorizeRequest(user: String, challenge: String, response: String)
  case class AuthorizeResponse(user: String, session: String)
  case class Device(name: String, description: String, cpu: Int, gpu: Int)
}