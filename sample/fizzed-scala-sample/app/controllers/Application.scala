package controllers

import com.fizzed.play.twitter.TwitterPlugin

import play.api._
import play.api.mvc._

object Application extends Controller {

  val TWITTER_PLUGIN = Play.current.plugin[TwitterPlugin]

  def index = Action {
    Ok(views.html.index("Fizzed Twitter Module Sample"))
  }

}